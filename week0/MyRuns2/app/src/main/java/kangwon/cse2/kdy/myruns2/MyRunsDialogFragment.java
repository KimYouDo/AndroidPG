package kangwon.cse2.kdy.myruns2;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyRunsDialogFragment extends Fragment {

    public static final int PHOTO_PICKER_ID = 0;
    public static final int DURATION_PICKER_ID = 1;
    public static final int DISTANCE_PICKER_ID = 2;
    public static final int CALORIES_PICKER_ID = 3;
    public static final int HEARTRATE_PICKER_ID = 4;
    public static final int COMMENT_PICKER_ID = 5;

    public MyRunsDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_runs_dialog, container, false);
    }

}
