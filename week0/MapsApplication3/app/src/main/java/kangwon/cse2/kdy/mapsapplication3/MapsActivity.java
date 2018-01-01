package kangwon.cse2.kdy.mapsapplication3;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MapsActivity extends AppCompatActivity
        implements OnMapReadyCallback, ServiceConnection
{
    public static final String ACTION = "LocationUpdate";
    protected static final String KEY_ALTITUDE_HIGH = "altitude-high";
    protected static final String KEY_ALTITUDE_LOW = "altutude-low";
    protected static final String KEY_CLIMB = "climb";
    protected static final String KEY_CURRENT_LOC = "current-location";
    protected static final String KEY_DISTANCE = "distance";
    protected static final String KEY_DURATION = "duration";
    protected static final String KEY_LATLNG_LIST = "latlng-list";
    protected static final String KEY_NUM_OF_POINTS_IN_POLYLINE_OPTIONS = "num-of-points-in-polyline-options";
    protected static final String KEY_POLYLINE_OPTIONS = "polyline-options";
    protected static final String KEY_START_LOC = "start-location";
    protected static final String KEY_START_TIME = "start-time";
    static final int MY_LOCATION_PERMISSIONS_REQUEST_CODE = 1;
    private static final String TAG = "MapsActivity";
    private double altitude_high;
    private double altitude_low;
    private double climb;
    protected Location currentLoc;
    private Marker currentMarker;
    private float distance;
    private long duration;
    protected boolean mBoundToService;


    private ServiceConnection mConnection = this;


    protected ArrayList<LatLng> mLatLngList;
    protected String mLatitudeLabel;
    protected TextView mLatitudeTextView;
    protected List<Location> mLocationList;
    protected String mLongitudeLabel;
    protected TextView mLongitudeTextView;
    private GoogleMap mMap;
    private int numOfPointsInPolylineOptions;
    private PolylineOptions polyOptions;
    private Polyline polyline;
    private Intent serviceIntent;
    protected Location startLoc;
    private Marker startMarker;
    private long startTime;
    private TrackingService trackingService;
    private UpdateReceiver updateReceiver;

    private void startReceiving()
    {
        IntentFilter localIntentFilter = new IntentFilter();
        localIntentFilter.addAction("LocationUpdate");
        LocalBroadcastManager.getInstance(this).registerReceiver(this.updateReceiver, localIntentFilter);
    }

    private void updateMapUI()
    {
        Log.i("MapsActivity", "polyline options " + this.polyOptions);
        if ((this.mMap == null) || (this.startLoc == null));
        else
        {
            if (this.startMarker == null)
            {
                LatLng localLatLng1 = new LatLng(this.startLoc.getLatitude(), this.startLoc.getLongitude());
                this.startMarker = this.mMap.addMarker(new MarkerOptions().position(localLatLng1).title("시작 위치").icon(BitmapDescriptorFactory.defaultMarker(0.0F)));
            }
            if (this.currentMarker != null)
                this.currentMarker.remove();
            LatLng localLatLng2 = new LatLng(this.currentLoc.getLatitude(), this.currentLoc.getLongitude());
            this.currentMarker = this.mMap.addMarker(new MarkerOptions().position(localLatLng2).title("현재 위치").icon(BitmapDescriptorFactory.defaultMarker(120.0F)));
            if (this.polyline != null)
                this.polyline.remove();
            this.polyline = this.mMap.addPolyline(this.polyOptions);
            CameraPosition localCameraPosition = new CameraPosition.Builder().target(localLatLng2).zoom(13.0F).bearing(0.0F).build();
            this.mMap.animateCamera(CameraUpdateFactory.newCameraPosition(localCameraPosition));
        }
    }

    private void updateRecentChanges()
    {
        if (this.mLocationList.size() == 0);
        else
        {

            if (this.startLoc == null)
                this.startLoc = ((Location)this.mLocationList.get(0));
            this.currentLoc = ((Location)this.mLocationList.get(-1 + this.mLocationList.size()));
            Log.d("MapsActivity", "current location: " + this.currentLoc);
            for (int i = this.numOfPointsInPolylineOptions; i < this.mLocationList.size(); i++)
            {
                Location localLocation = (Location)this.mLocationList.get(i);
                LatLng localLatLng = new LatLng(localLocation.getLatitude(), localLocation.getLongitude());
                this.mLatLngList.add(localLatLng);
                this.polyOptions.add(localLatLng);
                this.numOfPointsInPolylineOptions = (1 + this.numOfPointsInPolylineOptions);
                double d = localLocation.getAltitude();
                this.altitude_high = Math.max(d, this.altitude_high);
                this.altitude_low = Math.min(d, this.altitude_low);
                this.climb = Math.max(this.altitude_high - this.altitude_low, 0.0D);
                this.duration = ((Calendar.getInstance().getTimeInMillis() - this.startTime) / 1000L);
                if (i != 0)
                    this.distance += ((Location)this.mLocationList.get(i)).distanceTo((Location)this.mLocationList.get(i - 1));
            }
        }
    }

    private void updateTextUI()
    {
        if (this.currentLoc != null)
        {
            TextView localTextView1 = this.mLatitudeTextView;
            Object[] arrayOfObject1 = new Object[2];
            arrayOfObject1[0] = this.mLatitudeLabel;
            arrayOfObject1[1] = Double.valueOf(this.currentLoc.getLatitude());
            localTextView1.setText(String.format("%s: %.3f", arrayOfObject1));
            TextView localTextView2 = this.mLongitudeTextView;
            Object[] arrayOfObject2 = new Object[2];
            arrayOfObject2[0] = this.mLongitudeLabel;
            arrayOfObject2[1] = Double.valueOf(this.currentLoc.getLongitude());
            localTextView2.setText(String.format("%s: %.3f", arrayOfObject2));
        }
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
            if (paramBundle.keySet().contains("num-of-points-in-polyline-options"))
                this.numOfPointsInPolylineOptions = paramBundle.getInt("num-of-points-in-polyline-options");
            if (paramBundle.keySet().contains("latlng-list"))
                this.mLatLngList = paramBundle.getParcelableArrayList("latlng-list");
            if (paramBundle.keySet().contains("start-location"))
                this.startLoc = ((Location)paramBundle.getParcelable("start-location"));
            if (paramBundle.keySet().contains("current-location"))
                this.currentLoc = ((Location)paramBundle.getParcelable("current-location"));
            if (paramBundle.keySet().contains("polyline-options"))
                this.polyOptions = ((PolylineOptions)paramBundle.getParcelable("polyline-options"));
            if (paramBundle.keySet().contains("start-time"))
                this.startTime = paramBundle.getLong("start-time");
            if (paramBundle.keySet().contains("duration"))
                this.duration = paramBundle.getLong("duration");
            if (paramBundle.keySet().contains("distance"))
                this.distance = paramBundle.getFloat("distance");
            if (paramBundle.keySet().contains("altutude-low"))
                this.altitude_low = paramBundle.getDouble("altutude-low");
            if (paramBundle.keySet().contains("altitude-high"))
                this.altitude_high = paramBundle.getDouble("altitude-high");
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
        setContentView(R.layout.activity_maps);
        ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
        ActivityCompat.requestPermissions(this, new String[] { "android.permission.ACCESS_FINE_LOCATION" }, 1);
        this.mLatitudeLabel = getResources().getString(R.string.latitude_label);
        this.mLongitudeLabel = getResources().getString(R.string.longitude_label);

        this.mLatitudeTextView = ((TextView)findViewById(R.id.latitude_text));
        this.mLongitudeTextView = ((TextView)findViewById(R.id.longitude_text ));

        if (paramBundle == null)
        {
            this.polyOptions = new PolylineOptions();
            this.mLatLngList = new ArrayList();
            this.mBoundToService = false;
            this.startTime = Calendar.getInstance().getTimeInMillis();
            this.duration = 0L;
            this.distance = 0.0F;
            this.altitude_low = 1.7976931348623157E+308D;
            this.altitude_high = 4.9E-324D;
            this.climb = 0.0D;
        }
        updateValuesFromBundle(paramBundle);
        this.updateReceiver = new UpdateReceiver();
        this.serviceIntent = new Intent(this, TrackingService.class);
        startService(this.serviceIntent);
        Log.i("MapsActivity", "onCreate 끝 ================");
    }

    protected void onDestroy()
    {
        Log.d("MapsActivity", "onDestroy ==============================");
        if ((isFinishing()) && (this.trackingService != null))
        {
            Log.d("MapsActivity", "isFinishing ==============================");
            this.trackingService.stopService(this.serviceIntent);
            Log.d("MapsActivity", "Service destroyed ==============================");
        }
        super.onDestroy();
    }

    public void onMapReady(GoogleMap paramGoogleMap)
    {
        this.mMap = paramGoogleMap;
    }

    protected void onPause()
    {
        unbindService(this.mConnection);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.updateReceiver);
        super.onPause();
    }

    protected void onResume()
    {
        super.onResume();
        Log.d("onResume", "Binding======================================");
        bindService(this.serviceIntent, this.mConnection, Context.BIND_AUTO_CREATE);
        startReceiving();
        updateUI();
    }

    protected void onSaveInstanceState(Bundle paramBundle)
    {
        paramBundle.putInt("num-of-points-in-polyline-options", this.numOfPointsInPolylineOptions);
        paramBundle.putParcelableArrayList("latlng-list", this.mLatLngList);
        paramBundle.putParcelable("start-location", this.startLoc);
        paramBundle.putParcelable("current-location", this.currentLoc);
        paramBundle.putParcelable("polyline-options", this.polyOptions);
        paramBundle.putLong("start-time", this.startTime);
        paramBundle.putLong("duration", this.duration);
        paramBundle.putFloat("distance", this.distance);
        paramBundle.putDouble("altutude-low", this.altitude_low);
        paramBundle.putDouble("altitude-high", this.altitude_high);
        paramBundle.putDouble("climb", this.climb);
        super.onSaveInstanceState(paramBundle);
    }

    public void onServiceConnected(ComponentName paramComponentName, IBinder paramIBinder)
    {
        this.trackingService = ((TrackingService.TrackingBinder)paramIBinder).getReference();
        Log.d("MapsActivity", "Service connected");
        this.mLocationList = this.trackingService.getLocList();
        updateRecentChanges();
        updateUI();
    }

    public void onServiceDisconnected(ComponentName paramComponentName)
    {
        Log.d("MapsActivity", "트래킹 서비스 끊어짐");
    }




    public class UpdateReceiver extends BroadcastReceiver
    {
        public UpdateReceiver()
        {
        }

        public void onReceive(Context paramContext, Intent paramIntent)
        {
            Log.d("MapsActivity", "update received ======================================");
            MapsActivity.this.mLocationList = MapsActivity.this.trackingService.getLocList();
            Log.d("MapsActivity", "list: " + MapsActivity.this.mLocationList.size());
            MapsActivity.this.updateRecentChanges();
            MapsActivity.this.updateUI();
        }
    }


}