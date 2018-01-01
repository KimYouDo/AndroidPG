package com.example.gigabyte_p15f.lifecycle_v3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Button backBtn = (Button) findViewById(R.id.backBtn);

        // 버튼을 눌렀을 때 메인 액티비티로 돌아갑니다.
        backBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                setResult(RESULT_OK, resultIntent);  // SecondActivity의 메소드
                finish();
            }
        });

        Toast.makeText(this, "7Second.onCreate() 호출됨.", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, "8Second.onRestart() 호출됨.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "9Second.onStart() 호출됨.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "10Second.onResume() 호출됨.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "11Second.onPause() 호출됨.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "12Second.onStop() 호출됨.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "13Second.onDestroy() 호출됨.", Toast.LENGTH_SHORT).show();
    }
}