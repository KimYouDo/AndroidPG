package kangwon.cse2.kdy.postdemo2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        Button button = (Button) findViewById(R.id.startButton);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (i = 0; i < 5; i++) {
                            textView.post(new Runnable() {
                                public void run() {
                                    textView.setText("처리 중..." + i * 20 + "%");
                                }
                            });

                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                            }
                        }
                        textView.post(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText("완료.. 100%");
                            }
                        });
                    }
                });
                t.start();
            }
        });
    }
}
