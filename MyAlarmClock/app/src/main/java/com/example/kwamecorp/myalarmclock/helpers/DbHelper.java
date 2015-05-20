package com.example.kwamecorp.myalarmclock.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import com.example.kwamecorp.myalarmclock.models.AlarmModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kwamecorp on 5/18/15.
 */
public class DbHelper extends SQLiteOpenHelper {

    //region Constants
    private static final String DATABASE_NAME = "myalarmclock.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "alarms";
    public static final String COLUMN_NAME_ID = "_id";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_NAME_HOUR = "hour";
    public static final String COLUMN_NAME_MINUTES = "minutes";
    public static final String COLUMN_NAME_RINGTONE = "ringtone";
    public static final String COLUMN_NAME_ENABLED = "enabled";

    private static final String SQL_CREATE_ALARM_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NAME_NAME + " TEXT," +
                    COLUMN_NAME_HOUR + " INTEGER," +
                    COLUMN_NAME_MINUTES + " INTEGER," +
                    COLUMN_NAME_RINGTONE + " TEXT," +
                    COLUMN_NAME_ENABLED + " BOOLEAN" + " )";

    private static final String SQL_DELETE_ALARM_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;


    //endregion

    //region Constructor

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //endregion

    //region Overrides

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ALARM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ALARM_TABLE);
        onCreate(db);
    }

    //endregion

    //region Public Methods (CRUD)

    public List<AlarmModel> getAlarms(){

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME;

        List<AlarmModel> alarmModels = new ArrayList<AlarmModel>();

        Cursor c = db.rawQuery(query, null);

        if(c != null)
        {
            while(c.moveToNext())
            {
                alarmModels.add(mapToModel(c));
            }
        }

        c.close();

        return alarmModels;
    }

    public AlarmModel getAlarm(int id)
    {
        AlarmModel alarm;

        String query =
                "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME_ID + " = " + id;

        SQLiteDatabase db = this.getReadableDatabase();

        if(db != null)
        {
            // check use query instead of rawQuery
            Cursor c = db.rawQuery(query,null);

            if (c != null && c.moveToNext()) {
                alarm =  mapToModel(c);
                c.close();
                return alarm;
            }
        }

        return null;
    }

    public long createAlarm(AlarmModel alarmModel)
    {
        ContentValues contentValues = mapToDb(alarmModel);
        SQLiteDatabase db = this.getWritableDatabase();
        // fechar a DB
        Log.w("DB", "cheguei aqui");
        return db.insert(TABLE_NAME, null, contentValues);
    }

    public long updateAlarm(AlarmModel alarmModel)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = mapToDb(alarmModel);
        return db.update(TABLE_NAME, contentValues, COLUMN_NAME_ID + " = ?", new String[] { String.valueOf(alarmModel.getId()) });
    }

    public int deleteAlarm(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        if (db != null)
        {
            return db.delete(TABLE_NAME, COLUMN_NAME_ID + " = ?", new String[] { String.valueOf(id) });
        }
        return 1; // SQLITE ERROR CODE?
    }

    //endregion

    //region Private Methods

    private AlarmModel mapToModel(Cursor c)
    {
        // guardar num static os columnsIndex
        AlarmModel alarmModel = new AlarmModel();
        alarmModel.setId(c.getInt(c.getColumnIndex(COLUMN_NAME_ID)));
        alarmModel.setName(c.getString(c.getColumnIndex(COLUMN_NAME_NAME)));
        alarmModel.setIsEnabled(c.getInt(c.getColumnIndex(COLUMN_NAME_ENABLED)) == 0 ? false : true);
        alarmModel.setHour(c.getInt(c.getColumnIndex(COLUMN_NAME_HOUR)));
        alarmModel.setMinutes(c.getInt(c.getColumnIndex(COLUMN_NAME_MINUTES)));
        String ringtoneUri = c.getString(c.getColumnIndex(COLUMN_NAME_RINGTONE));
        alarmModel.setRingtone(Uri.parse(ringtoneUri));

        return alarmModel;
    }

    private ContentValues mapToDb(AlarmModel alarmModel)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_NAME, alarmModel.getName());
        contentValues.put(COLUMN_NAME_RINGTONE, alarmModel.getRingtone().toString());
        contentValues.put(COLUMN_NAME_ENABLED, alarmModel.getIsEnabled());
        contentValues.put(COLUMN_NAME_HOUR, alarmModel.getHour());
        contentValues.put(COLUMN_NAME_MINUTES, alarmModel.getMinutes());

        return contentValues;
    }

}
