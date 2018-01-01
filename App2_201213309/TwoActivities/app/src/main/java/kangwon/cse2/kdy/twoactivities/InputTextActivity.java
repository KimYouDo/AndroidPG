package kangwon.cse2.kdy.twoactivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class InputTextActivity extends Activity {

        EditText editText;

    public void backButtonClick(View paramView)
    {
        Intent localIntent = new Intent(getApplicationContext(), MainActivity.class);
        localIntent.putExtra("input-text", this.editText.getText());
        setResult(-1, localIntent);
        finish();
    }

    protected void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_text_input);
        this.editText = ((EditText)findViewById(R.id.editText));
    }
}