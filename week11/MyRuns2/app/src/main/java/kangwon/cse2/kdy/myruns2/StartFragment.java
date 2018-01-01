package kangwon.cse2.kdy.myruns2;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class StartFragment extends Fragment {
    public static final Map<String, Integer> ACTIVITY_TO_ID_MAP;
    public static final String ACTIVITY_TYPE = "activity_type";
    public static final String AUTOMATIC = "자동";
    public static final String GPS = "GPS";
    public static final String[] ID_TO_ACTIVITY = { "Running", "Walking", "Standing", "Cycling", "Hiking", "Downhill Skiing", "Cross-Country Skiing", "Snowboarding", "Skating", "Swimming", "Mountain Biking", "Wheelchair", "Elliptical", "Other" };
    public static final String[] ID_TO_INPUT;
    public static final Map<String, Integer> INPUT_TO_ID_MAP = new HashMap();
    public static final String INPUT_TYPE = "input_type";
    public static final String MANUAL_ENTRY = "직접 입력";
    private Spinner activitySpinner;
    private Spinner inputSpinner;
    private Intent mIntent;
    private Button startButton;
    private Button syncButton;

    static
    {
        INPUT_TO_ID_MAP.put("직접 입력", Integer.valueOf(0));
        INPUT_TO_ID_MAP.put("자동", Integer.valueOf(1));
        INPUT_TO_ID_MAP.put("GPS", Integer.valueOf(2));
        ID_TO_INPUT = new String[] { "직접 입력", "자동", "GPS" };
        ACTIVITY_TO_ID_MAP = new HashMap();
        ACTIVITY_TO_ID_MAP.put("Running", Integer.valueOf(0));
        ACTIVITY_TO_ID_MAP.put("Walking", Integer.valueOf(1));
        ACTIVITY_TO_ID_MAP.put("Standing", Integer.valueOf(2));
        ACTIVITY_TO_ID_MAP.put("Cycling", Integer.valueOf(3));
        ACTIVITY_TO_ID_MAP.put("Hiking", Integer.valueOf(4));
        ACTIVITY_TO_ID_MAP.put("Downhill Skiing", Integer.valueOf(5));
        ACTIVITY_TO_ID_MAP.put("Cross-Country Skiing", Integer.valueOf(6));
        ACTIVITY_TO_ID_MAP.put("Snowboarding", Integer.valueOf(7));
        ACTIVITY_TO_ID_MAP.put("Skating", Integer.valueOf(8));
        ACTIVITY_TO_ID_MAP.put("Swimming", Integer.valueOf(9));
        ACTIVITY_TO_ID_MAP.put("Mountain Biking", Integer.valueOf(10));
        ACTIVITY_TO_ID_MAP.put("Wheelchair", Integer.valueOf(11));
        ACTIVITY_TO_ID_MAP.put("Elliptical", Integer.valueOf(12));
        ACTIVITY_TO_ID_MAP.put("Other", Integer.valueOf(13));
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
    {
        View localView = paramLayoutInflater.inflate(R.layout.fragment_start, paramViewGroup, false);
        this.startButton = ((Button)localView.findViewById(R.id.button_start));
        this.syncButton = ((Button)localView.findViewById(R.id.button_sync));
        this.inputSpinner = ((Spinner)localView.findViewById(R.id.spinnerInputType));
        this.activitySpinner = ((Spinner)localView.findViewById(R.id.spinnerActivityType));
        this.startButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramAnonymousView)
            {
                String str1 = StartFragment.this.inputSpinner.getSelectedItem().toString();
                String str2 = StartFragment.this.activitySpinner.getSelectedItem().toString();

                switch(str1){
                    case MANUAL_ENTRY:
                        mIntent = new Intent(getActivity(), ManualEntryActivity.class);
                        break;
                    case AUTOMATIC:
                        mIntent = new Intent(getActivity(),  MapDisplayActivity.class);
                        break;
                    case GPS:
                        mIntent = new Intent(getActivity(), MapDisplayActivity.class);
                        break;
                    default:
                        break;
                }

                mIntent.putExtra(INPUT_TYPE,INPUT_TO_ID_MAP.get(str1));
                mIntent.putExtra(ACTIVITY_TYPE,ACTIVITY_TO_ID_MAP.get(str1));
                getActivity().startActivity(mIntent);
            }
        });

        // Trigger activity on selecting sync button
        syncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg) {
                Toast.makeText(StartFragment.this.getActivity(), "Sync", Toast.LENGTH_SHORT).show();
            }
        });
        return localView;
    }
}