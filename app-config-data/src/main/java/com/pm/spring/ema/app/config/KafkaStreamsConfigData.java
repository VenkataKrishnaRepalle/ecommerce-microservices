package com.pm.spring.ema.app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "kafka-streams-config")
public class KafkaStreamsConfigData {
  private String applicationID;
  private String inputTopicName;
  private String outputTopicName;
  private String stateFileLocation;
  private String wordCountStoreName;

  public String getApplicationID() {
    return applicationID;
  }

  public void setApplicationID(String applicationID) {
    this.applicationID = applicationID;
  }

  public String getInputTopicName() {
    return inputTopicName;
  }

  public void setInputTopicName(String inputTopicName) {
    this.inputTopicName = inputTopicName;
  }

  public String getOutputTopicName() {
    return outputTopicName;
  }

  public void setOutputTopicName(String outputTopicName) {
    this.outputTopicName = outputTopicName;
  }

  public String getStateFileLocation() {
    return stateFileLocation;
  }

  public void setStateFileLocation(String stateFileLocation) {
    this.stateFileLocation = stateFileLocation;
  }

  public String getWordCountStoreName() {
    return wordCountStoreName;
  }

  public void setWordCountStoreName(String wordCountStoreName) {
    this.wordCountStoreName = wordCountStoreName;
  }
}
