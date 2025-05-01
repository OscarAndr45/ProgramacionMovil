package com.oscarmena.socceropenapi;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class VenuesResponse {
    @SerializedName("data")
    private List<Venue> data;

    public List<Venue> getData() {
        return data;
    }

    public static class Venue {
        @SerializedName("id")
        private int id;

        @SerializedName("name")
        private String name;

        @SerializedName("city_name")
        private String city_name;

        @SerializedName("address")
        private String address;

        @SerializedName("capacity")
        private int capacity;

        // Getters
        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getCity() {
            return city_name;
        }

        public String getAddress() {
            return address;
        }

        public int getCapacity() {
            return capacity;
        }
    }
}