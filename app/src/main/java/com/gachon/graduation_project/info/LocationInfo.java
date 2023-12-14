package com.gachon.graduation_project.info;

import java.util.ArrayList;

public class LocationInfo {
    private String locationName;
    private ArrayList<String> locations;

    public LocationInfo(String locationName, ArrayList<String> locations) {
        this.locationName = locationName;
        this.locations = locations;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public ArrayList<String> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<String> locations) {
        this.locations = locations;
    }
}
