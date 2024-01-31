package com.example.epaper.renderserver.model;

public class ScheduleEnvelope {

    private String ap;
    private String mac;
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

    public Schedule getSchedule() {
        return schedule;
    }
    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
