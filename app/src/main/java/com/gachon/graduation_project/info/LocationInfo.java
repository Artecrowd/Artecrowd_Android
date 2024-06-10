package com.gachon.graduation_project.info;

import java.util.ArrayList;

public class LocationInfo {
    private String locationName;
    private ArrayList<LocationDetail> locations;

    public LocationInfo(String locationName, ArrayList<LocationDetail> locations) {
        this.locationName = locationName;
        this.locations = locations;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public ArrayList<LocationDetail> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<LocationDetail> locations) {
        this.locations = locations;
    }

    public static class LocationDetail {
        private String location;
        private String id;

        public LocationDetail(String location, String id) {
            this.location = location;
            this.id = id;
        }

        public String getLocation() {
            return location;
        }

        public String getId() {
            return id;
        }
    }
}