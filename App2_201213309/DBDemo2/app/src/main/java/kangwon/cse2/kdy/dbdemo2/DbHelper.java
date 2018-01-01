package kangwon.cse2.kdy.dbdemo2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

class DbHelper extends SQLiteOpenHelper {

    // Important table information
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "song_titles.db";

    private static final String TABLE_NAME = "titles";

    // Keys for table
    private static final String KEY_ROWID = "_id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_ARTIST = "artist";

    // Database creation SQL statement
    private static final String CREATE_TABLE_ENTRIES = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME + " ("
            + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_TITLE + " TEXT NOT NULL, "
            + KEY_ARTIST + " TEXT NOT NULL" + ");";

    private static DbHelper sInstance;

    static synchronized DbHelper getInstance(Context context) {

        // 인스턴스가 없는 경우에만 인스턴스를 새로 만든다.
        // 인스턴스가 이미 있는 경우에는 그것을 반환한다.
        if (sInstance == null) {
            sInstance = new DbHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    // Constructor
    private DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create table schema if not exists
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DbHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Songs.title, Sonss.artist 배열의 데이터를 데이터베이스 테이블에 채워준다.
    public void fillData() {
        for (int i = 0; i < Songs.artist.length; i++) {
            ContentValues values = new ContentValues();
            values.put(KEY_TITLE, Songs.title[i]);
            values.put(KEY_ARTIST, Songs.artist[i]);
            insertEntry(values);
        }
    }

    // 데이터베이스 테이블의 모든 데이터를 삭제한다.
    public void removeData() {
        SQLiteDatabase database = getWritableDatabase();
        database.delete(TABLE_NAME, null, null);
    }

    // Insert a item given each column value
    synchronized long insertEntry(ContentValues values) {
        SQLiteDatabase database = getWritableDatabase();
        long insertId = database.insert(TABLE_NAME, null, values);
        return insertId;
    }

    // Remove an entry by giving its index
    synchronized void removeEntry(long rowIndex) {

        SQLiteDatabase database = getWritableDatabase();
        database.delete(TABLE_NAME, KEY_ROWID
                + " = " + rowIndex, null);
    }

    // Remove an entry by giving its title
    synchronized void removeEntry(String title) {

        SQLiteDatabase database = getWritableDatabase();
        database.delete(TABLE_NAME, KEY_TITLE
                + " = ?", new String[] {title});
    }

    // Query a specific entry by its index.
    synchronized Cursor fetchEntryByIndex(long rowId) {
        SQLiteDatabase database = getReadableDatabase();

        return database.query(TABLE_NAME, null,
                KEY_ROWID + " = " + rowId, null, null, null, null);
    }


    // Query the entire table, return all rows
    synchronized Cursor fetchEntries() {
        SQLiteDatabase database = getReadableDatabase();

        return database.query(TABLE_NAME,
                null, null, null, null, null, null);
    }

}
