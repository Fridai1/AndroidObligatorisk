package com.example.nikol.androidobligatorisk;

import java.io.Serializable;

public class Room implements Serializable {

    private int id;
    private String name;
    private String description;
    private int capacity;
    private String remarks;
    private int buildingId;

    public Room(int id, String description, int capacity, String remarks, int buildingId, String name) {
        this.id = id;
        this.description = description;
        this.capacity = capacity;
        this.remarks = remarks;
        this.buildingId = buildingId;
        this.name = name;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
