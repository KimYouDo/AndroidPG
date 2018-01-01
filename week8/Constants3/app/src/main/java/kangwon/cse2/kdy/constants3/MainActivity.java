package kangwon.cse2.kdy.constants3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getSupportFragmentManager().findFragmentById(android.R.id.content)==null){
            getSupportFragmentManager().beginTransaction().add(android.R.id.content,
                    new ConstantsFragment()).commit();
        }
    }
}
