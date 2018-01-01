package kangwon.cse2.kdy.iageswitcher_v3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import static android.R.id.button1;

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

        Button button11 = (Button) findViewById(R.id.button1);
        Button button22 = (Button) findViewById(R.id.button2);

        class ButtonListener implements View.OnClickListener {
            public void onClick(View v){
                switch (v.getId())  {
                    case R.id.button1:
                        image1.setVisibility(View.VISIBLE);
                        image2.setVisibility(View.INVISIBLE);
                        break;

                    case R.id.button2:
                        image1.setVisibility(View.INVISIBLE);
                        image2.setVisibility(View.VISIBLE);
                }

            }
        }

        View.OnClickListener clickListener = new ButtonListener();

        button11.setOnClickListener(clickListener);
        button22.setOnClickListener(clickListener);
    }


}
