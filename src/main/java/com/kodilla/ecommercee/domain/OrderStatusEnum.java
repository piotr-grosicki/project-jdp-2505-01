package com.kodilla.ecommercee.domain;

public enum OrderStatusEnum {
    NEW("New"),
    PROCESSING("Processing"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled");

    private final String status;

    OrderStatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
