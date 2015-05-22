package com.example.kwamecorp.myalarmclock;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by rafa on 22/05/15.
 */
public interface AlarmServiceEndpoint
{
    public static String URL = "https://panicbtn.herokuapp.com";

    @GET("/alarms/{id}")
    void getAlarmStatus(@Path("id") Integer alarmId, Callback<AlarmStatus> cb);

    @GET("/alarms/{id}")
    AlarmStatus getAlarmStatus(@Path("id") Integer alarmId);


}
