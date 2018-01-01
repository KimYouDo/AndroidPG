package kangwon.cse.jck.myruns3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

class ExerciseDbHelper extends SQLiteOpenHelper {

    // Important table information
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "entry.db";

    private static final String TABLE_NAME_ENTRIES = "entry";
    private static final String TAG = "Storing in Database";

    // Keys for table
    private static final String KEY_ROWID = "_id";
    private static final String KEY_INPUT_TYPE = "input_type";
    private static final String KEY_ACTIVITY_TYPE = "activity_type";
    private static final String KEY_DATE_TIME = "date_time";
    private static final String KEY_DURATION = "duration";
    private static final String KEY_DISTANCE = "distance";
    private static final String KEY_AVG_SPEED = "avg_speed";
    private static final String KEY_CALORIES = "calories";
    private static final String KEY_CLIMB = "climb";
    private static final String KEY_HEARTRATE = "heartrate";
    private static final String KEY_COMMENT = "comment";
    private static final String KEY_PRIVACY = "privacy";
    public static final String KEY_GPS_DATA = "gps";

    // Database creation SQL statement
    private static final String CREATE_TABLE_ENTRIES = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME_ENTRIES + " ("
            + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_INPUT_TYPE + " INTEGER NOT NULL, "
            + KEY_ACTIVITY_TYPE + " INTEGER NOT NULL, "
            + KEY_DATE_TIME + " DATETIME NOT NULL, "
            + KEY_DURATION + " FLOAT, "
            + KEY_DISTANCE + " FLOAT, "
            + KEY_AVG_SPEED + " FLOAT,"
            + KEY_CALORIES + " INTEGER, "
            + KEY_CLIMB + " FLOAT, "
            + KEY_HEARTRATE + " INTEGER, "
            + KEY_COMMENT + " TEXT, "
            + KEY_PRIVACY + " INTEGER, "
            + KEY_GPS_DATA + " BLOB " + ");";

    private static ExerciseDbHelper sInstance;

    static synchronized ExerciseDbHelper getInstance(Context context) {

        // 인스턴스가 없는 경우에만 인스턴스를 새로 만든다.
        // 인스턴스가 이미 있는 경우에는 그것을 반환한다.
        if (sInstance == null) {
            sInstance = new ExerciseDbHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    // Constructor
    private ExerciseDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create table schema if not exists
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ENTRIES);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(ExerciseDbHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ENTRIES);
        onCreate(db);
    }


    // Insert a item given each column value
    long insertEntry(ExerciseEntry entry) {

        // Insert all the values
        ContentValues values = new ContentValues();
        values.put(KEY_INPUT_TYPE,entry.getInputType());
        values.put(KEY_ACTIVITY_TYPE,entry.getActivityType());
        values.put(KEY_DATE_TIME, entry.getDateTime());
        values.put(KEY_DURATION,entry.getDuration());
        values.put(KEY_DISTANCE,entry.getDistance());
        values.put(KEY_AVG_SPEED,entry.getAvgSpeed());
        values.put(KEY_CALORIES,entry.getCalories());
        values.put(KEY_CLIMB,entry.getClimb());
        values.put(KEY_HEARTRATE,entry.getHeartRate());
        values.put(KEY_COMMENT,entry.getComment());

        Gson gson = new Gson();
        values.put(KEY_GPS_DATA,gson.toJson(entry.getLocationList()).getBytes());

        // Insert to database
        SQLiteDatabase database = getWritableDatabase();
        long insertId = database.insert(TABLE_NAME_ENTRIES, null, values);
        return insertId;
    }


    // Remove an entry by giving its index
    synchronized void removeEntry(long rowIndex) {

        SQLiteDatabase database = getWritableDatabase();
        database.delete(TABLE_NAME_ENTRIES, KEY_ROWID
                + " = " + rowIndex, null);
    }


    // Query a specific entry by its index.
    synchronized ExerciseEntry fetchEntryByIndex(long rowId) {
        SQLiteDatabase database = getReadableDatabase();

        Cursor cursor = database.query(TABLE_NAME_ENTRIES, null,
                KEY_ROWID + " = " + rowId, null, null, null, null);
        cursor.moveToFirst();
        ExerciseEntry entry = cursorToExerciseEntry(cursor);
        Log.d("DB에서 읽은 entry", cursorToExerciseEntry(cursor).toString());

        cursor.close();

        return entry;
    }


    // Query the entire table, return all rows
    synchronized ArrayList<ExerciseEntry> fetchEntries() {

        ArrayList<ExerciseEntry> entries = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();

        Cursor cursor = database.query(TABLE_NAME_ENTRIES,
                null, null, null, null, null, null);

        cursor.moveToFirst();

        // Fetch the entries one by one until cursor reaches the end
        while (!cursor.isAfterLast()) {
            ExerciseEntry entry = cursorToExerciseEntry(cursor);
            Log.d("DB에서 읽은 entry", cursorToExerciseEntry(cursor).toString());
            entries.add(entry);
            cursor.moveToNext();
        }

        cursor.close();

        return entries;
    }


    private ExerciseEntry cursorToExerciseEntry(Cursor cursor) {

        ExerciseEntry entry = new ExerciseEntry();
        entry.setId(cursor.getLong(0));
        entry.setInputType(cursor.getInt(1));
        entry.setActivityType(cursor.getInt(2));
        entry.setDateTime(cursor.getLong(3));
        entry.setDuration(cursor.getInt(4));
        entry.setDistance(cursor.getDouble(5));
        entry.setAvgSpeed(cursor.getDouble(6));
        entry.setCalories(cursor.getInt(7));
        entry.setClimb(cursor.getDouble(8));
        entry.setHeartRate(cursor.getInt(9));
        entry.setComment(cursor.getString(10));
        Gson gson = new Gson();
        String json = new String(cursor.getBlob(12));
        Type type = new TypeToken<ArrayList<LatLng>>() {}.getType();
        entry.setLocationList((ArrayList<LatLng>)gson.fromJson(json, type));

        return entry;
    }
}
