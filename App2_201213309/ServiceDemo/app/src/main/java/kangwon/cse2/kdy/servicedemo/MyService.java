package kangwon.cse2.kdy.servicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

    protected Thread t;

    public IBinder onBind(Intent paramIntent)
    {
        return null;
    }

    public void onDestroy()
    {
        super.onDestroy();
        this.t.interrupt();
    }

    public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
    {
        this.t = new Thread(new Runnable()
        {
            private int count;

            public void run()
            {
                int i = 0;
                while (i == 0)
                {
                    int j = this.count;
                    this.count = (j + 1);
                    Log.i("MyService: count", String.valueOf(j));
                    try
                    {
                        Thread.sleep(500L);
                    }
                    catch (InterruptedException localInterruptedException)
                    {
                        Log.i("MyService", "Interrupted! --> thread stops.");
                        i = 1;
                    }
                }
            }
        });
        this.t.start();
        return paramInt1;
    }
}