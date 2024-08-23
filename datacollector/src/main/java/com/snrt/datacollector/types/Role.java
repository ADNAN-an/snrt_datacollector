package com.snrt.datacollector.types;

public enum Role {
    SUPERADMIN,
    ADMIN;

    public String toUpperCase() {
        return name().toUpperCase();
    }
}
