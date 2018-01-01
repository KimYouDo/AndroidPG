package kangwon.cse2.kdy.constants1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
