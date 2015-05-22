package com.example.kwamecorp.myalarmclock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kwamecorp.myalarmclock.components.ListViewCell;
import com.example.kwamecorp.myalarmclock.helpers.AlarmAdapter;
import com.example.kwamecorp.myalarmclock.helpers.DbHelper;
import com.example.kwamecorp.myalarmclock.models.AlarmModel;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class AlarmListActivity extends Activity implements ListViewCell.IListViewCellListener {

    //region Constants

    private static final int REQ_CODE_ALARM_DETAIL = 1;
    private static final String TAG = "ALARMCOCK";

    //endregion

    //region Properties

    private AlarmAdapter adapter;
    private ListView listView;
    private AlarmServiceEndpoint alarmServiceEndpoint;

    //endregion


    //region Overrides

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);

        listView = (ListView) findViewById(R.id.alarm_list_items);

        adapter = new AlarmAdapter(this, 0);

        adapter.setListener(this);

        listView.setAdapter(adapter);

	    findViewById(R.id.addAlarmFAB).setOnClickListener(new View.OnClickListener()
	    {
		    @Override
		    public void onClick(View v)
		    {
			    Intent intent = new Intent(getApplicationContext(), AlarmDetailActivity.class);
			    intent.putExtra("id", 0);
			    startActivityForResult(intent, REQ_CODE_ALARM_DETAIL);
		    }
	    });

        reLoadList();

        new AlarmStatusChecker().start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            reLoadList();
        }
    }

    //endregion

    //region Public Methods

    public void openAlarmDetail(int id)
    {
        Intent intent = new Intent(this,
                AlarmDetailActivity.class);
        intent.putExtra("id", id);
        startActivityForResult(intent, REQ_CODE_ALARM_DETAIL);
    }

    public void deleteAlarm(int id)
    {
        DbHelper.getInstance(this).deleteAlarm(id);
        reLoadList();
    }

    //endregion


    //region Private Methods

    private void reLoadList()
    {
        adapter.clear();
        adapter.addAll(DbHelper.getInstance(this).getAlarms());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void deleteAlarm(AlarmModel alarm) {
        deleteAlarm(alarm.getId());
    }

    @Override
    public void openAlarmDetail(AlarmModel alarm) {
        openAlarmDetail(alarm.getId());
    }

    //endregion
}
