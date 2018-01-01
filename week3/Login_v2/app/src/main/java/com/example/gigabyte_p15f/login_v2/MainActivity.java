package com.example.gigabyte_p15f.login_v2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText id;
    EditText pw;
    static final int MENU_REQUEST = 1;
    public static final int REQUEST_SECOND_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Login");
        Button button = (Button) findViewById(R.id.button);
        id = (EditText) findViewById(R.id.editText);
        pw = (EditText) findViewById(R.id.editText2);

        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                if (id.getText().toString().trim().length() == 0
                        || pw.getText().toString().trim().length() == 0) {
                    Toast.makeText(MainActivity.this, "아이디와 비밀본호를 입력하세요.", Toast.LENGTH_SHORT).show();
                } else {
                    intent = new Intent(MainActivity.this, Main2Activity.class);
                    startActivityForResult(intent, REQUEST_SECOND_ACTIVITY);
                }
            }
        };
        button.setOnClickListener(listener);
    }

    protected void onPause() {
        super.onPause();
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("name", id.getText().toString());
        editor.commit();

        SharedPreferences pref2 = getSharedPreferences("pref2", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = pref2.edit();
        editor2.putString("name2", pw.getText().toString());
        editor2.commit();
    }

    protected void onResume() {
        super.onResume();
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        if ((pref != null) && (pref.contains("name"))) {
            String name = pref.getString("name", "");
            id.setText(name);
        }

        SharedPreferences pref2 = getSharedPreferences("pref2", Activity.MODE_PRIVATE);
        if ((pref2 != null) && (pref2.contains("name2"))) {
            String name2 = pref2.getString("name2", "");
            pw.setText(name2);
        }
    }


}