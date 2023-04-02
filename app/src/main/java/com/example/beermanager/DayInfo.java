package com.example.beermanager;

public class DayInfo{

    private int ID;
    private String date;
    private int goal;
    private int current;
    private boolean isLast;

    public DayInfo(int ID, String date, int goal, int current, boolean isLast) {
        this.ID = ID;
        this.date = date;
        this.goal = goal;
        this.current = current;
        this.isLast = isLast;
    }
    public DayInfo() {
    }
    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public int getGoal() {
        return goal;
    }
    public void setGoal(int goal) {
        this.goal = goal;
    }
    public int getCurrent() {
        return current;
    }
    public void setCurrent(int current) {
        this.current = current;
    }
    public boolean isLast() {
        return isLast;
    }
    public void setLast(boolean last) {
        isLast = last;
    }

    public boolean getTrue(){
        if (current >= goal)
                return true;
        else
            return false;
    }

    @Override
    public String toString() {
        return "Dne " + date + " jste vypil " + current + " piv. " + " Váš cíl byl " + goal;
    }
}
