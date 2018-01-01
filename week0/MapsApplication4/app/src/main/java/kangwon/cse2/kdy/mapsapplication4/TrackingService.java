package kangwon.cse2.kdy.mapsapplication4;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

public class TrackingService extends Service implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener {
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 1000L;
    protected static final String TAG = "TrackingService";
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 2000L;
    private final int NOTIFICATION_ID = 1;
    private ArrayList<Location> locList;
    protected GoogleApiClient mGoogleApiClient;
    private NotificationManager mNotificationManager;
    private TrackingBinder trackingBinder = new TrackingBinder();


    protected void buildGoogleApiClient()
    {
        this.mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
    }

    protected LocationRequest createLocationRequest()
    {
        LocationRequest localLocationRequest = new LocationRequest();
        localLocationRequest.setInterval(2000L);
        localLocationRequest.setFastestInterval(1000L);
        localLocationRequest.setPriority(100);
        return localLocationRequest;
    }

    private void notifyToUser()
    {
        Context localContext = getApplicationContext();
        Intent localIntent = new Intent(localContext, MapsActivity.class);
        localIntent.addCategory("android.intent.category.LAUNCHER");
        localIntent.setAction("android.intent.action.MAIN");
        PendingIntent localPendingIntent = PendingIntent.getActivity(localContext, 0, localIntent, 0);
        Notification localNotification = new Notification.Builder(this).setContentTitle("Tracking Service").setContentText("위치 주적 중").setSmallIcon(R.drawable.ic_stat_tracking).setContentIntent(localPendingIntent).build();
        this.mNotificationManager = ((NotificationManager)getSystemService(NOTIFICATION_SERVICE));
        localNotification.flags = (0x2 | localNotification.flags);
        this.mNotificationManager.notify(1, localNotification);
    }

    public List<Location> getLocList()
    {
        return this.locList;
    }

    public IBinder onBind(Intent paramIntent)
    {
        return this.trackingBinder;
    }

    public void onConnected(Bundle paramBundle)
    {
        if (Build.VERSION.SDK_INT >= 23)
        {
            int j = checkSelfPermission("android.permission.ACCESS_FINE_LOCATION");
            //int i = 0;
            if (j != 0);
        }
        for (int i = 1; ; i = 1)
        {
            if (i != 0)
            {
                LocationServices.FusedLocationApi.requestLocationUpdates(this.mGoogleApiClient, createLocationRequest(), this);
                notifyToUser();
            }
            return;
        }

    }

    public void onConnectionFailed(ConnectionResult paramConnectionResult)
    {
        Log.i("TrackingService", "Connection failed: ConnectionResult.getErrorCode() = " + paramConnectionResult.getErrorCode());
    }

    public void onConnectionSuspended(int paramInt)
    {
        Log.i("TrackingService", "Connection suspended");
        this.mGoogleApiClient.connect();
    }

    public void onCreate()
    {
        this.locList = new ArrayList();
        buildGoogleApiClient();
        super.onCreate();
    }

    public void onDestroy()
    {
        if (this.mGoogleApiClient.isConnected())
            this.mGoogleApiClient.disconnect();
        if (this.mNotificationManager != null)
            this.mNotificationManager.cancel(1);
        Log.d("TrackingService", "Tracking service destroyed");
        super.onDestroy();
    }

    public void onLocationChanged(Location paramLocation)
    {
        Log.i("TrackingService", "Location 업데이트 콜백" + paramLocation);
        if (paramLocation != null)
        {
            this.locList.add(paramLocation);
            Intent localIntent = new Intent();
            localIntent.setAction("LocationUpdate");
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(localIntent);
            Log.d("TrackingService", "Sent location update broadcast");
        }
    }

    public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
    {
        this.mGoogleApiClient.connect();
        return super.onStartCommand(paramIntent, paramInt1, paramInt2);
    }

    public class TrackingBinder extends Binder
    {
        public TrackingBinder()
        {
        }

        public TrackingService getReference()
        {
            return TrackingService.this;
        }
    }
}