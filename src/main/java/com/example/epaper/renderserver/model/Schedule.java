package com.example.epaper.renderserver.model;

import java.util.List;

/**
 *
 * @author merten
 */
public class Schedule {

    private String room;
    private String date;
    private List<String[]> entries;

    /**
     * @return the room
     */
    public String getRoom() {
        return room;
    }

    /**
     * @param room the room to set
     */
    public void setRoom(String room) {
        this.room = room;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the entries
     */
    public List<String[]> getEntries() {
        return entries;
    }

    /**
     * @param entries the entries to set
     */
    public void setEntries(List<String[]> entries) {
        this.entries = entries;
    }

}
