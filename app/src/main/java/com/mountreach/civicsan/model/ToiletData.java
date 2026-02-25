package com.mountreach.civicsan.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ToiletData {
    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("status")
    private String status;

    @SerializedName("public_toilets")
    private List<PublicToilet> publicToilets;

    @SerializedName("notices")
    private List<Notice> notices;

    @SerializedName("link_changes")
    private List<LinkChange> linkChanges;

    @SerializedName("details_updates")
    private List<DetailUpdate> detailsUpdates;

    @SerializedName("costs_admin")
    private String costsAdmin;

    @SerializedName("last_updated")
    private String lastUpdated;

    // Empty constructor (required for Gson)
    public ToiletData() {}

    // Full constructor with all fields
    public ToiletData(String id, String title, String status,
                      List<PublicToilet> publicToilets,
                      List<Notice> notices,
                      List<LinkChange> linkChanges,
                      List<DetailUpdate> detailsUpdates,
                      String costsAdmin,
                      String lastUpdated) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.publicToilets = publicToilets;
        this.notices = notices;
        this.linkChanges = linkChanges;
        this.detailsUpdates = detailsUpdates;
        this.costsAdmin = costsAdmin;
        this.lastUpdated = lastUpdated;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PublicToilet> getPublicToilets() {
        return publicToilets;
    }

    public void setPublicToilets(List<PublicToilet> publicToilets) {
        this.publicToilets = publicToilets;
    }

    public List<Notice> getNotices() {
        return notices;
    }

    public void setNotices(List<Notice> notices) {
        this.notices = notices;
    }

    public List<LinkChange> getLinkChanges() {
        return linkChanges;
    }

    public void setLinkChanges(List<LinkChange> linkChanges) {
        this.linkChanges = linkChanges;
    }

    public List<DetailUpdate> getDetailsUpdates() {
        return detailsUpdates;
    }

    public void setDetailsUpdates(List<DetailUpdate> detailsUpdates) {
        this.detailsUpdates = detailsUpdates;
    }

    public String getCostsAdmin() {
        return costsAdmin;
    }

    public void setCostsAdmin(String costsAdmin) {
        this.costsAdmin = costsAdmin;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    // Optional: Add toString() method for debugging
    @Override
    public String toString() {
        return "ToiletData{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", status='" + status + '\'' +
                ", publicToilets=" + (publicToilets != null ? publicToilets.size() : 0) +
                ", notices=" + (notices != null ? notices.size() : 0) +
                ", linkChanges=" + (linkChanges != null ? linkChanges.size() : 0) +
                ", detailsUpdates=" + (detailsUpdates != null ? detailsUpdates.size() : 0) +
                ", costsAdmin='" + costsAdmin + '\'' +
                ", lastUpdated='" + lastUpdated + '\'' +
                '}';
    }
}