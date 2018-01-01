package com.example.gigabyte_p15f.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSupportActionBar().setTitle("메뉴");
        final Button button2 = (Button)findViewById(R.id.button2);
        final Button button3 = (Button)findViewById(R.id.button3);
        final Button button4 = (Button)findViewById(R.id.button4);

        View.OnClickListener listener = new View.OnClickListener(){
            public void onClick(View v){
                Intent intent=new Intent();
                if(v== button2)
                    intent.putExtra("message", "고객 관리 완료");
                else if(v== button3)
                    intent.putExtra("message", "매출 관리 완료");
                else
                    intent.putExtra("message", "상품 관리 완료");
                setResult(RESULT_OK, intent);
                finish();
            }
        };

        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
    }
}
