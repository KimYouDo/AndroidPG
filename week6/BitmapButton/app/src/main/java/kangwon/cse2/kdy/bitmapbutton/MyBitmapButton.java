package kangwon.cse2.kdy.bitmapbutton;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.MotionEvent;


public class MyBitmapButton extends AppCompatButton{

    public MyBitmapButton(Context context){
        super(context);
        init();
    }

    public MyBitmapButton(Context context, AttributeSet atts){
        super(context, atts);
        init();
    }

    private void init(){
        setBackgroundResource(R.drawable.bitmap_button_normal);

        setTextColor(Color.WHITE);
        setTextSize(getResources().getDimension(R.dimen.text_size));
        setTypeface(Typeface.DEFAULT_BOLD);
    }

    public boolean onTouchEvent(MotionEvent event){
        int action = event.getAction();

        if(action == MotionEvent.ACTION_DOWN){
            setBackgroundResource(R.drawable.bitmap_button_clicked);
            invalidate();
        }
        if(action== MotionEvent.ACTION_UP){
            init();
            invalidate();
        }

        return true;
    }
}




