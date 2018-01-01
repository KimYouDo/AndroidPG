package kangwon.cse2.kdy.handler;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    int max;
    Button btn;
    ProgressBar bar;
    EditText editText;
    Handler handler = new Handler();
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.button1);
        btn.setOnClickListener(this);

        bar = (ProgressBar) findViewById(R.id.progressBar);
        editText = (EditText) findViewById(R.id.max);


    }

    public void onClick(View view) {
        Thread t = new Thread(new Runnable() {
            String s = editText.getText().toString();

            public void run() {
                try {
                    max = Integer.parseInt(s);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                bar.setMax(max);
                for (i = 0; i < max; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        public void run() {
                            bar.setProgress(i);
                            if(i==max) {
                                TextView txt = (TextView) findViewById(R.id.output);
                                txt.setText("작업완료!");
                                txt.setTextColor(Color.RED);
                            }
                        }
                    });
                }
            }
        });
        t.start();
    }

}
