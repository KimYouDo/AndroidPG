package com.example.gigabyte_p15f.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static final int MENU_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Login");
        Button button = (Button)findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
            Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                startActivityForResult(intent, MENU_REQUEST);
        }
    });
}
protected void onActivityResult(int requestCode, int resultCode, Intent data){
    super.onActivityResult(requestCode, resultCode, data);
    if(requestCode == MENU_REQUEST)
        if(data != null){
            String message = data.getStringExtra("message");
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}

}