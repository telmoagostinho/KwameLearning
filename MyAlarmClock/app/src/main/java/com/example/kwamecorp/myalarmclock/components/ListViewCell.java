package com.example.kwamecorp.myalarmclock.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.kwamecorp.myalarmclock.R;
import com.example.kwamecorp.myalarmclock.models.AlarmModel;

/**
 * Created by kwamecorp on 5/20/15.
 */
public class ListViewCell extends FrameLayout {

    private TextView textName;
    private TextView textTime;
    private TextView textId;


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

    }

    public void setData(AlarmModel alarm)
    {
        textName.setText(alarm.getName());
        textTime.setText(alarm.getHour() + " : " + alarm.getMinutes());
        textId.setText(String.valueOf(alarm.getId()));
    }
}
