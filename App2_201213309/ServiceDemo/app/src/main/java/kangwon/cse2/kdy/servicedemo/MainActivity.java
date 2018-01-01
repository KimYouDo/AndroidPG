package kangwon.cse2.kdy.servicedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity  {

        protected void onCreate(Bundle paramBundle)
        {
            super.onCreate(paramBundle);
            setContentView(R.layout.activity_main);
        }

    protected void onDestroy()
    {
        if (!isChangingConfigurations())
            stopService(new Intent(this, MyService.class));
        super.onDestroy();
    }

    public void startService(View paramView)
    {
        startService(new Intent(this, MyService.class));
    }

    public void stopService(View paramView)
    {
        stopService(new Intent(this, MyService.class));
    }


}