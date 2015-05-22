package com.example.kwamecorp.myalarmclock.services;

import android.app.IntentService;
import android.content.Intent;

import com.example.kwamecorp.myalarmclock.AlarmRingerActivity;

import java.io.IOException;

/**
 * Created by kwamecorp on 5/15/15.
 */
public class AlarmService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public AlarmService(String name) {
        super(name);
    }
    public AlarmService() {
        super("Alarm service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Intent i = new Intent(getBaseContext(), AlarmRingerActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtras(intent);
        startActivity(i);

    }
}
