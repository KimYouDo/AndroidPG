package kangwon.cse2.kdy.touchevent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.textView);
        View view = findViewById(R.id.touchView);

        View.OnTouchListener touchListener = new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();

                float curX = event.getX();
                float curY = event.getY();

                if(action==MotionEvent.ACTION_DOWN)
                    textView.append("손가락 눌림 : "+curX+", "+curY+"\n");
                else if(action==MotionEvent.ACTION_MOVE)
                    textView.append("손가락 움직임임 : "+curX+", "+curY+"\n");
               else if(action==MotionEvent.ACTION_UP)
                    textView.append("손가락 땜 : "+curX+", "+curY+"\n");

                return true;
            }
        };
        view.setOnTouchListener(touchListener);
    }
}
