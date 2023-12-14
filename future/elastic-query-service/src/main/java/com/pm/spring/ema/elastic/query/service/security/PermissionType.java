package com.pm.spring.ema.elastic.query.service.security;

public enum PermissionType {

    READ("READ"), WRITE("WRITE"), ADMIN("ADMIN"), USER("USER"), MANAGER("MANAGER"), OWNER("OWNER");

    private String type;

    PermissionType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
