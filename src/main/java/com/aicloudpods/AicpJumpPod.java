package com.aicloudpods;

import org.apache.spark.launcher.SparkLauncher;

import java.io.IOException;

public class AicpJumpPod {

    public static void main(String args[]) throws IOException, InterruptedException {
        Process launch = new SparkLauncher()
                .setAppName("AICPLauncherApp")
                .setMaster("k8s://https://kubernetes.docker.internal:6443")
                //.setSparkHome("/Users/balamuruganguruswamy/balamurugan/software/spark-3.0.0-bin-hadoop2.7")
                .setDeployMode("cluster")
                .setMainClass("com.aicloudpods.SparkMinIO")
                .setConf("spark.kubernetes.namespace", "spark")
                .setConf("spark.kubernetes.authenticate.driver.serviceAccountName", "spark-sa")
                .setConf("spark.executor.instances", "2")
                .setConf("spark.kubernetes.container.image", "aicp/ai-mi:latest")
                .setAppResource("local:///opt/spark/examples/jars/sparkonminio.jar")
                .setVerbose(true)
                .launch();

        InputStreamReaderRunnable inputStreamReaderRunnable = new InputStreamReaderRunnable(launch.getInputStream(), "spark stdout");
        Thread inputThread = new Thread(inputStreamReaderRunnable, "input");
        inputThread.start();


        InputStreamReaderRunnable errorStreamReaderRunnable = new InputStreamReaderRunnable(launch.getInputStream(), "spark stderr");
        Thread errorThread = new Thread(inputStreamReaderRunnable, "error");
        errorThread.start();

        launch.waitFor();
    }
}
