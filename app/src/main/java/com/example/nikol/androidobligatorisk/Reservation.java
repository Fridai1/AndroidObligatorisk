package com.example.nikol.androidobligatorisk;

public class Reservation {
        private String fromTimeString;
        private String toTimeString;
        private String userId;
        private String purpose;
        private int roomId;

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


}
