package kangwon.cse2.kdy.fragament;

import android.app.Activity;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements SecondFragment.OnShowButtonTouchListener {

    private FirstFragment firstFragment;
    private TextView textView;
    private int countView =0;

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


    protected void onStart() {
        super.onStart();
        firstFragment =
                (FirstFragment) getSupportFragmentManager().findFragmentById(R.id.fragment1_container);
        textView = (TextView) firstFragment.getView().findViewById(R.id.display);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            return;
        }

        firstFragment = new FirstFragment();


        //FirstFragment firstFragment = new FirstFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.fragment1_container, firstFragment).commit();

        SecondFragment secondFragment = new SecondFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.fragment2_container, secondFragment).commit();

        Toast.makeText(this, "Fragments added", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShowButtonTouch(int count) {
        countView=count;
        textView.setText("터치 횟수: " + count);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        textView.setText(savedInstanceState.getString("showCountView"));
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("showCountView", "터치 횟수: " + countView);

    }
}
