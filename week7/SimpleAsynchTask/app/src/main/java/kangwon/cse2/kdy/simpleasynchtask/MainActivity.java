package kangwon.cse2.kdy.simpleasynchtask;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    int max;
    Button btn;
    ProgressBar bar;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button)findViewById(R.id.button1);

        btn.setOnClickListener(this);

        bar = (ProgressBar)findViewById(R.id.progressBar);
        editText = (EditText)findViewById(R.id.max);
    }

    public void onClick(View view){
        String s = editText.getText().toString();
        try {
            max = Integer.parseInt(s);
        }catch (NumberFormatException e){
            e.printStackTrace();
        }

        bar.setMax(max);

        switch (view.getId()){
            case R.id.button1:
                new LongOperation().execute(max);
                break;
        }
    }

    private class LongOperation extends AsyncTask<Integer, Integer, String>{
        protected String doInBackground(Integer... params){
            int upTo = params[0];
            for(int i=1; i<=upTo; i++){
                try{
                    Thread.sleep(1000);
                    publishProgress(i);
                }catch (InterruptedException e){
                    Thread.interrupted();
                }
            }
            return  "작업 완료!";
        }


        protected void onPostExecute(String result){
            TextView txt = (TextView) findViewById(R.id.output);
            txt.setText(result);
            txt.setTextColor(Color.RED);
        }

        protected void onProgressUpdate(Integer... values){
            bar.setProgress(values[0]);
        }
    }
}
