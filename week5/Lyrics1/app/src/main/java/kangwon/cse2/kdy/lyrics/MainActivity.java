package kangwon.cse2.kdy.lyrics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView  lyricsTextView;
    private String text1;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, SongData.Title) ;
        lyricsTextView = (TextView) findViewById(R.id.lyricsTextView);


        ListView listView = (ListView) findViewById(R.id.titlesList);
        listView.setAdapter(adapter);

        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id){
                text1 = SongData.Lyrics[position];
                onSaveInstanceState(new Bundle());

                lyricsTextView.setText(SongData.Lyrics[position]);
            }
        });
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState); // 반드시 호출해 주세요.
        outState.putString("text",text1);
    }
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        lyricsTextView.setText(savedInstanceState.getString("text"));
        text1 = savedInstanceState.getString("text");
    }

}
