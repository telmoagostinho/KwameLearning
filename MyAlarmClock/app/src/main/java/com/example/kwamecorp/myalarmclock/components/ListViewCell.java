package com.example.kwamecorp.myalarmclock.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kwamecorp.myalarmclock.R;
import com.example.kwamecorp.myalarmclock.helpers.DbHelper;
import com.example.kwamecorp.myalarmclock.models.AlarmModel;

/**
 * Created by kwamecorp on 5/20/15.
 */
public class ListViewCell extends FrameLayout{

    private TextView textName;
    private TextView textTime;
    private TextView textId;
    private TextView textDays;
    private ImageButton editBtn, removeBtn;
    private AlarmModel mAlarm;

    public ListViewCell(Context context) {
        super(context);
        init();
    }

    private void init(){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.alarm_list_item, this, true);

        textName = (TextView) findViewById(R.id.alarm_list_item_name);
        textTime = (TextView) findViewById(R.id.alarm_list_item_time);
        textId = (TextView) findViewById(R.id.alarm_list_item_id);
        textDays = (TextView) findViewById(R.id.alarm_list_item_days);
        editBtn = (ImageButton) findViewById(R.id.editBtn);
        removeBtn = (ImageButton) findViewById(R.id.removeBtn);

        editBtn.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //TODO: inject edit code here
                Toast.makeText(getContext(), "TODO: inject edit code here", Toast.LENGTH_LONG).show();
            }
        });

        removeBtn.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (mAlarm != null)
                {
                    // TODO: DbHelper should be singleton to avoid multiple instances inside same app/thread
                    new DbHelper(getContext()).deleteAlarm(mAlarm.getId());
                    // TODO: notify UI that something happened
                }
            }
        });
    }

    public void setData(AlarmModel alarm)
    {
        mAlarm = alarm;
        textName.setText(alarm.getName());
        textTime.setText(alarm.getHour() + " : " + alarm.getMinutes());
        textId.setText(String.valueOf(alarm.getId()));
    }


}


