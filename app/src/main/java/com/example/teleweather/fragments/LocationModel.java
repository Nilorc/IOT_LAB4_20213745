package com.example.teleweather.fragments;

import com.google.gson.annotations.SerializedName;

public class LocationModel {
    @SerializedName("name")
    private String name;

    @SerializedName("region")
    private String region;

    @SerializedName("country")
    private String country;

    @SerializedName("id")  //puede a posteriror
    private int id;

    public String getName() { return name; }
    public String getRegion() { return region; }
    public String getCountry() { return country; }
    public int getId() { return id; }
}
