package kangwon.cse.jck.myruns3;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MapDisplayActivity extends AppCompatActivity
        implements OnMapReadyCallback, LoaderManager.LoaderCallbacks<ExerciseEntry>
{
    protected static final String KEY_ACTIVITY_TYPE = "activity-type";
    protected static final String KEY_CLIMB = "climb";
    protected static final String KEY_DISTANCE = "distance";
    protected static final String KEY_DURATION = "duration";
    protected static final String KEY_LATLNG_LIST = "latlng-list";
    protected static final String KEY_START_TIME = "start-time";
    private static final String TAG = "MapDisplayActivity";
    private int activityType;
    private double climb;
    private double distance;
    private double duration;
    protected LatLng endLatLng;
    private Marker endMarker;
    protected ExerciseEntry entry;
    protected String mActivityTypeLabel;
    protected TextView mActivityTypeTextView;
    protected String mClimbLabel;
    protected TextView mClimbTextView;
    protected String mDateTimeLabel;
    protected TextView mDateTimeTextView;
    protected String mDistanceLabel;
    protected TextView mDistanceTextView;
    protected String mDurationLabel;
    protected TextView mDurationTextView;
    protected ArrayList<LatLng> mLatLngList;
    private GoogleMap mMap;
    private PolylineOptions polyOptions;
    private Polyline polyline;
    private long rowid;
    protected LatLng startLatLng;
    private Marker startMarker;
    private long startTime;

    private void loadData()
    {
        if (getLoaderManager().getLoader(0) == null)
            getSupportLoaderManager().initLoader(0, null, this).forceLoad();
        else
        {
            getSupportLoaderManager().restartLoader(0, null, this).forceLoad();
        }
    }

    private void runThread()
    {
        new Thread()
        {
            public void run()
            {
                MainActivity.DBhelper.removeEntry(MapDisplayActivity.this.rowid);
            }
        }
                .start();
    }

    private void updateMapUI()
    {
        if ((this.mMap == null) || (this.mLatLngList == null) || (this.mLatLngList.size() == 0));
        else
        {
            this.startLatLng = ((LatLng)this.mLatLngList.get(0));
            this.endLatLng = ((LatLng)this.mLatLngList.get(-1 + this.mLatLngList.size()));
            if (this.startMarker == null)
                this.startMarker = this.mMap.addMarker(new MarkerOptions().position(this.startLatLng).title("시작 위치").icon(BitmapDescriptorFactory.defaultMarker(0.0F)));
            if (this.endMarker == null)
                this.endMarker = this.mMap.addMarker(new MarkerOptions().position(this.endLatLng).title("현재 위치").icon(BitmapDescriptorFactory.defaultMarker(120.0F)));
            this.polyOptions = new PolylineOptions();
            this.polyOptions.addAll(this.mLatLngList);
            if (this.polyline == null)
                this.polyline = this.mMap.addPolyline(this.polyOptions);
            CameraPosition localCameraPosition = new CameraPosition.Builder().target(this.startLatLng).zoom(13.0F).bearing(0.0F).build();
            this.mMap.animateCamera(CameraUpdateFactory.newCameraPosition(localCameraPosition));
        }
    }

    private void updateTextUI()
    {
        String str1 = DateFormat.getDateTimeInstance(3, 3, Locale.KOREAN).format(Long.valueOf(this.startTime));
        TextView localTextView1 = this.mDateTimeTextView;
        Object[] arrayOfObject1 = new Object[2];
        arrayOfObject1[0] = this.mDateTimeLabel;
        arrayOfObject1[1] = str1;
        localTextView1.setText(String.format("%s: %s", arrayOfObject1));
        String str2 = StartFragment.ID_TO_ACTIVITY[this.activityType];
        TextView localTextView2 = this.mActivityTypeTextView;
        Object[] arrayOfObject2 = new Object[2];
        arrayOfObject2[0] = this.mActivityTypeLabel;
        arrayOfObject2[1] = str2;
        localTextView2.setText(String.format("%s: %s", arrayOfObject2));
        TextView localTextView3 = this.mDistanceTextView;
        Object[] arrayOfObject3 = new Object[3];
        arrayOfObject3[0] = this.mDistanceLabel;
        arrayOfObject3[1] = Double.valueOf(this.distance / 1000.0D);
        arrayOfObject3[2] = "km";
        localTextView3.setText(String.format("%s: %.1f%s", arrayOfObject3));
        TextView localTextView4 = this.mDurationTextView;
        Object[] arrayOfObject4 = new Object[3];
        arrayOfObject4[0] = this.mDurationLabel;
        arrayOfObject4[1] = Double.valueOf(this.duration / 60.0D);
        arrayOfObject4[2] = "분";
        localTextView4.setText(String.format("%s: %.1f%s", arrayOfObject4));
        TextView localTextView5 = this.mClimbTextView;
        Object[] arrayOfObject5 = new Object[3];
        arrayOfObject5[0] = this.mClimbLabel;
        arrayOfObject5[1] = Double.valueOf(this.climb);
        arrayOfObject5[2] = "m";
        localTextView5.setText(String.format("%s: %.1f%s", arrayOfObject5));
    }

    private void updateUI()
    {
        updateMapUI();
        updateTextUI();
    }

    private void updateValuesFromBundle(Bundle paramBundle)
    {
        if (paramBundle != null)
        {
            if (paramBundle.keySet().contains("latlng-list"))
                this.mLatLngList = paramBundle.getParcelableArrayList("latlng-list");
            if (paramBundle.keySet().contains("activity-type"))
                this.activityType = paramBundle.getInt("activity-type");
            if (paramBundle.keySet().contains("start-time"))
                this.startTime = paramBundle.getLong("start-time");
            if (paramBundle.keySet().contains("duration"))
                this.duration = paramBundle.getDouble("duration");
            if (paramBundle.keySet().contains("distance"))
                this.distance = paramBundle.getDouble("distance");
            if (paramBundle.keySet().contains("climb"))
                this.climb = paramBundle.getDouble("climb");
            updateUI();
        }
    }

    public void onBackPressed()
    {
        finish();
        super.onBackPressed();
    }

    protected void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_map_display);
        ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
        this.rowid = getIntent().getExtras().getLong("Row_Index");
        this.mDateTimeLabel = "일시";
        this.mActivityTypeLabel = "운동 종류";
        this.mDistanceLabel = "거리";
        this.mDurationLabel = "시간";
        this.mClimbLabel = "오름";
        this.mDateTimeTextView = ((TextView)findViewById(R.id.data_time_text));
        this.mActivityTypeTextView = ((TextView)findViewById(R.id.activity_type_text));
        this.mDistanceTextView = ((TextView)findViewById(R.id.distance_text));
        this.mDurationTextView = ((TextView)findViewById(R.id.duration_text));
        this.mClimbTextView = ((TextView)findViewById(R.id.climb_text));
        if (paramBundle == null)
            loadData();
        updateValuesFromBundle(paramBundle);
    }

    public Loader<ExerciseEntry> onCreateLoader(int paramInt, Bundle paramBundle)
    {
        Log.d("HistoryFragment: ", "Loader Created");
        return new ExerciseLoader(getApplicationContext(), this.rowid);
    }

    public boolean onCreateOptionsMenu(Menu paramMenu)
    {
        super.onCreateOptionsMenu(paramMenu);
        paramMenu.add(0, 0, 0, "삭제").setShowAsAction(2);
        return true;
    }

    public void onLoadFinished(Loader<ExerciseEntry> paramLoader, ExerciseEntry paramExerciseEntry)
    {
        Log.d("HistoryFragment: ", "Loading Finished");
        this.entry = paramExerciseEntry;
        this.activityType = this.entry.getActivityType();
        this.startTime = this.entry.getDateTime();
        this.distance = this.entry.getDistance();
        this.duration = this.entry.getDuration();
        this.climb = this.entry.getClimb();
        this.mLatLngList = this.entry.getLocationList();
        StringBuilder localStringBuilder = new StringBuilder().append("onLoadFinished, mLatLngList가 null인가? ");
        if (this.mLatLngList == null);
        for (boolean bool = true; ; bool = false)
        {
            Log.d("MapDisplayActivity", String.valueOf(bool));
            updateUI();
            return;
        }
    }

    public void onLoaderReset(Loader<ExerciseEntry> paramLoader)
    {
    }

    public void onMapReady(GoogleMap paramGoogleMap)
    {
        this.mMap = paramGoogleMap;
    }

    public boolean onOptionsItemSelected(MenuItem paramMenuItem)
    {
        runThread();
        finish();
        return true;
    }

    protected void onResume()
    {
        super.onResume();
        StringBuilder localStringBuilder = new StringBuilder().append("onResume 시작, mLatLngList가 null인가? ");
        if (this.mLatLngList == null);
        for (boolean bool = true; ; bool = false)
        {
            Log.d("MapDisplayActivity", String.valueOf(bool));
            if (this.mLatLngList != null)
                updateUI();
            return;
        }
    }

    protected void onSaveInstanceState(Bundle paramBundle)
    {
        paramBundle.putParcelableArrayList("latlng-list", this.mLatLngList);
        paramBundle.putInt("activity-type", this.activityType);
        paramBundle.putLong("start-time", this.startTime);
        paramBundle.putDouble("duration", this.duration);
        paramBundle.putDouble("distance", this.distance);
        paramBundle.putDouble("climb", this.climb);
        super.onSaveInstanceState(paramBundle);
    }

    private static class ExerciseLoader extends AsyncTaskLoader<ExerciseEntry>
    {
        private long rowid;

        ExerciseLoader(Context paramContext, long paramLong)
        {
            super(null);
            this.rowid = paramLong;
        }

        public ExerciseEntry loadInBackground()
        {
            Log.d("loading", "loading");
            if (MainActivity.DBhelper != null);
            for (ExerciseEntry localExerciseEntry = MainActivity.DBhelper.fetchEntryByIndex(this.rowid); ; localExerciseEntry = null)
                return localExerciseEntry;
        }
    }
}
