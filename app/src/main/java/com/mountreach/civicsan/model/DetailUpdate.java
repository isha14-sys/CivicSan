package com.mountreach.civicsan.model;

import com.google.gson.annotations.SerializedName;

public class DetailUpdate {
    @SerializedName("description")
    private String description;

    @SerializedName("time")
    private String time;

    @SerializedName("details")
    private String details;

    // Empty constructor
    public DetailUpdate() {}

    // Full constructor
    public DetailUpdate(String description, String time, String details) {
        this.description = description;
        this.time = time;
        this.details = details;
    }

    // Getters and Setters
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
}