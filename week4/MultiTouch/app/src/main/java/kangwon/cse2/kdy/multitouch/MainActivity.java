package kangwon.cse2.kdy.multitouch;

import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {

    private  float[] x = new float[2];
    private  float[] y = new float[2];
    ImageView imageView;
    private float distance;
    float curScale =1.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView)findViewById(R.id.imageView);
    }

    public boolean onTouchEvent(MotionEvent event)
    {
        int pointerCount = event.getPointerCount();

        int action = MotionEventCompat.getActionMasked(event);
        switch(action)
        {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                for(int i=0; i<pointerCount; i++){
                    x[i] = event.getY(i);
                    y[i] = event.getX(i);
                }
                distance = (float)Math.sqrt(Math.pow(x[1] -x[0],2) + Math.pow(y[1]-y[0],2));
                break;
            case MotionEvent.ACTION_MOVE:
                if(pointerCount==2){
                    for(int i=0; i<pointerCount; i++){
                        x[i] = event.getY(i);
                        y[i] = event.getX(i);
                    }
                    float newDistance = (float)Math.sqrt(Math.pow(x[1] -x[0],2) + Math.pow(y[1]-y[0],2));
                    float diffDistance = newDistance - distance;

                    curScale = curScale*(1.0f+(diffDistance*0.01f));

                    imageView.setScaleX(curScale);
                    imageView.setScaleY(curScale);

                    distance = newDistance;
                }
                break;
        }
        return true;
    }
}
