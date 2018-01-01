package kangwon.cse.jck.myruns3;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class ExerciseEntry {
    private int activityType = 0;
    private double avgSpeed;
    private int calories;
    private double climb;
    private String comment;
    private long dateTime;
    private double distance;
    private double duration;
    private int heartRate;
    private Long id;
    private int inputType = 0;
    private ArrayList<LatLng> locationList;

    public void addLocationList(LatLng paramLatLng)
    {
        this.locationList.add(paramLatLng);
    }

    int getActivityType()
    {
        return this.activityType;
    }

    double getAvgSpeed()
    {
        return this.avgSpeed;
    }

    int getCalories()
    {
        return this.calories;
    }

    double getClimb()
    {
        return this.climb;
    }

    String getComment()
    {
        return this.comment;
    }

    long getDateTime()
    {
        return this.dateTime;
    }

    double getDistance()
    {
        return this.distance;
    }

    double getDuration()
    {
        return this.duration;
    }

    int getHeartRate()
    {
        return this.heartRate;
    }

    public long getId()
    {
        return this.id.longValue();
    }

    int getInputType()
    {
        return this.inputType;
    }

    public ArrayList<LatLng> getLocationList()
    {
        return this.locationList;
    }

    void setActivityType(int paramInt)
    {
        this.activityType = paramInt;
    }

    void setAvgSpeed(double paramDouble)
    {
        this.avgSpeed = paramDouble;
    }

    void setCalories(int paramInt)
    {
        this.calories = paramInt;
    }

    void setClimb(double paramDouble)
    {
        this.climb = paramDouble;
    }

    void setComment(String paramString)
    {
        this.comment = paramString;
    }

    void setDateTime(long paramLong)
    {
        this.dateTime = paramLong;
    }

    void setDistance(double paramDouble)
    {
        this.distance = paramDouble;
    }

    void setDuration(double paramDouble)
    {
        this.duration = paramDouble;
    }

    void setHeartRate(int paramInt)
    {
        this.heartRate = paramInt;
    }

    public void setId(long paramLong)
    {
        this.id = Long.valueOf(paramLong);
    }

    void setInputType(int paramInt)
    {
        this.inputType = paramInt;
    }

    public void setLocationList(ArrayList<LatLng> paramArrayList)
    {
        this.locationList = paramArrayList;
    }

    public String toString()
    {
        return this.inputType + ", " + this.activityType + ", " + this.dateTime + ", " + this.duration + ", " + this.distance + ", " + this.avgSpeed + ", " + this.calories + ", " + this.climb + ", " + this.heartRate + ", " + this.comment;
    }


}
