package com.pm.spring.ema.app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "security.roles")
public class RoleProperties {
  private String user;
  private String admin;
  private String customer;
  private String customerSupport;
  private String storeManager;
  private String marketingManager;
  private String contentManager;
  private String vendor;

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getAdmin() {
    return admin;
  }

  public void setAdmin(String admin) {
    this.admin = admin;
  }

  public String getCustomer() {
    return customer;
  }

  public void setCustomer(String customer) {
    this.customer = customer;
  }

  public String getCustomerSupport() {
    return customerSupport;
  }

  public void setCustomerSupport(String customerSupport) {
    this.customerSupport = customerSupport;
  }

  public String getStoreManager() {
    return storeManager;
  }

  public void setStoreManager(String storeManager) {
    this.storeManager = storeManager;
  }

  public String getMarketingManager() {
    return marketingManager;
  }

  public void setMarketingManager(String marketingManager) {
    this.marketingManager = marketingManager;
  }

  public String getContentManager() {
    return contentManager;
  }

  public void setContentManager(String contentManager) {
    this.contentManager = contentManager;
  }

  public String getVendor() {
    return vendor;
  }

  public void setVendor(String vendor) {
    this.vendor = vendor;
  }
}
