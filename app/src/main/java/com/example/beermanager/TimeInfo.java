package com.example.beermanager;

public class TimeInfo {

    private int ID;
    private String time;

    public TimeInfo(int ID, String time) {
        this.ID = ID;
        this.time = time;
    }
    public TimeInfo() {
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public int getID() {
        return ID;
    }
    public String getTime() {
        return time;
    }

    @Override
    public String toString() {
        return ID + ". pivo jste vypil v " + time;
    }
}
