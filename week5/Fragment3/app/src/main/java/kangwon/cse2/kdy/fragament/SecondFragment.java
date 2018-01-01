package kangwon.cse2.kdy.fragament;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends Fragment {

    public int count = 0;
    private Button touchButton;
    private Button showButton;

    private OnShowButtonTouchListener listener;


    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (OnShowButtonTouchListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnShowButtonTouchListener");
        }
    }

    public SecondFragment() {
        // Required empty public constructor
    }

    public interface OnShowButtonTouchListener {
        public void onShowButtonTouch(int count);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

               if(savedInstanceState != null){
                   count = savedInstanceState.getInt("showCount");
               }
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    public void onStart() {
        super.onStart();

        touchButton = (Button) getView().findViewById(R.id.touch_button);
        showButton = (Button) getView().findViewById(R.id.show_button);

        touchButton.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if ( event.getAction() == MotionEvent.ACTION_DOWN ) {
                    count++;
                }
                return true;
            }
        });

        showButton.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                onSaveInstanceState(new Bundle());

                listener.onShowButtonTouch(count);
                return true;
            }
        });
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("showCount", count);

    }
}

