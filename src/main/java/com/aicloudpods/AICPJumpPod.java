package com.aicloudpods;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.launcher.SparkAppHandle;
import org.apache.spark.launcher.SparkLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@SpringBootApplication
public class AICPJumpPod implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(AICPJumpPod.class, args);
    }

    @Autowired
    private KafkaConfig kafkaConfig;
    
    @Override
    public void run(String... args) {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Consumer<String, String> consumer = new KafkaConsumer<>(buildKafkaProperties());
            consumer.subscribe(Collections.singleton(kafkaConfig.getKafkaTopic()));
            while (true) {
                System.out.println("Started polling the kafka at::: " + kafkaConfig.getKafkaTopic() + " with the bootstrap servers at:::"+ kafkaConfig.getKafkaBootstrapServers());
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println("Message received: " + record.value());
                    Event event = objectMapper.readValue(record.value(), Event.class);
                    System.out.println("Event arrived for the MasterURL file::"+ event.getMasterURL());
                    //submitTheSparkJob(event);
                }
                consumer.commitAsync();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private Properties buildKafkaProperties() {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getKafkaBootstrapServers());
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConfig.getKafkaGroupId());
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        return properties;
    }

    private void submitTheSparkJob(Event event) throws IOException {
        SparkAppHandle sparkAppHandle = new SparkLauncher()
                .setAppName(event.getAppName())
                .setMaster(event.getMasterURL())
                .setDeployMode(event.getDeployMode())
                .setMainClass(event.getMainClass())
                .setConf("spark.kubernetes.namespace", event.getJobNamespace())
                .setConf("spark.kubernetes.authenticate.driver.serviceAccountName", event.getJobServiceAccount())
                .setConf("spark.executor.instances", event.getJobExecutorInstances())
                .setConf("spark.kubernetes.container.image", event.getJobContainerImage())
                .setAppResource(event.getJobAppJarLocation())
                .setVerbose(true)
                .startApplication(new AiJumpListener());
        System.out.println("Launched the app with the ID" + sparkAppHandle.getAppId());
        System.out.println("Get the state" + sparkAppHandle.getState());
        System.out.println("Spark Job Launched!");
    }
}
