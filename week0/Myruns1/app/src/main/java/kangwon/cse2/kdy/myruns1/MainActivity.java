package kangwon.cse2.kdy.myruns1;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    public static final String PREFS = "Profile_Info";
    static final String PROFILE_IMAGE_FILENAME = "profile.png";
    public static final int REQUEST_CAMERA_CAPTURE = 0;
    static final String TEMP_IMAGE_FILENAME = "temp.png";
    private static final int WRITE_PERMISSION_CHECK_CODE = 1002;
    Button changePhotoButton;
    private boolean hasTempPhoto = false;
    private Uri mImageUri;
    private ImageView mlmageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mlmageView = (ImageView) findViewById(R.id.imageView);
        this.changePhotoButton = (Button) findViewById(R.id.ChangButton);
        loadPhoto("profile.png");
        File localFile = new File(getFilesDir().getAbsolutePath() + "/" + "temp.png");
        if (savedInstanceState != null)
        {
            this.hasTempPhoto = savedInstanceState.getBoolean("hasTempPhoto");
            if ((localFile.exists()) && (this.hasTempPhoto))
                loadPhoto("temp.png");
        }
        loadUserInfo();
    }
    private void savePhoto(String path) {
        this.mlmageView.buildDrawingCache();
        Bitmap bmap = mlmageView.getDrawingCache();
        try {
            FileOutputStream wFile = openFileOutput(path, MODE_PRIVATE);
            bmap.compress(Bitmap.CompressFormat.PNG, 100, wFile);
            wFile.flush();
            wFile.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public boolean externalStorageAvailable()
    {
        if ("mounted".equals(Environment.getExternalStorageState()));
        for (boolean bool = true; ; bool = false)
            return bool;
    }

    private void loadPhoto(String path) {
        try {
            FileInputStream rFile = openFileInput(path);
            Bitmap bmap = BitmapFactory.decodeStream(rFile);
            this.mlmageView.setImageBitmap(bmap);
            rFile.close();
        } catch (IOException e) {
            this.mlmageView.setImageResource(R.drawable.profile_icon);
        }
    }

    public void onRequestPermissionsResult ( int requestCode, String[] permissions, int[] grantResults){
        if (requestCode == WRITE_PERMISSION_CHECK_CODE && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {             // 권한을 획득한 경우
                startCamera();
            }

            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {               // 거부된 경우
                // 권한이 왜 필요한지 설명해야 하나?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    // 사용자가 권한을 허용하지 않은 적이 있을 때는
                    // shouldShowRequestPermissionRationale가 true이다.
                    // 이 권한이 왜 필요한지 설명해 줄 필요가 있다.
                    // 사용자가 권한을 허용 여부를 아직 한 번도 응답하지 않은 때는
                    // shouldShowRequestPermissionRationale가 false이다.
                    // 이 권한이 왜 필요한지 설명해 주는 게 오히려 번거로운 일일 수 있기 때문이다.
                    // (이 때는 이 곳으로 올 수 없다! 이미 한 번 응답을 했기 때문에 이 곳으로 왔다!)
                    Toast.makeText(this, "사진을 찍어 외부 저장소에 저장해야 하므로 외부저장소 접근권한이 필요합니다.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    // 사용자가 권한을 허용하지 않으면서 "다시 묻지 않음"을 체크한 이후에는
                    // shouldShowRequestPermissionRationale가 false이다.
                    // 맨 처음 권한 요청 대화창에는 "다시 묻지 않음" 체크 박스가 없다.
                    // 맨 처음 권한 요청 대화창에서 권한 부여를 거부하고 나면
                    // 이 기능을 수행하기 위해 왜 권한이 필요한지 설명이 보이게 된다.
                    // 그 이후에도 사용자가 권한 부여를 거부하고 "다시 이상 묻지 않기"를 설정했다면
                    // 사용자를 더 이상 귀찮게 하지 말고 기능 자체를 불능화한다.
                    changePhotoButton.setEnabled(false);
                }

            }

        }

    }

    private void updateCroppedPhoto(int paramInt, Intent paramIntent)
    {
        if (paramInt == -1)
        {
            mlmageView.setImageURI(Crop.getOutput(paramIntent));
            savePhoto("temp.png");
            this.hasTempPhoto = true;
        }

            if (paramInt == 404) {
                Toast.makeText(this, "Error",Toast.LENGTH_SHORT ).show();
            }

    }

    public void changePhoto(View paramView)
    {
        if (ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") != 0)
            ActivityCompat.requestPermissions(this, new String[] { "android.permission.WRITE_EXTERNAL_STORAGE" }, 1002);
                   startCamera();

    }

    private void startCroppingPhoto(Uri paramUri)
    {
        Crop.of(paramUri, Uri.fromFile(new File(getCacheDir(), "cropped_photo"))).asSquare().start(this);
    }



    private void saveUserInfo()
    {
        SharedPreferences.Editor localEditor = getSharedPreferences("Profile_Info", 0).edit();

        localEditor.putBoolean("changePhotoButtonEnabled", this.changePhotoButton.isEnabled());
        localEditor.commit();
    }


    private void startCamera()
    {
        this.mImageUri = Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "temp_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
        try
        {
            startActivityForResult(intent, REQUEST_CAMERA_CAPTURE);

        }
        catch (ActivityNotFoundException localActivityNotFoundException)
        {
                localActivityNotFoundException.printStackTrace();
        }
    }


    public boolean onCreateOptionsMenu(Menu paramMenu)
    {
        getMenuInflater().inflate(R.menu.menu_main, paramMenu);
        return true;
    }

    private void loadUserInfo()
    {
        SharedPreferences localSharedPreferences = getSharedPreferences("Profile_Info", 0);

        this.changePhotoButton.setEnabled(localSharedPreferences.getBoolean("changePhotoButtonEnabled", true));
    }

    protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
    {
        if (paramInt2 != -1);

            switch (paramInt1)
            {

                case 0:
                    startCroppingPhoto(this.mImageUri);
                    break;
                case 6709:
                    updateCroppedPhoto(paramInt2, paramIntent);
                    File localFile = new File(this.mImageUri.getPath());
                    if (localFile.exists())
                        localFile.delete();
                default:
                    break;
            }

    }

    protected void onRestoreInstanceState(Bundle paramBundle)
    {
        super.onRestoreInstanceState(paramBundle);
        this.changePhotoButton.setEnabled(paramBundle.getBoolean("changePhotoButtonEnabled"));
        Log.d("changeButtonEnabled", "" + this.changePhotoButton.isEnabled());
    }

    protected void onSaveInstanceState(Bundle paramBundle)
    {
        super.onSaveInstanceState(paramBundle);
        paramBundle.putBoolean("hasTempPhoto", this.hasTempPhoto);
        paramBundle.putBoolean("changePhotoButtonEnabled", this.changePhotoButton.isEnabled());
    }



    public void selectCancel(View paramView)
    {
        finish();
    }

    public void selectSave(View paramView)
    {
        savePhoto("profile.png");
        File localFile = new File(getFilesDir().getAbsolutePath() + "/" + "temp.png");
        boolean bool = localFile.delete();
        Log.w("Delete Check", "File deleted: " + localFile + ": " + bool);
        this.hasTempPhoto = false;
        saveUserInfo();
        Toast.makeText(getApplicationContext(), "저장되었습니다.", Toast.LENGTH_SHORT).show();
    }
}

