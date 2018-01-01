package kangwon.cse2.kdy.tourchevent_v2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    GestureDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        View view = findViewById(R.id.touchView);
        detector = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent motionEvent) {
                textView.append("onDown() 호풀됨.\n");
                return true;
            }

            @Override
            public void onShowPress(MotionEvent e) {
                textView.append("onShowPress() 호풀됨.\n");
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                textView.append("onSingleTapUp() 호풀됨.\n");
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                textView.append("onScroll() 호풀됨 : " + distanceX + ", " + distanceY + "\n");
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                textView.append("onLongPress() 호풀됨.\n");
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                textView.append("onFling() 호풀됨 : " + velocityX + ", " + velocityY + "\n");
                return true;
            }
        });

        View view2 = findViewById(R.id.gestureView);
        view2.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                detector.onTouchEvent(motionEvent);
                return true;
            }
        });

        View.OnTouchListener touchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();

                float curX = event.getX();
                float curY = event.getY();

                if (action == MotionEvent.ACTION_DOWN)
                    textView.append("손가락 눌림 : " + curX + ", " + curY + "\n");
                else if (action == MotionEvent.ACTION_MOVE)
                    textView.append("손가락 움직임임 : " + curX + ", " + curY + "\n");
                else if (action == MotionEvent.ACTION_UP)
                    textView.append("손가락 땜 : " + curX + ", " + curY + "\n");

                return true;
            }
        };
        view.setOnTouchListener(touchListener);
    }
}
