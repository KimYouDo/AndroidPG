package kangwon.cse2.kdy.orientationchange_v2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String data;
    EditText editText;
    static final String STATE = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button)findViewById(R.id.button);
        editText  = (EditText)findViewById(R.id.editText);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data = editText.getText().toString();
                Toast.makeText(getApplicationContext(), "값을 저장했습니다 : " + data, Toast.LENGTH_SHORT).show();
                editText.setText("");
            }
        });

        Button call_button = (Button)findViewById(R.id.call_button);

        call_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "변수에 저장된 값 : "+data, Toast.LENGTH_SHORT).show();
            }
        });



    }

    public void onRestoreInstanceState(Bundle outState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(outState);

        // Restore state members from saved instance
        data = outState.getString(STATE);


    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE, data);

    }
}
