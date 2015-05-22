package com.example.kwamecorp.myalarmclock;

/**
 * Created by rafa on 22/05/15.
 */
public class AlarmStatus
{
    private int id;
    private String timestamp;
    private String location;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(String timestamp)
    {
        this.timestamp = timestamp;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    @Override
    public String toString()
    {
        return "AlarmStatus{" +
                "id=" + id +
                ", timestamp='" + timestamp + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
