package com.aicloudpods;

public class Event {

    private String appName;
    private String masterURL;
    private String deployMode;
    private String mainClass;
    private String jobNamespace;
    private String jobServiceAccount;
    private String jobExecutorInstances;
    private String jobContainerImage;
    private String jobAppJarLocation;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getMasterURL() {
        return masterURL;
    }

    public void setMasterURL(String masterURL) {
        this.masterURL = masterURL;
    }

    public String getDeployMode() {
        return deployMode;
    }

    public void setDeployMode(String deployMode) {
        this.deployMode = deployMode;
    }

    public String getMainClass() {
        return mainClass;
    }

    public void setMainClass(String mainClass) {
        this.mainClass = mainClass;
    }

    public String getJobNamespace() {
        return jobNamespace;
    }

    public void setJobNamespace(String jobNamespace) {
        this.jobNamespace = jobNamespace;
    }

    public String getJobServiceAccount() {
        return jobServiceAccount;
    }

    public void setJobServiceAccount(String jobServiceAccount) {
        this.jobServiceAccount = jobServiceAccount;
    }

    public String getJobExecutorInstances() {
        return jobExecutorInstances;
    }

    public void setJobExecutorInstances(String jobExecutorInstances) {
        this.jobExecutorInstances = jobExecutorInstances;
    }

    public String getJobContainerImage() {
        return jobContainerImage;
    }

    public void setJobContainerImage(String jobContainerImage) {
        this.jobContainerImage = jobContainerImage;
    }

    public String getJobAppJarLocation() {
        return jobAppJarLocation;
    }

    public void setJobAppJarLocation(String jobAppJarLocation) {
        this.jobAppJarLocation = jobAppJarLocation;
    }
}
