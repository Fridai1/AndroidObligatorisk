package com.example.nikol.androidobligatorisk;

import java.io.Serializable;

public class Building implements Serializable {
    private int id;

    public Building(int id, String name, int cityId, String adresse) {
        this.id = id;
        this.name = name;
        this.cityId = cityId;
        this.adresse = adresse;
    }

    private String name;
    private int cityId;
    private String adresse;

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
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }


    @Override
    public String toString() {
        return name;
    }
}
