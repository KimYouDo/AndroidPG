package kangwon.cse2.kdy.iageswitcher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView image1;
    ImageView image2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         image1 = (ImageView) findViewById(R.id.inageView1);
         image2 = (ImageView) findViewById(R.id.inageView2);

        image1.setVisibility(View.VISIBLE);
        image2.setVisibility(View.INVISIBLE);
    }

    public void onUpButtonClick(View v){

        image1.setVisibility(View.VISIBLE);
        image2.setVisibility(View.INVISIBLE);
    }

    public void onDownButtonClick(View v){
        image1.setVisibility(View.INVISIBLE);
        image2.setVisibility(View.VISIBLE);
    }
}
