package kangwon.cse2.kdy.mapsapplication2;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity
        implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    protected static final String TAG = "MainActivity";
    protected Marker lastMarker;
    protected Location mCurrentLocation;
    protected GoogleApiClient mGoogleApiClient;
    protected double mLat;
    protected LatLng mLatLng;
    protected String mLatitudeLabel;
    protected TextView mLatitudeText;
    protected double mLng;
    protected String mLongitudeLabel;
    protected TextView mLongitudeText;
    private GoogleMap mMap;
    protected PolylineOptions polyOptions = new PolylineOptions();
    protected boolean start = true;

    private void updateUI()
    {
        if ((this.mCurrentLocation != null) && (this.mMap != null))
        {
            this.mLat = this.mCurrentLocation.getLatitude();
            this.mLng = this.mCurrentLocation.getLongitude();
            this.mLatLng = new LatLng(this.mLat, this.mLng);
            Log.i("MainActivity", "업데이트" + this.mLat + "' " + this.mLng);

            this.mMap.addMarker(new MarkerOptions().position(this.mLatLng).title("시작 위치").icon(BitmapDescriptorFactory.defaultMarker(0.0F)));
            this.start = false;
            CameraPosition localCameraPosition = new CameraPosition.Builder().target(this.mLatLng).zoom(13.0F).bearing(0.0F).build();
            this.mMap.animateCamera(CameraUpdateFactory.newCameraPosition(localCameraPosition));
            this.polyOptions.add(this.mLatLng);
            this.mMap.addPolyline(this.polyOptions);

            TextView localTextView1 = this.mLatitudeText;
            Object[] arrayOfObject1 = new Object[2];
            arrayOfObject1[0] = this.mLatitudeLabel;
            arrayOfObject1[1] = Double.valueOf(this.mLat);
            localTextView1.setText(String.format("%s: %f", arrayOfObject1));
            TextView localTextView2 = this.mLongitudeText;
            Object[] arrayOfObject2 = new Object[2];
            arrayOfObject2[0] = this.mLongitudeLabel;
            arrayOfObject2[1] = Double.valueOf(this.mLng);
            localTextView2.setText(String.format("%s: %f", arrayOfObject2));
        }
        else
        {
            if (!this.start)
                if (this.lastMarker != null)
                this.lastMarker.remove();
            this.lastMarker = this.mMap.addMarker(new MarkerOptions().position(this.mLatLng).title("현재 위치").icon(BitmapDescriptorFactory.defaultMarker(120.0F)));
            if (this.mCurrentLocation == null)
                Toast.makeText(this, "오류", Toast.LENGTH_SHORT).show();
        }
    }




    protected synchronized void buildGoogleApiClient() {
        Log.i(TAG, "Building GoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }


    protected LocationRequest createLocationRequest() {
        LocationRequest localLocationRequest = new LocationRequest();
        localLocationRequest.setInterval(5000L);
        localLocationRequest.setFastestInterval(3000L);
        localLocationRequest.setPriority(100);
        return localLocationRequest;
    }

    public void onConnected(Bundle paramBundle) {
        startLocationUpdates();
    }

    public void onConnectionFailed(ConnectionResult paramConnectionResult) {
        Log.i("MainActivity", "Connection failed: ConnectionResult.getErrorCode() = " + paramConnectionResult.getErrorCode());
    }

    public void onConnectionSuspended(int paramInt) {
        Log.i("MainActivity", "Connection suspended");
        this.mGoogleApiClient.connect();
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mLatitudeLabel = getResources().getString(R.string.latitude_label);
        mLongitudeLabel = getResources().getString(R.string.longitude_label);
        mLatitudeText = (TextView) findViewById((R.id.latitude_text));
        mLongitudeText = (TextView) findViewById((R.id.longitude_text));
        buildGoogleApiClient();
    }

    public void onLocationChanged(Location paramLocation) {
        Log.i("MainActivity", "업데이트 콜백" + paramLocation);
        this.mCurrentLocation = paramLocation;
        updateUI();
    }

    public void onMapReady(GoogleMap paramGoogleMap) {
        this.mMap = paramGoogleMap;
    }

    protected void onStart() {
        super.onStart();
        this.mGoogleApiClient.connect();
    }

    protected void onStop() {
        super.onStop();
        if (this.mGoogleApiClient.isConnected())
            this.mGoogleApiClient.disconnect();
    }

    protected void startLocationUpdates() {
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 1);
        if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0) {
            Log.i("MainActivity", "업데이트 요청");
            LocationServices.FusedLocationApi.requestLocationUpdates(this.mGoogleApiClient, createLocationRequest(), this);
        }
    }
}
