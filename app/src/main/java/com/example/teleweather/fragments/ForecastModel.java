package com.example.teleweather.fragments;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ForecastModel {

    @SerializedName("forecast")
    private Forecast forecast;

    public Forecast getForecast() {
        return forecast;
    }

    public class Forecast {
        @SerializedName("forecastday")
        private List<ForecastDay> forecastday;

        public List<ForecastDay> getForecastday() {
            return forecastday;
        }
    }

    public class ForecastDay {
        @SerializedName("date")
        private String date;

        @SerializedName("day")
        private Day day;

        public String getDate() {
            return date;
        }

        public Day getDay() {
            return day;
        }
    }

    public class Day {
        @SerializedName("maxtemp_c")
        private float maxtempC;

        @SerializedName("mintemp_c")
        private float mintempC;

        @SerializedName("condition")
        private Condition condition;

        public float getMaxtempC() {
            return maxtempC;
        }

        public float getMintempC() {
            return mintempC;
        }

        public Condition getCondition() {
            return condition;
        }
    }

    public class Condition {
        @SerializedName("text")
        private String text;

        public String getText() {
            return text;
        }
    }
}
