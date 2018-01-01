package kangwon.cse.jck.myruns3;

import android.content.Context;
import android.content.Intent;
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
        implements LoaderManager.LoaderCallbacks<List<ExerciseEntry>>
{
    public static final String FROM_HISTORY = "From_History";
    public static final String ROW_INDEX = "Row_Index";
    private HistoryAdapter adapter;

    public static String formatDateTime(long paramLong)
    {
        Date localDate = new Date(paramLong);
        SimpleDateFormat localSimpleDateFormat1 = new SimpleDateFormat("hh:mm:ss");
        SimpleDateFormat localSimpleDateFormat2 = new SimpleDateFormat("MMM dd yyyy");
        return localSimpleDateFormat1.format(localDate) + " " + localSimpleDateFormat2.format(localDate);
    }

    public static String formatDistance(double paramDouble, String paramString)
    {
        if (paramString.equals("Miles"))
            paramDouble /= 1.60934D;
        StringBuilder localStringBuilder = new StringBuilder();
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Double.valueOf(paramDouble);
        return String.format("%.2f", arrayOfObject) + " " + paramString;
    }

    public static String formatDuration(double paramDouble)
    {
        int i = (int)(paramDouble / 60.0D);
        int j = (int)(paramDouble % 60.0D);
        if ((i == 0) && (j == 0));
        for (String str = "0secs"; ; str = String.valueOf(i) + "min " + String.valueOf(j) + "secs")
            return str;
    }

    private void loadData()
    {
        if (getLoaderManager().getLoader(0) == null)
            getLoaderManager().initLoader(0, null, this).forceLoad();
        else
            getLoaderManager().restartLoader(0, null, this).forceLoad();

    }

    public void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        if (getActivity() != null)
        {
            this.adapter = new HistoryAdapter(getActivity(), new ArrayList());
            Log.d("adapter created", "adapter created");
            setListAdapter(this.adapter);
            loadData();
        }
        if (paramBundle != null)
        {
            setListAdapter(this.adapter);
            this.adapter.notifyDataSetChanged();
        }
    }

    public Loader<List<ExerciseEntry>> onCreateLoader(int paramInt, Bundle paramBundle)
    {
        Log.d("HistoryFragment: ", "Loader Created");
        return new HistoryLoader(getContext());
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
    {
        return paramLayoutInflater.inflate(R.layout.fragment_history, paramViewGroup, false);
    }

    public void onListItemClick(ListView paramListView, View paramView, int paramInt, long paramLong)
    {
        super.onListItemClick(paramListView, paramView, paramInt, paramLong);
        long l = Long.parseLong(((TextView)paramView.findViewById(R.id.history_item_rowid)).getText().toString());
        if (MainActivity.DBhelper.fetchEntryByIndex(l).getInputType() == ((Integer)StartFragment.INPUT_TO_ID_MAP.get("직접 입력")).intValue());
        for (Intent localIntent = new Intent(getActivity(), DisplayEntryActivity.class); ; localIntent = new Intent(getActivity(), MapDisplayActivity.class))
        {
            localIntent.putExtra("From_History", true);
            localIntent.putExtra("Row_Index", l);
            getActivity().startActivity(localIntent);
            return;
        }
    }

    public void onLoadFinished(Loader<List<ExerciseEntry>> paramLoader, List<ExerciseEntry> paramList)
    {
        Log.d("HistoryFragment: ", "Loading Finished");
        this.adapter.clear();
        this.adapter.addAll(paramList);
    }

    public void onLoaderReset(Loader<List<ExerciseEntry>> paramLoader)
    {
    }

    public void onStart()
    {
        super.onStart();
        loadData();
    }

    class HistoryAdapter extends ArrayAdapter<ExerciseEntry>
    {
        HistoryAdapter(Context context, List<ExerciseEntry> entries) {
            super(context, 0, entries);
        }

        private String getFirstLine(ExerciseEntry paramExerciseEntry)
        {
            String str1 = StartFragment.ID_TO_INPUT[paramExerciseEntry.getInputType()];
            String str2 = StartFragment.ID_TO_ACTIVITY[paramExerciseEntry.getActivityType()];
            String str3 = HistoryFragment.formatDateTime(paramExerciseEntry.getDateTime());
            return str1 + ": " + str2 + ", " + str3;
        }

        private String getSecondLine(ExerciseEntry paramExerciseEntry)
        {
            String str1 = PreferenceManager.getDefaultSharedPreferences(HistoryFragment.this.getActivity()).getString(HistoryFragment.this.getString(R.string.unit_preference), HistoryFragment.this.getString(R.string.unit_km));
            String str2 = HistoryFragment.formatDistance(paramExerciseEntry.getDistance(), str1);
            String str3 = HistoryFragment.formatDuration(paramExerciseEntry.getDuration());
            return str2 + ", " + str3;
        }

        public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
        {
            if (paramView == null)
                paramView = LayoutInflater.from(getContext()).inflate(R.layout.history_item, paramViewGroup, false);
            ExerciseEntry localExerciseEntry = (ExerciseEntry) getItem(paramInt);
            ((TextView) paramView.findViewById(R.id.history_item_first_line)).setText(getFirstLine(localExerciseEntry));
            ((TextView) paramView.findViewById(R.id.history_item_second_line)).setText(getSecondLine(localExerciseEntry));
            ((TextView) paramView.findViewById(R.id.history_item_rowid)).setText(localExerciseEntry.getId() + "");
            return paramView;
        }
    }

    public static class HistoryLoader extends AsyncTaskLoader<List<ExerciseEntry>>
    {
        public HistoryLoader(Context context) {
            super(context);
        }

        public List<ExerciseEntry> loadInBackground()
        {
            Log.d("loading", "loading");
            if (MainActivity.DBhelper != null);
            for (ArrayList localArrayList = MainActivity.DBhelper.fetchEntries(); ; localArrayList = null)
                return localArrayList;
        }
    }
}