package com.del.app.model;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PLACED("PLACED"),
    ACCEPTED("ACCEPTED"),
    PREPARED("PREPARED"),
    PICKED_UP("PICKED_UP"),
    DELIVERED("DELIVERED"),
    CANCELLED("CANCELLED");

    private final String name;

    OrderStatus(String name) {
        this.name = name;
    }
}