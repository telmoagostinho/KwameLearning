package com.example.kwamecorp.myalarmclock.models;

import android.net.Uri;

/**
 * Created by kwamecorp on 5/13/15.
 */
public class AlarmModel {

    public static final int SUNDAY = 0;
    public static final int MONDAY = 1;
    public static final int TUESDAY = 2;
    public static final int WEDNESDAY = 3;
    public static final int THURSDAY = 4;
    public static final int FRIDAY = 5;
    public static final int SATURDAY = 6;


    private int id;
    private String name;
    private int hour;
    private int minutes;
    private Uri ringtone;
    private boolean isEnabled;
    private boolean repeatingDays[];

    public AlarmModel() {
        repeatingDays = new boolean[7];
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public Uri getRingtone() {
        return ringtone;
    }

    public void setRingtone(Uri ringtone) {
        this.ringtone = ringtone;
    }

    public boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public void setRepeatingDay(int weekDayIdx, boolean value) {
        repeatingDays[weekDayIdx] = value;
    }

    public boolean getRepeatingDay(int weekDayIdx) {
        return repeatingDays[weekDayIdx];
    }
}
