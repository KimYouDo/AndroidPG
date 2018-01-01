package kangwon.cse.jck.myruns3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class ExerciseDbHelper extends SQLiteOpenHelper {

    // Important table information
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "entry.db";
    public static final String TABLE_NAME_ENTRIES = "entry";
    private static final String TAG = "Storing in Database";

    // Keys for table
    public static final String KEY_ROWID = "_id";
    public static final String KEY_INPUT_TYPE = "input_type";
    public static final String KEY_ACTIVITY_TYPE = "activity_type";
    public static final String KEY_DATE_TIME = "date_time";
    public static final String KEY_DURATION = "duration";
    public static final String KEY_DISTANCE = "distance";
    public static final String KEY_AVG_PACE = "avg_pace";
    public static final String KEY_AVG_SPEED = "avg_speed";
    public static final String KEY_CALORIES = "calories";
    public static final String KEY_CLIMB = "climb";
    public static final String KEY_HEARTRATE = "heartrate";
    public static final String KEY_COMMENT = "comment";
    public static final String KEY_PRIVACY = "privacy";
    public static final String KEY_GPS_DATA = "gps";


    // Database creation SQL statement
    public static final String CREATE_TABLE_ENTRIES = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME_ENTRIES + " ("
            + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_INPUT_TYPE + " INTEGER NOT NULL, "
            + KEY_ACTIVITY_TYPE + " INTEGER NOT NULL, "
            + KEY_DATE_TIME + " DATETIME NOT NULL, "
            + KEY_DURATION + " FLOAT, "
            + KEY_DISTANCE + " FLOAT, "
            + KEY_AVG_PACE + " FLOAT, "
            + KEY_AVG_SPEED + " FLOAT,"
            + KEY_CALORIES + " INTEGER, "
            + KEY_CLIMB + " FLOAT, "
            + KEY_HEARTRATE + " INTEGER, "
            + KEY_COMMENT + " TEXT, "
            + KEY_PRIVACY + " INTEGER, "
            + KEY_GPS_DATA + " BLOB " + ");";

    private ExerciseDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static ExerciseDbHelper sinstence;

    public static ExerciseDbHelper getInstance(Context context) {
        if (sinstence == null) {
            sinstence = new ExerciseDbHelper(context.getApplicationContext());
        }
        return sinstence;
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(ExerciseDbHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ENTRIES);
        onCreate(db);
    }

    public long insertEntry(ExerciseEntry entry) {

        // Insert all the values
        ContentValues values = new ContentValues();
        values.put(KEY_INPUT_TYPE,entry.getmInputType());
        values.put(KEY_ACTIVITY_TYPE,entry.getmActivityType());
        values.put(KEY_DATE_TIME, entry.getmDateTime());
        values.put(KEY_DURATION,entry.getmDuration());
        values.put(KEY_DISTANCE,entry.getmDistance());
        //values.put(KEY_AVG_PACE,entry.getmAvgPace());
        values.put(KEY_AVG_SPEED,entry.getmAvgSpeed());
        values.put(KEY_CALORIES,entry.getmCalorie());
        values.put(KEY_CLIMB,entry.getmClimb());
        values.put(KEY_HEARTRATE,entry.getmHeartRate());
        values.put(KEY_COMMENT,entry.getmComment());


        // Insert to database
        SQLiteDatabase database = getWritableDatabase();
        long insertId = database.insert(TABLE_NAME_ENTRIES, null, values);
        database.close();
        return insertId;
    }

    public void removeEntry(long paramLong)
    {
        SQLiteDatabase localSQLiteDatabase = getWritableDatabase();
        localSQLiteDatabase.delete("entry", "_id = " + paramLong, null);
        localSQLiteDatabase.close();
    }

    public ArrayList<ExerciseEntry> fetchEntries()
    {
        ArrayList localArrayList = new ArrayList();
        SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
        Cursor localCursor = localSQLiteDatabase.query("entry", null, null, null, null, null, null);
        localCursor.moveToFirst();
        while (!localCursor.isAfterLast())
        {
            ExerciseEntry localExerciseEntry = cursorToExerciseEntry(localCursor);
            Log.d("Storing in Database", cursorToExerciseEntry(localCursor).toString());
            localArrayList.add(localExerciseEntry);
            localCursor.moveToNext();
        }
        localCursor.close();
        localSQLiteDatabase.close();
        return localArrayList;
    }

    private ExerciseEntry cursorToExerciseEntry(Cursor paramCursor)
    {
        ExerciseEntry localExerciseEntry = new ExerciseEntry();
        localExerciseEntry.setId(paramCursor.getLong(0));
        localExerciseEntry.setInputType(paramCursor.getInt(1));
        localExerciseEntry.setActivityType(paramCursor.getInt(2));
        localExerciseEntry.setDateTime(paramCursor.getLong(3));
        localExerciseEntry.setDuration(paramCursor.getInt(4));
        localExerciseEntry.setDistance(paramCursor.getDouble(5));
        localExerciseEntry.setAvgSpeed(paramCursor.getDouble(7));
        localExerciseEntry.setCalorie(paramCursor.getInt(8));
        localExerciseEntry.setClimb(paramCursor.getDouble(9));
        localExerciseEntry.setHeartRate(paramCursor.getInt(10));
        localExerciseEntry.setComment(paramCursor.getString(11));
        return localExerciseEntry;
    }

    public ExerciseEntry fetchEntryByIndex(long paramLong)
    {
        SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
        Cursor localCursor = localSQLiteDatabase.query("entry", null, "_id = " + paramLong, null, null, null, null);
        localCursor.moveToFirst();
        ExerciseEntry localExerciseEntry = cursorToExerciseEntry(localCursor);
        Log.d("Storing in Database", cursorToExerciseEntry(localCursor).toString());
        localCursor.close();
        localSQLiteDatabase.close();
        return localExerciseEntry;
    }
}
