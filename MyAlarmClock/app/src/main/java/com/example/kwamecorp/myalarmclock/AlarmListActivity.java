package com.example.kwamecorp.myalarmclock;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TimePicker;

import com.example.kwamecorp.myalarmclock.helpers.AlarmAdapter;
import com.example.kwamecorp.myalarmclock.helpers.DbHelper;
import com.example.kwamecorp.myalarmclock.models.AlarmModel;

import java.util.List;


public class AlarmListActivity extends  ActionBarActivity {

    //region Properties

    private DbHelper dbHelper = new DbHelper(this);
    private List<AlarmModel> alarms;
    private AlarmAdapter adapter;

    //endregion


    //region Overrides

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);

        getSupportActionBar().setTitle(R.string.alarm_list_title);

        alarms = dbHelper.getAlarms();

        adapter = new AlarmAdapter(this, alarms);

        ListView listView = (ListView) findViewById(R.id.alarm_list_items);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alarm_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_alarm) {
            Intent intent = new Intent(this,
                    AlarmDetailActivity.class);
            intent.putExtra("id", 0);

            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadList();
    }

    //endregion

    //region Public Methods
    public void openAlarmDetail(int id)
    {
        Intent intent = new Intent(this,
                AlarmDetailActivity.class);

        intent.putExtra("id", id);
        startActivity(intent);
    }

    public void deleteAlarm(int id)
    {
        dbHelper.deleteAlarm(id);

        alarms = dbHelper.getAlarms();

        adapter.setAlarms(alarms);

        adapter.notifyDataSetChanged();



    }

    //endregion


    //region Private Methods

    private void loadList()
    {
        alarms = dbHelper.getAlarms();

        adapter.setAlarms(alarms);
        adapter.notifyDataSetChanged();

    }

    //endregion
}
