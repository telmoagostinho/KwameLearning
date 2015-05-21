package com.example.kwamecorp.myalarmclock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kwamecorp.myalarmclock.helpers.AlarmAdapter;
import com.example.kwamecorp.myalarmclock.helpers.DbHelper;
import com.example.kwamecorp.myalarmclock.models.AlarmModel;


public class AlarmListActivity extends  ActionBarActivity {

    //region Constants

    private static final int REQ_CODE_ALARM_DETAIL = 1;

    //endregion

    //region Properties

    private DbHelper dbHelper = new DbHelper(this);
    private AlarmAdapter adapter;
    private ListView listView;

    //endregion


    //region Overrides

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);

//        getSupportActionBar().setTitle(R.string.alarm_list_title);

        listView = (ListView) findViewById(R.id.alarm_list_items);

        adapter = new AlarmAdapter(this, 0);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
	        @Override
	        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	        {
		        AlarmModel alarmModel = adapter.getItem(position);
		        openAlarmDetail(alarmModel.getId());
	        }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
	        @Override
	        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
	        {
		        AlarmModel alarmModel = adapter.getItem(position);
		        deleteAlarm(alarmModel.getId());
		        return true;
	        }
        });

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

    }

    @Override
    protected void onResume() {
        super.onResume();

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
        dbHelper.deleteAlarm(id);
        reLoadList();
    }

    //endregion


    //region Private Methods

    private void reLoadList()
    {
        adapter.clear();
        adapter.addAll(dbHelper.getAlarms());
        adapter.notifyDataSetChanged();
    }

    //endregion
}
