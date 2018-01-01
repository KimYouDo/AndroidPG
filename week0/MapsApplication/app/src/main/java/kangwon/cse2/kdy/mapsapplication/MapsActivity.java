package kangwon.cse2.kdy.mapsapplication;

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
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity
        implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {



    protected Location mLastLocation;
    protected String mLatitudeLabel;
    protected TextView mLatitudeText;
    protected String mLongitudeLabel;
    protected TextView mLongitudeText;
    private GoogleMap mMap;

    private GoogleApiClient mGoogleApiClient = null;



    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mGoogleApiClient.connect();
    }

    public void onConnected(Bundle paramBundle) {
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 1);
        if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0) {
            this.mLastLocation = LocationServices.FusedLocationApi.getLastLocation(this.mGoogleApiClient);
            Log.i("MainActivity", "Last Location: " + this.mLastLocation);
            if ((this.mLastLocation != null) && (this.mMap != null)) {
                LatLng localLatLng1 = new LatLng(this.mLastLocation.getLatitude(), this.mLastLocation.getLongitude());
                this.mMap.addMarker(new MarkerOptions().position(localLatLng1).title("최근 위치"));
                CameraPosition localCameraPosition = new CameraPosition.Builder().target(localLatLng1).zoom(13.0F).bearing(0.0F).build();
                this.mMap.animateCamera(CameraUpdateFactory.newCameraPosition(localCameraPosition));
                LatLng localLatLng2 = new LatLng(37.867375000000003D, 127.738783D);
                LatLng localLatLng3 = new LatLng(37.869796999999998D, 127.743135D);
                this.mMap.addMarker(new MarkerOptions().position(localLatLng2).title("강원대학교 공학6호관").icon(BitmapDescriptorFactory.defaultMarker(120.0F)));
                PolylineOptions localPolylineOptions = new PolylineOptions().add(localLatLng2).add(localLatLng3).add(localLatLng1);
                this.mMap.addPolyline(localPolylineOptions);

                TextView localTextView1 = this.mLatitudeText;
                Object[] arrayOfObject1 = new Object[2];
                arrayOfObject1[0] = this.mLatitudeLabel;
                arrayOfObject1[1] = Double.valueOf(this.mLastLocation.getLatitude());
                localTextView1.setText(String.format("%s: %f", arrayOfObject1));
                TextView localTextView2 = this.mLongitudeText;
                Object[] arrayOfObject2 = new Object[2];
                arrayOfObject2[0] = this.mLongitudeLabel;
                arrayOfObject2[1] = Double.valueOf(this.mLastLocation.getLongitude());
                localTextView2.setText(String.format("%s: %f", arrayOfObject2));
            }
            else {
                Toast.makeText(this, R.string.no_location_detected, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onConnectionFailed(ConnectionResult paramConnectionResult)
    {
        Log.i("MainActivity", "Connection failed: ConnectionResult.getErrorCode() = " + paramConnectionResult.getErrorCode());
    }

    public void onConnectionSuspended(int paramInt)
    {
        Log.i("MainActivity", "Connection suspended");
        this.mGoogleApiClient.connect();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mLatitudeLabel = getResources().getString(R.string.latitude_label);
                 mLongitudeLabel = getResources().getString(R.string.longitude_label);
               mLatitudeText = (TextView) findViewById((R.id.latitude_text));
        mLongitudeText = (TextView) findViewById((R.id.longitude_text));

        buildGoogleApiClient();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }

    protected void onStart()
    {
        super.onStart();
        this.mGoogleApiClient.connect();
    }

    protected void onStop()
    {
        super.onStop();
        if (this.mGoogleApiClient.isConnected())
            this.mGoogleApiClient.disconnect();
    }



}
