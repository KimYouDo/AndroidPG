package com.soundcloud.android.crop;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import kangwon.cse2.kdy.myruns1.R;

public class Crop
{
    public static final int REQUEST_CROP = 6709;
    public static final int REQUEST_PICK = 9162;
    public static final int RESULT_ERROR = 404;
    private Intent cropIntent = new Intent();

    private Crop(Uri paramUri1, Uri paramUri2)
    {
        this.cropIntent.setData(paramUri1);
        this.cropIntent.putExtra("output", paramUri2);
    }

    public static Throwable getError(Intent paramIntent)
    {
        return (Throwable)paramIntent.getSerializableExtra("error");
    }

    private static Intent getImagePicker()
    {
        return new Intent("android.intent.action.GET_CONTENT").setType("image/*");
    }

    public static Uri getOutput(Intent paramIntent)
    {
        return (Uri)paramIntent.getParcelableExtra("output");
    }

    public static Crop of(Uri paramUri1, Uri paramUri2)
    {
        return new Crop(paramUri1, paramUri2);
    }

    public static void pickImage(Activity paramActivity)
    {
        pickImage(paramActivity, 9162);
    }

    public static void pickImage(Activity paramActivity, int paramInt)
    {
        try
        {
            paramActivity.startActivityForResult(getImagePicker(), paramInt);
            return;
        }
        catch (ActivityNotFoundException localActivityNotFoundException)
        {
            while (true)
                showImagePickerError(paramActivity);
        }
    }

    public static void pickImage(Context paramContext, android.app.Fragment paramFragment)
    {
        pickImage(paramContext, paramFragment, 9162);
    }

    @TargetApi(11)
    public static void pickImage(Context paramContext, android.app.Fragment paramFragment, int paramInt)
    {
        try
        {
            paramFragment.startActivityForResult(getImagePicker(), paramInt);
            return;
        }
        catch (ActivityNotFoundException localActivityNotFoundException)
        {
            while (true)
                showImagePickerError(paramContext);
        }
    }

    public static void pickImage(Context paramContext, android.support.v4.app.Fragment paramFragment)
    {
        pickImage(paramContext, paramFragment, 9162);
    }

    public static void pickImage(Context paramContext, android.support.v4.app.Fragment paramFragment, int paramInt)
    {
        try
        {
            paramFragment.startActivityForResult(getImagePicker(), paramInt);
            return;
        }
        catch (ActivityNotFoundException localActivityNotFoundException)
        {
            while (true)
                showImagePickerError(paramContext);
        }
    }

    private static void showImagePickerError(Context paramContext)
    {
        Toast.makeText(paramContext, R.string.crop__pick_error, Toast.LENGTH_SHORT).show();
    }

    public Crop asSquare()
    {
        this.cropIntent.putExtra("aspect_x", 1);
        this.cropIntent.putExtra("aspect_y", 1);
        return this;
    }

    public Intent getIntent(Context paramContext)
    {
        this.cropIntent.setClass(paramContext, CropImageActivity.class);
        return this.cropIntent;
    }

    public void start(Activity paramActivity)
    {
        start(paramActivity, 6709);
    }

    public void start(Activity paramActivity, int paramInt)
    {
        paramActivity.startActivityForResult(getIntent(paramActivity), paramInt);
    }

    public void start(Context paramContext, android.app.Fragment paramFragment)
    {
        start(paramContext, paramFragment, 6709);
    }

    @TargetApi(11)
    public void start(Context paramContext, android.app.Fragment paramFragment, int paramInt)
    {
        paramFragment.startActivityForResult(getIntent(paramContext), paramInt);
    }

    public void start(Context paramContext, android.support.v4.app.Fragment paramFragment)
    {
        start(paramContext, paramFragment, 6709);
    }

    public void start(Context paramContext, android.support.v4.app.Fragment paramFragment, int paramInt)
    {
        paramFragment.startActivityForResult(getIntent(paramContext), paramInt);
    }

    public Crop withAspect(int paramInt1, int paramInt2)
    {
        this.cropIntent.putExtra("aspect_x", paramInt1);
        this.cropIntent.putExtra("aspect_y", paramInt2);
        return this;
    }

    public Crop withMaxSize(int paramInt1, int paramInt2)
    {
        this.cropIntent.putExtra("max_x", paramInt1);
        this.cropIntent.putExtra("max_y", paramInt2);
        return this;
    }

    static abstract interface Extra
    {
        public static final String ASPECT_X = "aspect_x";
        public static final String ASPECT_Y = "aspect_y";
        public static final String ERROR = "error";
        public static final String MAX_X = "max_x";
        public static final String MAX_Y = "max_y";
    }
}

/* Location:           C:\Apk_Manager_5.0.2\place-apk-here-for-modding\MyRuns1\classes_dex2jar.jar
 * Qualified Name:     com.soundcloud.android.crop.Crop
 * JD-Core Version:    0.6.2
 */