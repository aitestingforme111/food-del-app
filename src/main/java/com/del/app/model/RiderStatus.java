package com.del.app.model;

public enum RiderStatus {
  AVAILABLE("AVAILABLE"),
  ASSIGNED("ASSIGNED"),
  OFFLINE("OFFLINE"),
  BUSY("BUSY"); // Can be added for riders on other deliveries

  private final String name;

  RiderStatus(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}