package kangwon.cse2.kdy.twoactivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

        public static final int START_TEXT_INPUT_ACTIVITY_CODE = 1;
        private TextView textView;

    public void buttonClick(View paramView)
    {
        startActivityForResult(new Intent(getApplicationContext(), InputTextActivity.class), START_TEXT_INPUT_ACTIVITY_CODE);
    }

    protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
    {
        if ((paramInt1 == 1) && (paramInt2 == -1))
            this.textView.setText(this.textView.getText().toString() + paramIntent.getCharSequenceExtra("input-text"));
        super.onActivityResult(paramInt1, paramInt2, paramIntent);
    }

    protected void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_main);
        this.textView = ((TextView)findViewById(R.id.textView));
        if ((paramBundle != null) && (paramBundle.keySet().contains("textview")))
            this.textView.setText(paramBundle.getCharSequence("textview"));
    }

    protected void onSaveInstanceState(Bundle paramBundle)
    {
        super.onSaveInstanceState(paramBundle);
        paramBundle.putCharSequence("textview", this.textView.getText());
    }
}