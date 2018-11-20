package com.uottawa.runnan.seg_deliberable1.Model;

public class Availability extends ServiceProvider{
    public Availability(String dates, String time, String name) {
        this.dates = dates;
        this.time = time;
        this.name = name;
    }

    @Override
    public String getDates() {
        return dates;
    }

    @Override
    public void setDates(String dates) {
        this.dates = dates;
    }

    @Override
    public String getTime() {
        return time;
    }

    @Override
    public void setTime(String time) {
        this.time = time;
    }

    String dates;
    String time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;


    public Availability(String dates, String time) {
        this.dates = dates;
        this.time = time;
    }
}
