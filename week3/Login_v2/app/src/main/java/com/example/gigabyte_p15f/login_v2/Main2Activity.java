package com.example.gigabyte_p15f.login_v2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    static final int MENU_REQUEST = 1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSupportActionBar().setTitle("메뉴");

        final Button button2 = (Button) findViewById(R.id.button2);
        final Button button3 = (Button) findViewById(R.id.button3);
        final Button button4 = (Button) findViewById(R.id.button4);
        final Button button5 = (Button) findViewById(R.id.button5);

        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                if (v == button2)
                    intent = new Intent(getApplicationContext(), Main3Activity.class);
                else if (v == button3)
                    intent = new Intent(getApplicationContext(), Main4Activity.class);
                else if (v == button4)
                    intent = new Intent(getApplicationContext(), Main5Activity.class);

                else
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivityForResult(intent, MENU_REQUEST);
            }
        };

        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        button5.setOnClickListener(listener);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MENU_REQUEST)
            if(data != null){
                String message = data.getStringExtra("message");
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
    }
}