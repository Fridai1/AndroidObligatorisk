package com.example.nikol.androidobligatorisk;

import java.io.Serializable;

public class Building implements Serializable {
    private int id;

    public Building(int id, String name, int cityId, String address) {
        this.id = id;
        this.name = name;
        this.cityId = cityId;
        this.address = address;
    }

    private String name;
    private int cityId;
    private String address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getAdresse() {
        return address;
    }

    public void setAdresse(String adresse) {
        this.address = address;
    }


    @Override
    public String toString() {
        return "Bygning " + name + " på " + address;
    }
}
