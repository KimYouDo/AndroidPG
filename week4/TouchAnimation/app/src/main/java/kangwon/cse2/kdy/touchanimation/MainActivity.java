package kangwon.cse2.kdy.touchanimation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    Animation flowAnim_up;
    Animation flowAnim_down;
    Animation flowAnim_L;
    Animation flowAnim_R;
    GestureDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);

        flowAnim_up = AnimationUtils.loadAnimation(this, R.anim.flow_up);
        flowAnim_R = AnimationUtils.loadAnimation(this, R.anim.flow_r);
        flowAnim_L = AnimationUtils.loadAnimation(this, R.anim.flow_l);
        flowAnim_down = AnimationUtils.loadAnimation(this, R.anim.flow_down);


        detector = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent motionEvent) {
                // textView.append("onDown() 호풀됨.\n");
                return true;
            }

            @Override
            public void onShowPress(MotionEvent e) {
                //textView.append("onShowPress() 호풀됨.\n");
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                if (distanceY > 0&&(Math.abs(distanceY) >Math.abs(distanceX)))
                    imageView.startAnimation(flowAnim_up);
                else if (distanceY<0&&(Math.abs(distanceY) >Math.abs(distanceX)))
                    imageView.startAnimation(flowAnim_down);
                else if (distanceX<0&&(Math.abs(distanceY) <Math.abs(distanceX)))
                    imageView.startAnimation(flowAnim_R);
                else if (distanceX>0&&(Math.abs(distanceY) <Math.abs(distanceX)))
                    imageView.startAnimation(flowAnim_L);
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                // textView.append("onLongPress() 호풀됨.\n");
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                //textView.append("onFling() 호풀됨 : " + velocityX + ", " + velocityY + "\n");
                return true;
            }
        });

    }

    public boolean onTouchEvent(MotionEvent event) {
        detector.onTouchEvent(event);
        return true;
    }
}


