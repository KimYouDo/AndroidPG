package kangwon.cse2.kdy.listdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

        ListView listView;

    protected void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_main);
        this.listView = ((ListView)findViewById(R.id.list));
        ArrayAdapter localArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Songs.title);
        //adapter = new ArrayAdapter<String>(, android.R.layout.simple_list_item_1, contactsList);
        this.listView.setAdapter(localArrayAdapter);
    }
}