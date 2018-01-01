package com.example.gigabyte_p15f.lifecycle_v4;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_SECOND_ACTIVITY = 1;

    static final String STATE_SCORE = "playerScore";
    static final String STATE_LEVEL = "playerLevel";

    // 액티비티 인스턴스 상태변수
    private int score;
    private int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Local innerclass에서 사용되는 지역변수이므로 final로 선언!
        final TextView scoreTextView = (TextView)findViewById(R.id.scoreTextView);
        final TextView levelTextView = (TextView)findViewById(R.id.levelTextView);

        // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            score = savedInstanceState.getInt(STATE_SCORE);
            level = savedInstanceState.getInt(STATE_LEVEL);
        } else {
            // Probably initialize members with default values for a new instance
        }

        Button button = (Button)findViewById(R.id.scoreButton);
        // Local innerclass
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                score++;
                scoreTextView.setText("Score = " + score);
            }
        });

        button = (Button)findViewById(R.id.levelButton);
        // Local innerclass
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                level++;
                levelTextView.setText("Level = " + level);
            }
        });

    }

    public void onSecondActivityStartButtonClick(View v) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivityForResult(intent, REQUEST_SECOND_ACTIVITY);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, "01Main.onRestart() 호출됨.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "02Main.onStart() 호출됨.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        score = savedInstanceState.getInt(STATE_SCORE);
        level = savedInstanceState.getInt(STATE_LEVEL);
        Toast.makeText(this, "1Main.onRestoreInstanceState() 호출됨.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 열라 게임해서 점수, 레벨 올림!
        Toast.makeText(this, "2Main.onResume() 호출됨.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "3Main.onPause() 호출됨.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
        // Save the user's current game state
        savedInstanceState.putInt(STATE_SCORE, score);
        savedInstanceState.putInt(STATE_LEVEL, level);
        Toast.makeText(this, "4Main.onSaveInstanceState() 호출됨.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "5Main.onStop() 호출됨.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "6Main.onDestroy() 호출됨.", Toast.LENGTH_SHORT).show();
    }

    /*
     * SecondActivity가 종료할 때(finish 메소드 실행) 시스템에 의해 호출되는 callback 메소드
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SECOND_ACTIVITY) {
            Toast toast = Toast.makeText(this,
                    "7Main.onActivityResult() 메소드가 호출됨. 요청코드 : " + requestCode
                            + ", 결과코드 : " + resultCode, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
