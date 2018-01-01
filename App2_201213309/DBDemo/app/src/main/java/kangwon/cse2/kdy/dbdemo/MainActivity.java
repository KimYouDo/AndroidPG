package kangwon.cse2.kdy.dbdemo;

import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    ListView listView;

    protected void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_main);
        this.listView = ((ListView)findViewById(R.id.list));
        DbHelper dbHelper = DbHelper.getInstance(this);
        dbHelper.removeData();
        dbHelper.fillData();
        SimpleCursorAdapter SimpleCursorAdapter =
                new SimpleCursorAdapter(this, R.layout.row, dbHelper.fetchEntries(), new String[] { "title", "artist" }, new int[] { R.id.title, R.id.value }, 0);

        /*getContext(), R.layout.row,
                current, new String[]{
                DatabaseHelper.TITLE,
                DatabaseHelper.VALUE},
                new int[]{R.id.title, R.id.value},
                0);*/
        this.listView.setAdapter(SimpleCursorAdapter);
    }
}
