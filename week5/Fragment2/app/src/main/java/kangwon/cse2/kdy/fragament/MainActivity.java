package kangwon.cse2.kdy.fragament;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
if(savedInstanceState != null){
    return;
}

        FirstFragment firstFragment = new FirstFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.fragment1_container, firstFragment).commit();

        SecondFragment secondFragment = new SecondFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.fragment2_container, secondFragment).commit();

        Toast.makeText(this, "Fragments added", Toast.LENGTH_SHORT).show();
    }
}
