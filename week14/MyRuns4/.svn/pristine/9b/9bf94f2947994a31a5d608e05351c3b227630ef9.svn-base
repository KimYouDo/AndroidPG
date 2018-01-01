package kangwon.cse.jck.myruns3;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoryFragment extends ListFragment
        implements LoaderManager.LoaderCallbacks<List<ExerciseEntry>> {

    // Constant tag for position
    public static final String ROW_INDEX = "Row_Index";
    public static final String FROM_HISTORY = "From_History";

    private HistoryAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getActivity() != null) {

            adapter = new HistoryAdapter(getActivity(), new ArrayList<ExerciseEntry>());
            setListAdapter(adapter);
            loadData();
        }

    }

    // 데이터베이스로부터 데이터를 읽어 온다.
    // Loader가 데이터베이스를 읽어 List<ExerciseEntry>를 반환한다.
    // 데이터베이스 읽기가 끝나면 onLoadFinished 메소드에 의해
    // List<ExerciseEntry>가 adapter에 넣어진다. --> 화면에 나타난다.

    // LoaderManager.getLoader 메소드는 onCreateLoader 메소드를 호출한다.
    // onCreateLoader 메소드는 HistoryLoader 인스턴스를 구성하여 반환한다.
    // 반환되는 HistoryLoader 인스턴스는 고유번호 0번(getLoader 인자로 주어진 값)을 부여받는다.
    private void loadData() {
        if(getLoaderManager().getLoader(0) == null) {
            getLoaderManager().initLoader(0, null, this).forceLoad();
        } else {
            getLoaderManager().restartLoader(0, null, this).forceLoad();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        loadData();
    }



    /////////////////////// Override loader functionality ///////////////////////
    // LoaderManager.LoaderCallbacks 메소드들 세 개.

    @Override
    public Loader<List<ExerciseEntry>> onCreateLoader(int id, Bundle args) {
        Log.d("HistoryFragment: ", "Loader Created");
        return new HistoryLoader(getContext());
    }

    @Override
    public void onLoadFinished(Loader<List<ExerciseEntry>> loader, List<ExerciseEntry> data) {
        Log.d("HistoryFragment: ", "Loading Finished");
        adapter.clear();
        adapter.addAll(data);
    }

    @Override
    public void onLoaderReset(Loader<List<ExerciseEntry>> loader) {

    }

    /////////////////////// Use AsyncTaskLoader to read from database ///////////////////////

    public static class HistoryLoader extends AsyncTaskLoader<List<ExerciseEntry>> {

        public HistoryLoader(Context context){
            super(context);
        }

        @Override
        public List<ExerciseEntry> loadInBackground() {
            Log.d("loading","loading");
            return MainActivity.DBhelper != null ?
                    MainActivity.DBhelper.fetchEntries() : null;
        }
    }


    /////////////////////// Adapter for listview ///////////////////////

    class HistoryAdapter extends ArrayAdapter<ExerciseEntry> {

        HistoryAdapter(Context context, List<ExerciseEntry> entries) {
            super(context, 0, entries);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {

            // view 재활용이 아닌 경우에만 view를 새로 만든다.
            // 리스트가 길어 상하로 스크롤이 될 때 안드로이드는 화면에서 사라지는 view를 재활용한다.
            // 재활용되는 경우에는 재활용되는 view가 파라미터로 넘어 온다.
            // 이런 경우에는 view를 새로 만들지 않는다.
            if (view == null) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.history_item, parent, false);
            }

            ExerciseEntry entry = getItem(position);

            // Set the first line
            TextView firstLine = (TextView) view.findViewById(R.id.history_item_first_line);
            firstLine.setText(getFirstLine(entry));

            // Set the second line
            TextView secondLine = (TextView) view.findViewById(R.id.history_item_second_line);
            secondLine.setText(getSecondLine(entry));

            //Set the id
            TextView thirdLine = (TextView) view.findViewById(R.id.history_item_rowid);
            thirdLine.setText(entry.getId() + "");

            return view;
        }


        /////////////////////// Helper function ///////////////////////

        /**
         * Helper function to get the first line in the history list
         */
        private String getFirstLine(ExerciseEntry entry) {

            String input = StartFragment.ID_TO_INPUT[entry.getInputType()];
            String activity = StartFragment.ID_TO_ACTIVITY[entry.getActivityType()];
            String dateTime = formatDateTime(entry.getDateTime());
            return input + ": " + activity + ", " + dateTime;
        }

        /**
         * Helper function to get the second line in the history list
         */
        private String getSecondLine(ExerciseEntry entry) {

            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String unitPref = pref.getString(getString(R.string.unit_preference), getString(R.string.unit_km));
            String distance = formatDistance(entry.getDistance(), unitPref);
            String duration = formatDuration(entry.getDuration());
            return distance + ", " + duration;
        }
    }

    // Convert the date and time from milliseconds to the proper format
    public static String formatDateTime(long dateTime) {
        Date date = new Date(dateTime);
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd yyyy");

        return timeFormat.format(date) + " " + dateFormat.format(date);
    }

    // Convert the duration from seconds to the proper format
    public static String formatDuration(double duration) {
        int minutes = (int)(duration/60);
        int seconds = (int)(duration%60);
        if (minutes == 0 && seconds == 0) return "0secs";
        return String.valueOf(minutes) + "min " + String.valueOf(seconds) + "secs";
    }

    // Convert the distance from kilometers to the proper format
    public static String formatDistance(double distance, String unitPref) {

        if (unitPref.equals("Miles")) {
            distance /= ManualEntryActivity.MILES2KM; // converts from km to miles
        }
        return String.format("%.2f", distance)+" "+unitPref;
    }


        @Override
        public void onListItemClick(ListView parent, View v, int position, long id) {
            super.onListItemClick(parent, v, position, id);

            // Find the row id by accessing the value in the invisible row
            TextView tv = (TextView) v.findViewById(R.id.history_item_rowid);
            long rowid = Long.parseLong(tv.getText().toString());
            ExerciseEntry entry = MainActivity.DBhelper.fetchEntryByIndex(rowid);

            Intent mIntent;
            if(entry.getInputType() == StartFragment.INPUT_TO_ID_MAP.get(StartFragment.MANUAL_ENTRY)){
                mIntent = new Intent(getActivity(), DisplayEntryActivity.class);
            }else{
                mIntent = new Intent(getActivity(), MapDisplayActivity.class);
            }

            mIntent.putExtra(FROM_HISTORY,true);
            mIntent.putExtra(ROW_INDEX, rowid);
            getActivity().startActivity(mIntent);
        }

}
