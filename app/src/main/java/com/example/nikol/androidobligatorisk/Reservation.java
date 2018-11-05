package com.example.nikol.androidobligatorisk;

import java.io.Serializable;

public class Reservation implements Serializable {
        private String fromTimeString;
        private String toTimeString;
        private String userId;
        private String purpose;
        private String[] date;
        private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private int roomId;


    public String getDate() {
       date = fromTimeString.split(" ");
        String justDate = date[0];
        return justDate;
    }




    public String getFromTimeString() {
        return fromTimeString;
    }

    public void setFromTimeString(String fromTimeString) {
        this.fromTimeString = fromTimeString;
    }

    public String getToTimeString() {
        return toTimeString;
    }

    public void setToTimeString(String toTimeString) {
        this.toTimeString = toTimeString;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }



    Reservation(String fromTimeString, String toTimeString, String userId, String purpose, int roomId)
        {
                this.fromTimeString = fromTimeString;
                this.toTimeString = toTimeString;
                this.userId = userId;
                this.purpose = purpose;
                this.roomId = roomId;
        }

    @Override
    public String toString() {
        return "Reserved from " + fromTimeString + " to " + toTimeString;
    }
}
