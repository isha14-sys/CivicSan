package com.mountreach.civicsan.model;

import com.google.gson.annotations.SerializedName;

public class LinkChange {
    @SerializedName("description")
    private String description;

    @SerializedName("time")
    private String time;

    @SerializedName("link")
    private String link;

    // Empty constructor
    public LinkChange() {}

    // Full constructor
    public LinkChange(String description, String time, String link) {
        this.description = description;
        this.time = time;
        this.link = link;
    }

    // Getters and Setters
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }
}