package com.henrysican.rentaspot.models;

public enum BookingStatus {
    CONFIRMED("Confirmed"),
    PENDING("Pending"),
    CUSTOMER_CANCELED("Customer Cancel"),
    HOST_CANCELED("Host Cancel"),
    EXPIRED("Expired");

    private final String name;

    BookingStatus(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}