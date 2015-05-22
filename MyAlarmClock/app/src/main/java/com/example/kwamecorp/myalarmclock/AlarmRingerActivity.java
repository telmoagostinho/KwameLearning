package com.example.kwamecorp.myalarmclock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.kwamecorp.myalarmclock.helpers.AlarmManagerReceiver;
import com.example.kwamecorp.myalarmclock.helpers.DbHelper;

import java.io.IOException;

public class AlarmRingerActivity extends Activity {

    //region Properties
    private WakeLock mWakeLock;
    private MediaPlayer mMediaPlayer;
    //endregion


    //region Overrides
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_ringer);

        init();
    }

    @Override
    protected void onResume() {
        super.onResume();

       // Set the window to keep screen on
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        // Acquire wakelock
        PowerManager pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
        if (mWakeLock == null) {
            mWakeLock = pm.newWakeLock((PowerManager.FULL_WAKE_LOCK | PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "MyWakeLock");
        }

        if (!mWakeLock.isHeld()) {
            mWakeLock.acquire();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mWakeLock != null && mWakeLock.isHeld()) {
            mWakeLock.release();
        }

        if(mMediaPlayer != null)
        {
            mMediaPlayer.stop();
        }
    }

    //endregion

    //region Private Methods

    private void init()
    {

        Button btnShutDown = (Button) findViewById(R.id.action_shutdown_alarm);
        btnShutDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMediaPlayer.stop();
                finish();
            }
        });

        Button btnSnooze = (Button) findViewById(R.id.action_snooze_alarm);
        btnShutDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMediaPlayer.stop();
                int alarmId = getIntent().getIntExtra("id", 0);
                AlarmManagerReceiver.setAlarm(getApplicationContext(), DbHelper.getInstance(getApplicationContext()).getAlarm(alarmId));
            }
        });



        String ringtone = getIntent().getStringExtra("uri");

        if(ringtone != null && !ringtone.isEmpty())
        {
            mMediaPlayer = new MediaPlayer();
            Uri ringtoneUri = Uri.parse(ringtone);
            try {
                mMediaPlayer.setDataSource(this, ringtoneUri);
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                mMediaPlayer.setVolume(0.1f, 0.1f);
                mMediaPlayer.setLooping(true);
                mMediaPlayer.prepare();
                mMediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }







    }


    //endregion


}
