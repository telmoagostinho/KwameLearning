package com.example.kwamecorp.myalarmclock.helpers;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.kwamecorp.myalarmclock.AlarmListActivity;
import com.example.kwamecorp.myalarmclock.R;
import com.example.kwamecorp.myalarmclock.components.ListViewCell;
import com.example.kwamecorp.myalarmclock.models.AlarmModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kwamecorp on 5/19/15.
 */
public class AlarmAdapter extends ArrayAdapter<AlarmModel> {

    private final Context context;
    private ListViewCell.IListViewCellListener listener;


    public AlarmAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = new ListViewCell(this.context);
            ((ListViewCell)convertView).setListener(this.listener);
        }

        ((ListViewCell)convertView).setData(getItem(position));

        return convertView;
    }

    public void setListener(ListViewCell.IListViewCellListener listener)
    {
        this.listener = listener;
    }



}


