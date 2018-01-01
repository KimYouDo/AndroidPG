package kangwon.cse2.kdy.freezed;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

   private static final String TAG = MainActivity.class.getSimpleName();

    TextView clickCountView;
    TextView hiddenCountView;
    int clickCount;
    int hiddenCount;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clickCount =0;
        hiddenCount =0;
        clickCountView = (TextView)findViewById(R.id.clickCountView);
        hiddenCountView = (TextView)findViewById(R.id.hiddenCountView);
    }
    public  void onIncrement(View c){
        clickCount++;
        clickCountView.setText("클일 수: "+clickCount);
    }

    public  void onShow(View v){
        doHardwork();
        hiddenCount++;
        hiddenCountView.setText("뱐수 값: "+hiddenCount);
    }

    private void doHardwork(){
        try{
            Thread.sleep(10000);
        }catch (InterruptedException ex){
            Log.e(TAG, "Error in MainActivity", ex);
        }
    }
}
