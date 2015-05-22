package com.example.kwamecorp.myalarmclock.components;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kwamecorp.myalarmclock.R;
import com.example.kwamecorp.myalarmclock.helpers.AlarmAdapter;
import com.example.kwamecorp.myalarmclock.helpers.DbHelper;
import com.example.kwamecorp.myalarmclock.models.AlarmModel;

/**
 * Created by kwamecorp on 5/20/15.
 */
public class ListViewCell extends FrameLayout{

    private TextView textName;
    private TextView textTime;
    private TextView textId;
    private TextView textDayMonday;
    private TextView textDayTuesday;
    private TextView textDayWednesday;
    private TextView textDayThursday;
    private TextView textDayFriday;
    private TextView textDaySaturday;
    private TextView textDaySunday;

    private ImageButton editBtn, removeBtn;
    private AlarmModel mAlarm;
    private IListViewCellListener listener;

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
        textDayMonday = (TextView) findViewById(R.id.alarm_list_item_monday);
        textDayTuesday = (TextView) findViewById(R.id.alarm_list_item_tuesday);
        textDayWednesday = (TextView) findViewById(R.id.alarm_list_item_wednesday);
        textDayThursday = (TextView) findViewById(R.id.alarm_list_item_thursday);
        textDayFriday = (TextView) findViewById(R.id.alarm_list_item_friday);
        textDaySaturday = (TextView) findViewById(R.id.alarm_list_item_saturday);
        textDaySunday= (TextView) findViewById(R.id.alarm_list_item_sunday);
        editBtn = (ImageButton) findViewById(R.id.editBtn);
        removeBtn = (ImageButton) findViewById(R.id.removeBtn);

        editBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.openAlarmDetail(mAlarm);
            }
        });

        removeBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAlarm != null) {
                    // TODO: confirm box
                    listener.deleteAlarm(mAlarm);
                    // TODO: notify UI that something happened
                }
            }
        });
    }

    public void setData(AlarmModel alarm)
    {
        mAlarm = alarm;
        textName.setText(alarm.getName());
        textTime.setText(String.format("%02d : %02d", alarm.getHour(), alarm.getMinutes()));
        textId.setText(String.valueOf(alarm.getId()));
        changeDayColor(textDayMonday, alarm.getRepeatingDay(AlarmModel.MONDAY));
        changeDayColor(textDayTuesday, alarm.getRepeatingDay(AlarmModel.TUESDAY));
        changeDayColor(textDayWednesday, alarm.getRepeatingDay(AlarmModel.WEDNESDAY));
        changeDayColor(textDayThursday, alarm.getRepeatingDay(AlarmModel.THURSDAY));
        changeDayColor(textDayFriday, alarm.getRepeatingDay(AlarmModel.FRIDAY));
        changeDayColor(textDaySaturday, alarm.getRepeatingDay(AlarmModel.SATURDAY));
        changeDayColor(textDaySunday, alarm.getRepeatingDay(AlarmModel.SUNDAY));
    }


    public void setListener(IListViewCellListener listener) {
        this.listener = listener;
    }

    public interface IListViewCellListener
    {
        void deleteAlarm(AlarmModel alarm);

        void openAlarmDetail(AlarmModel alarm);
    }

    private void changeDayColor(TextView view, boolean isEnabled) {
        if (isEnabled) {
            view.setTextColor(Color.GREEN);
        } else {
            view.setTextColor(Color.BLACK);
        }
    }
}


