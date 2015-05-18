package com.example.kwamecorp.myalarmclock;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TimePicker;

import com.example.kwamecorp.myalarmclock.helpers.DbHelper;
import com.example.kwamecorp.myalarmclock.models.AlarmModel;

import java.util.List;


public class AlarmListActivity extends AppCompatActivity {

    //region Properties
    private DbHelper dbHelper = new DbHelper(this);
    private List<AlarmModel> alarms;
    //endregion


    //region Overrides

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);

        getSupportActionBar().setTitle(R.string.alarm_list_title);
        loadData();

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

    //endregion


    //region Private Methods

    private void loadData()
    {
        alarms = dbHelper.getAlarms();
        // TODO preencher lista
    }

    //endregion
}
