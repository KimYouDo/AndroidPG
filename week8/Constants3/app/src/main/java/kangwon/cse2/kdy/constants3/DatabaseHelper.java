package kangwon.cse2.kdy.constants3;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.hardware.SensorManager;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="constants.db";
    private static final int SCHEMA=1;
    static final String TITLE ="title";
    static final String VALUE ="value";
    static final String TABLE="constants";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null,SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 데이터베이스를 새로 만든다.
        db.execSQL("CREATE TABLE constants (title TEXT, value REAL);");

        ContentValues cv=new ContentValues();

        cv.put(TITLE, "Gravity, Death Star I");
        cv.put(VALUE, SensorManager.GRAVITY_DEATH_STAR_I);
        db.insert(TABLE, TITLE, cv);
        // 두 번째 파라미터가 "title"로 주어졌다. 그 의미는 아래와 같다.
        // cv에 값이 전혀 들어 있지 않은 경우에는 행을 하나 추가하되 "title"열에 NULL을 넣어 준다.


        cv.put(TITLE, "Gravity, Earth");
        cv.put(VALUE, SensorManager.GRAVITY_EARTH);
        db.insert(TABLE, TITLE, cv);

        cv.put(TITLE, "Gravity, Jupiter");
        cv.put(VALUE, SensorManager.GRAVITY_JUPITER);
        db.insert(TABLE, TITLE, cv);

        cv.put(TITLE, "Gravity, Mars");
        cv.put(VALUE, SensorManager.GRAVITY_MARS);
        db.insert(TABLE, TITLE, cv);

        cv.put(TITLE, "Gravity, Mercury");
        cv.put(VALUE, SensorManager.GRAVITY_MERCURY);
        db.insert(TABLE, TITLE, cv);

        cv.put(TITLE, "Gravity, Moon");
        cv.put(VALUE, SensorManager.GRAVITY_MOON);
        db.insert(TABLE, TITLE, cv);

        cv.put(TITLE, "Gravity, Neptune");
        cv.put(VALUE, SensorManager.GRAVITY_NEPTUNE);
        db.insert(TABLE, TITLE, cv);

        cv.put(TITLE, "Gravity, Pluto");
        cv.put(VALUE, SensorManager.GRAVITY_PLUTO);
        db.insert(TABLE, TITLE, cv);

        cv.put(TITLE, "Gravity, Saturn");
        cv.put(VALUE, SensorManager.GRAVITY_SATURN);
        db.insert(TABLE, TITLE, cv);

        cv.put(TITLE, "Gravity, Sun");
        cv.put(VALUE, SensorManager.GRAVITY_SUN);
        db.insert(TABLE, TITLE, cv);

        cv.put(TITLE, "Gravity, The Island");
        cv.put(VALUE, SensorManager.GRAVITY_THE_ISLAND);
        db.insert(TABLE, TITLE, cv);

        cv.put(TITLE, "Gravity, Uranus");
        cv.put(VALUE, SensorManager.GRAVITY_URANUS);
        db.insert(TABLE, TITLE, cv);

        cv.put(TITLE, "Gravity, Venus");
        cv.put(VALUE, SensorManager.GRAVITY_VENUS);
        db.insert(TABLE, TITLE, cv);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // oldVersion과 newVersion을 참고하여
        // 데이터베이스 테이블을 수정하거나 행을 수정하고
        // 그에 맞춰 데이터를 새로 채워 넣는 일을 수행함.
        throw new RuntimeException("How did we get here?");
    }
}
