package kangwon.cse2.kdy.messenger;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ChatViewActivity extends AppCompatActivity {


    String name="";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_view);

        Intent intent = getIntent();
        name = intent.getExtras().getString("name");
        Toast.makeText(this,"이름:"+name,Toast.LENGTH_LONG).show();

}

    protected  void restoreState(){
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        if((pref != null)&&(pref.contains(name))){
            String chatLog = pref.getString(name,"");
            ListView tempView = (ListView)findViewById(R.id.messageListView);
            ArrayAdapter adapter = (ArrayAdapter<String>)tempView.getAdapter();
            adapter.clear();
            adapter.addAll(chatLog.split("\t"));
            tempView.setAdapter(adapter);
        }
    }

    protected void saveState(){

        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        ListView tempView = (ListView)findViewById(R.id.messageListView);
        ArrayAdapter adapter = (ArrayAdapter<String>)tempView.getAdapter();
        String chatLog ="";
        for(int i=0; i<adapter.getCount(); i++)
            chatLog+=adapter.getItem(i).toString()+"\t";
        chatLog = chatLog.trim();
        editor.putString(name, chatLog);
        editor.commit();
    }


// 시작과 끝에 실행되는 메소드에서 saveState()와 restoreState()를 실행
    protected void onStart() {
        super.onStart();
        restoreState();
    }

    protected void onPause() {
        saveState();
        super.onPause();
    }

}
