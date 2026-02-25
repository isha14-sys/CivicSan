package com.mountreach.civicsan.model;

import com.google.gson.annotations.SerializedName;

public class PublicToilet {
    @SerializedName("name")
    private String name;

    @SerializedName("status")
    private String status;

    @SerializedName("location")
    private String location;

    // Empty constructor
    public PublicToilet() {}

    // Full constructor
    public PublicToilet(String name, String status, String location) {
        this.name = name;
        this.status = status;
        this.location = location;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}