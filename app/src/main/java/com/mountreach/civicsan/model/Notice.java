package com.mountreach.civicsan.model;

import com.google.gson.annotations.SerializedName;

public class Notice {
    @SerializedName("message")
    private String message;

    @SerializedName("priority")
    private String priority;

    @SerializedName("timestamp")
    private String timestamp;

    // Empty constructor
    public Notice() {}

    // Full constructor
    public Notice(String message, String priority, String timestamp) {
        this.message = message;
        this.priority = priority;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}