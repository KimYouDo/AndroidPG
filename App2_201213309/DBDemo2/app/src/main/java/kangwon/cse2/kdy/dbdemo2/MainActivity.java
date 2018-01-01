package kangwon.cse2.kdy.dbdemo2;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SimpleCursorAdapter adapter;
    Cursor cursor;
    DbHelper dbHelper;
    ListView listView;

    protected void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_main);
        this.listView = ((ListView)findViewById(R.id.list));

        this.dbHelper = DbHelper.getInstance(this);
        this.dbHelper.removeData();
        this.dbHelper.fillData();
        this.cursor = this.dbHelper.fetchEntries();
        this.adapter =new SimpleCursorAdapter(this, R.layout.row, cursor, new String[] { "title", "artist" }, new int[] { R.id.title, R.id.value }, 0);

        /*getContext(), R.layout.row,
                current, new String[]{
                DatabaseHelper.TITLE,
                DatabaseHelper.VALUE},
                new int[]{R.id.title, R.id.value},
                0);*/
        this.listView.setAdapter(this.adapter);
        this.listView.setOnItemClickListener(new ItemClickListener());
    }

    class ItemClickListener
            implements AdapterView.OnItemClickListener
    {
        ItemClickListener()
        {
        }

        public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
        {
            String str = ((TextView)paramView.findViewById(R.id.title)).getText().toString();
            MainActivity.this.dbHelper.removeEntry(str);
            MainActivity.this.adapter.changeCursor(MainActivity.this.dbHelper.fetchEntries());
        }
    }
    
}
