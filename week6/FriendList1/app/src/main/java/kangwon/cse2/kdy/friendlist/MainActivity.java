package kangwon.cse2.kdy.friendlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    String[] friendList = {"홍길동", "박보영", "박형식", "지수", "도봉순", "안민력", "인국두"};



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, friendList);
        ListView friendView = (ListView)findViewById(R.id.friendLostView);
        friendView.setAdapter(adapter);
        friendView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }
}
