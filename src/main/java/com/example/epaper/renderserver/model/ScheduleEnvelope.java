package com.example.epaper.renderserver.model;

public class ScheduleEnvelope {

    private String ap;
    private String mac;
    private int width;
    private int height;
    private Schedule schedule;

    public String getAp() {
        return ap;
    }
    public void setAp(String ap) {
        this.ap = ap;
    }

    public String getMac() {
        return mac;
    }
    public void setMac(String mac) {
        this.mac = mac;
    }

    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }

    public Schedule getSchedule() {
        return schedule;
    }
    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
