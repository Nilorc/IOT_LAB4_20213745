package com.example.teleweather.fragments;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class FutureForecastModel {

    @SerializedName("location")
    private Location location;

    @SerializedName("forecast")
    private Forecast forecast;

    public Location getLocation() {
        return location;
    }

    public Forecast getForecast() {
        return forecast;
    }

    public class Location {
        @SerializedName("name")
        private String name;

        @SerializedName("region")
        private String region;

        @SerializedName("country")
        private String country;

        public String getName() {
            return name;
        }

        public String getRegion() {
            return region;
        }

        public String getCountry() {
            return country;
        }
    }

    public class Forecast {
        @SerializedName("forecastday")
        private List<ForecastDay> forecastday;

        public List<ForecastDay> getForecastday() {
            return forecastday;
        }
    }

    public class ForecastDay {
        @SerializedName("hour")
        private List<HourData> hour;

        public List<HourData> getHour() {
            return hour;
        }
    }

    public class HourData {
        @SerializedName("time")
        private String time;

        @SerializedName("temp_c")
        private float tempC;

        @SerializedName("humidity")
        private int humidity;

        @SerializedName("chance_of_rain")
        private int chanceOfRain;

        @SerializedName("condition")
        private Condition condition;

        public String getTime() {
            return time;
        }

        public float getTempC() {
            return tempC;
        }

        public int getHumidity() {
            return humidity;
        }

        public int getChanceOfRain() {
            return chanceOfRain;
        }

        public Condition getCondition() {
            return condition;
        }
    }

    public class Condition {
        @SerializedName("text")
        private String text;

        @SerializedName("icon")
        private String icon;  // icon path (needs "https:")

        public String getText() {
            return text;
        }

        public String getIcon() {
            return icon;
        }
    }
}
