package kangwon.cse.jck.lifecycle_v2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_SECOND_ACTIVITY = 1;
    int ScoreCount =0;
    int LevelCount =0;
    TextView textView;
    TextView textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.textView2);
        textView2 = (TextView)findViewById(R.id.textView3);


        Toast.makeText(this, "0Main.onCreate() 호출됨.", Toast.LENGTH_SHORT).show();
    }

    public void onButton1Click(View v) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivityForResult(intent, REQUEST_SECOND_ACTIVITY);
    }

    public void onButtonScoreClick(View v) {
        ScoreCount ++;
        textView.setText("Score = "+ScoreCount );
    }
    public void onButtonLevelClick(View v) {
        LevelCount ++;
        textView2.setText("Level = "+ LevelCount );
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, "1Main.onRestart() 호출됨.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "2Main.onStart() 호출됨.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "3Main.onResume() 호출됨.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        Toast.makeText(this, "4Main.onPause() 호출됨.", Toast.LENGTH_SHORT).show();
        super.onPause();
    }

    @Override
    protected void onStop() {
        Toast.makeText(this, "5Main.onStop() 호출됨.", Toast.LENGTH_SHORT).show();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Toast.makeText(this, "6Main.onDestroy() 호출됨.", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    /*
     * SecondActivity가 종료할 때(finish 메소드 실행) 시스템에 의해 호출되는 callback 메소드
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SECOND_ACTIVITY) {
            Toast toast = Toast.makeText(this,
                    "Main.onActivityResult() 메소드가 호출됨. 요청코드 : " + requestCode
                    + ", 결과코드 : " + resultCode, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
