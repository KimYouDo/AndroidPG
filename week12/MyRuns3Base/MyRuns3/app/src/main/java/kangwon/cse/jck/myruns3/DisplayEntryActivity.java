package kangwon.cse.jck.myruns3;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class DisplayEntryActivity extends AppCompatActivity {
    private long rowid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Create
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_entry);

        this.rowid = getIntent().getExtras().getLong("Row_Index");
        ExerciseEntry localExerciseEntry = MainActivity.DBhelper.fetchEntryByIndex(this.rowid);
        ((EditText)findViewById(R.id.display_input)).setText(StartFragment.ID_TO_INPUT[localExerciseEntry.getInputType()]);
        ((EditText)findViewById(R.id.display_activity)).setText(StartFragment.ID_TO_ACTIVITY[localExerciseEntry.getActivityType()]);
        ((EditText)findViewById(R.id.display_datetime)).setText(HistoryFragment.formatDateTime(localExerciseEntry.getDateTime()));
        ((EditText)findViewById(R.id.display_duration)).setText(HistoryFragment.formatDuration(localExerciseEntry.getDuration()));
        EditText localEditText = (EditText)findViewById(R.id.display_distance);
        String str = PreferenceManager.getDefaultSharedPreferences(this).getString(getString(R.string.unit_preference), getString(R.string.unit_km));
        localEditText.setText(HistoryFragment.formatDistance(localExerciseEntry.getDistance(), str));
        ((EditText)findViewById(R.id.display_calories )).setText(localExerciseEntry.getCalorie() + " cals");
        ((EditText)findViewById(R.id.display_heartrate)).setText(localExerciseEntry.getHeartRate() + " bpm");
    }

    // To delete data entry
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(Menu.NONE, 0, 0, "삭제").
                setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    // Perform the removal on a thread
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        runThread();
        finish();
        return true;
    }

    // Extracredit: use a background thread to delete information
    private void runThread()
    {
        new Thread()
        {
            public void run()
            {
                MainActivity.DBhelper.removeEntry(DisplayEntryActivity.this.rowid);
            }
        }
                .start();
    }
}
