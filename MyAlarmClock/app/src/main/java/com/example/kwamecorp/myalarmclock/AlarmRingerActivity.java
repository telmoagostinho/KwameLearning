package com.example.kwamecorp.myalarmclock;

import com.example.kwamecorp.myalarmclock.helpers.AlarmManagerReceiver;
import com.example.kwamecorp.myalarmclock.helpers.DbHelper;
import com.example.kwamecorp.myalarmclock.services.AlarmService;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import java.io.IOException;

public class AlarmRingerActivity extends Activity {

    //region Properties
    private WakeLock mWakeLock;
    private MediaPlayer mMediaPlayer;
    private String mRingtone;
    private int mId;
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

        if(isAlarmStillRunning()){
            Intent i = new Intent(this, AlarmService.class);
            i.putExtra("id", mId);
            i.putExtra("uri", mRingtone);

            PendingIntent pi = PendingIntent.getService(this, mId, i,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5000, pi);
        }
    }

    private boolean isAlarmStillRunning() {
        return false;
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



        mRingtone = Settings.System.DEFAULT_RINGTONE_URI.toString(); // getIntent().getStringExtra("uri");
        mId = getIntent().getIntExtra("id", 0);

        if(mRingtone != null && !mRingtone.isEmpty())
        {
            mMediaPlayer = new MediaPlayer();
            Uri ringtoneUri = Uri.parse(mRingtone);
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
