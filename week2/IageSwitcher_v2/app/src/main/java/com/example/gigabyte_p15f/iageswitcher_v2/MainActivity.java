package com.example.gigabyte_p15f.iageswitcher_v2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView image1;
    ImageView image2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image1 = (ImageView) findViewById(R.id.inageView1);
        image2 = (ImageView) findViewById(R.id.inageView2);

        image1.setVisibility(View.VISIBLE);
        image2.setVisibility(View.INVISIBLE);
    }

    public void onButtonClick(View v){
        switch (v.getId())  {
            case R.id.button1:
                image1.setVisibility(View.VISIBLE);
                image2.setVisibility(View.INVISIBLE);
                break;

            case R.id.button2:
                image1.setVisibility(View.INVISIBLE);
                image2.setVisibility(View.VISIBLE);
        }
    }
}
