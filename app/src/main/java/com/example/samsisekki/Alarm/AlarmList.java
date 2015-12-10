package com.example.samsisekki.Alarm;

import java.util.ArrayList;

public class AlarmList {

    private ArrayList<Alarm> arDate;
    private boolean deleted;

    public AlarmList(ArrayList<Alarm> arDate, boolean deleted) {
        super();
        this.arDate = arDate;
        this.deleted = deleted;
    }

    public ArrayList<Alarm> getArDate() {
        return arDate;
    }

    public void setArDate(ArrayList<Alarm> arDate) {
        this.arDate = arDate;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

}