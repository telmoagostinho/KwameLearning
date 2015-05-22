package com.example.kwamecorp.myalarmclock;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.kwamecorp.myalarmclock.helpers.AlarmManagerReceiver;
import com.example.kwamecorp.myalarmclock.helpers.DbHelper;
import com.example.kwamecorp.myalarmclock.models.AlarmModel;
import com.example.kwamecorp.myalarmclock.services.AlarmService;

import java.util.Calendar;
import java.util.Random;


public class AlarmDetailActivity extends AppCompatActivity {

    //region Constants

    private static final int REQ_CODE_PICK_RINGTONE = 1;

    //endregion

    //region Properties
    private AlarmModel alarmModel;

    //region Layout Items

    private TimePicker timePicker;
    private EditText textName;
    private TextView textRingtoneUri;
    private View saveAlarmFAB;

    //endregion

    //endregion

    //region Overrides

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_alarm_detail);

        setLayout();
        setListeners();
    }

    //region Activity Result

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK)
        {
            switch (requestCode)
            {
                case REQ_CODE_PICK_RINGTONE:
                    setRingtone(data);
                    break;
                default:
                    Log.w("KW", "Activity Result default");
                    break;
            }

        }
    }

    //endregion

    //endregion

    //region Private Methods

    private void setLayout()
    {
        textName = (EditText) findViewById(R.id.alarm_details_name);
        textRingtoneUri = (TextView) findViewById(R.id.alarm_detail_current_ringtone);
        timePicker = (TimePicker) findViewById(R.id.alarm_detail_time_picker);
        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        saveAlarmFAB = findViewById(R.id.saveAlarmFAB);

        alarmModel = new AlarmModel();

        int id = getIntent().getExtras().getInt("id");
        if (id > 0) {
            alarmModel = DbHelper.getInstance(this).getAlarm(id);
            bindLayoutFromModel();
        }

    }

    private void setListeners()
    {
        textRingtoneUri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                startActivityForResult(intent, REQ_CODE_PICK_RINGTONE);
            }
        });

        saveAlarmFAB.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                saveAlarm();
                setResult(RESULT_OK);
                finish();
            }
        });

    }

    private void bindLayoutFromModel()
    {
        textName.setText(alarmModel.getName());
        timePicker.setCurrentHour(alarmModel.getHour());
        timePicker.setCurrentMinute(alarmModel.getMinutes());

        // more to do

    }

    private void saveAlarm()
    {
        // Save to DB

        bindModelFromLayout();

        if(alarmModel.getId() > 0)
        {
            DbHelper.getInstance(this).updateAlarm(alarmModel);
        }else
        {
            DbHelper.getInstance(this).createAlarm(alarmModel);
        }

        AlarmManagerReceiver.setAlarm(getApplicationContext(), alarmModel);

    }

    private void bindModelFromLayout()
    {

        alarmModel.setHour(timePicker.getCurrentHour().intValue());
        alarmModel.setMinutes(timePicker.getCurrentMinute().intValue());
        alarmModel.setName(textName.getText().toString());

        // TODO Days

        alarmModel.setIsEnabled(true);

    }

    private void setRingtone(Intent data)
    {
        Uri ringtoneUri =
                data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);

        alarmModel.setRingtone(ringtoneUri);

        textRingtoneUri.setText(RingtoneManager.getRingtone(this, alarmModel.getRingtone()).getTitle(this));
    }

    //endregion
}
