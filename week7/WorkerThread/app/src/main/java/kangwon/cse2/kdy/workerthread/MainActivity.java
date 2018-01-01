package kangwon.cse2.kdy.workerthread;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    TextView clickCountView;
    TextView hiddenCountView;
    int clickCount;
    int hiddenCount;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clickCount = 0;
        hiddenCount = 0;

        clickCountView = (TextView) findViewById(R.id.clickCountView);
        hiddenCountView = (TextView) findViewById(R.id.hiddenCountView);
    }

    public void onIncrement(View c) {
        clickCount++;
        clickCountView.setText("클일 수: " + clickCount);
    }

    public void onShow(View v) {
        hiddenCountView.setText("뱐수 값: " + hiddenCount);
    }

    public void onWork(View v) {
        doHardWork();
    }

    private void doHardWork() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                    hiddenCount++;
                } catch (InterruptedException ex) {
                    Log.e(TAG, "Error in MainActivity", ex);
                }
            }
        };
        Thread myThread = new Thread(runnable);
        myThread.start();

        Toast.makeText(this, "새 스레드 시작",Toast.LENGTH_SHORT).show();
    }
}
