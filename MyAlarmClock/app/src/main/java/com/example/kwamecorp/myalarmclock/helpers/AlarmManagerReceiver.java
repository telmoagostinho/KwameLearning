package com.example.kwamecorp.myalarmclock.helpers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by kwamecorp on 5/22/15.
 */import com.example.kwamecorp.myalarmclock.models.AlarmModel;
import com.example.kwamecorp.myalarmclock.services.AlarmService;

import java.util.Calendar;
import java.util.List;

public class AlarmManagerReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        setAllAlarms(context);
    }


    public static void setAlarm(Context context, AlarmModel alarm){
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, alarm.getHour());
        calendar.set(Calendar.MINUTE, alarm.getMinutes());
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pi = setPendingIntent(context, alarm);

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
    }

    public static void cancelAlarm(Context context, AlarmModel alarm){

        PendingIntent pi = setPendingIntent(context, alarm);

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        am.cancel(pi);
    }

    public static void setAllAlarms(Context context){
        cancelAllAlarms(context);

        List<AlarmModel> alarms = DbHelper.getInstance(context).getAlarms();

        if(alarms != null){
            for (AlarmModel alarm : alarms){
                setAlarm(context, alarm);
            }
        }
    }

    public static void cancelAllAlarms(Context context){
        List<AlarmModel> alarms = DbHelper.getInstance(context).getAlarms();

        if(alarms != null){
            for (AlarmModel alarm : alarms){
                cancelAlarm(context, alarm);
            }
        }
    }

    private static PendingIntent setPendingIntent(Context context, AlarmModel alarm)
    {
        Intent i = new Intent(context, AlarmService.class);
        i.putExtra("id", alarm.getId());
        i.putExtra("uri", alarm.getRingtone().toString());

        PendingIntent pi = PendingIntent.getService(context, alarm.getId(), i, PendingIntent.FLAG_UPDATE_CURRENT);

        return pi;

    }
}
