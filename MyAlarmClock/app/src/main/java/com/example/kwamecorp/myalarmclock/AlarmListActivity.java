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

import java.util.ArrayList;
import java.util.List;


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

        getSupportActionBar().setTitle(R.string.alarm_list_title);

        listView = (ListView) findViewById(R.id.alarm_list_items);

        adapter = new AlarmAdapter(this, 0);

        listView.setAdapter(adapter);

        reLoadList();

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
            startActivityForResult(intent, REQ_CODE_ALARM_DETAIL);
        }

        return super.onOptionsItemSelected(item);
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
