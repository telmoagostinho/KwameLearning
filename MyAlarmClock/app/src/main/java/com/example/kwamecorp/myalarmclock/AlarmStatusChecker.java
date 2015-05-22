package com.example.kwamecorp.myalarmclock;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by rafa on 22/05/15.
 */
public class AlarmStatusChecker
{
    private AlarmStatus mCurrentStatus;
    private Context mContext;
    private AlarmManager mAlarmManager;
    private AlarmStatusCheckerCallback mListener;


    public void start(Context context) {
        if (mContext == null || !mContext.equals(context)) {
            mContext = context;
            mAlarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        }

        retrieveStatus();
    }


    public void stop() {

    }

    public void setListener(AlarmStatusCheckerCallback listener) {
        this.mListener = listener;
    }

    public interface AlarmStatusCheckerCallback {
        void onButtonPressed();
    }

    protected void retrieveStatus() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(AlarmServiceEndpoint.URL)
                .build();

        AlarmServiceEndpoint service = restAdapter.create(AlarmServiceEndpoint.class);

        service.getAlarmStatus(2687, new Callback<AlarmStatus>()
        {
            @Override
            public void success(AlarmStatus alarmStatus, Response response)
            {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date strDate = null;
                try
                {
                    strDate = sdf.parse(alarmStatus.getTimestamp());
                } catch (ParseException e)
                {
                    Log.e("LOG", "burrei a cueca", e);

                }

                // Log.d("LOG", "last known button pressed timestamp: " + strDate.toString());

                long diff = Math.abs(System.currentTimeMillis() - strDate.getTime());

                if (diff >= 5000) {
                    retrieveStatus();
                } else {
                    mListener.onButtonPressed();
                }

            }

            @Override
            public void failure(RetrofitError error)
            {
                retrieveStatus();
            }
        });
    }
}
