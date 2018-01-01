package kangwon.cse2.kdy.myruns2;

import android.app.DatePickerDialog;
import android.app.ListActivity;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class ManualEntryActivity extends ListActivity {

    private static final int MANUAL_CALORIES = 4;
    private static final int MANUAL_COMMENT = 6;
    private static final int MANUAL_DATE = 0;
    private static final int MANUAL_DISTANCE = 3;
    private static final int MANUAL_DURATION = 2;
    private static final int MANUAL_HEARTRATE = 5;
    public static final String[] MANUAL_OPTIONS = {"날짜", "시각", "지속 시간", "거리", "칼로리", "맥박 수", "메모"};
    private static final int MANUAL_TIME = 1;
    public static final double MILES2CAL = 100.0D;
    public static final double MILES2KM = 1.60934D;
    public static ExerciseEntry entry;
    public Calendar mDateAndTime;

    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_manual_entry);
        Bundle localBundle = getIntent().getExtras();
        entry = new ExerciseEntry();
        entry.setmInputType(localBundle.getInt("input_type", 0));
        entry.setmActivityType(localBundle.getInt("activity_type", 0));
        this.mDateAndTime = Calendar.getInstance();
        entry.setmDateTime(this.mDateAndTime.getTimeInMillis());
        Log.d("date", this.mDateAndTime.getTimeInMillis() + "");
        entry.setmDuration(0.0D);
        entry.setmDistance(0.0D);
        entry.setmCalorie(0);
        entry.setmHeartRate(0);
        setListAdapter(new ArrayAdapter(this, R.layout.list_manual, MANUAL_OPTIONS));
        final ListView localListView = getListView();
        localListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong) {
                switch (paramAnonymousInt) {
                    case MANUAL_DATE:
                        ManualEntryActivity.this.selectManualDate(localListView);
                        break;
                    case MANUAL_TIME:
                        ManualEntryActivity.this.selectManualTime(localListView);
                        break;
                    case MANUAL_DURATION:
                        MyRunsDialogFragment.newInstance(MyRunsDialogFragment.DURATION_PICKER_ID).show(ManualEntryActivity.this.getFragmentManager(), "Distance");
                        break;
                    case MANUAL_DISTANCE:
                        MyRunsDialogFragment.newInstance(MyRunsDialogFragment.DISTANCE_PICKER_ID).show(ManualEntryActivity.this.getFragmentManager(), "Distance");
                        break;
                    case MANUAL_CALORIES:
                        MyRunsDialogFragment.newInstance(MyRunsDialogFragment.CALORIES_PICKER_ID).show(ManualEntryActivity.this.getFragmentManager(), "Calories");
                        break;
                    case MANUAL_HEARTRATE:
                        MyRunsDialogFragment.newInstance(MyRunsDialogFragment.HEARTRATE_PICKER_ID).show(ManualEntryActivity.this.getFragmentManager(), "Heart Rate");
                        break;
                    case MANUAL_COMMENT:
                        MyRunsDialogFragment.newInstance(MyRunsDialogFragment.COMMENT_PICKER_ID).show(ManualEntryActivity.this.getFragmentManager(), "Comments");
                        break;
                    default:
                        return;
                }

            }
        });
    }

    public void selectManualCancel(View paramView) {
        Toast.makeText(getApplicationContext(), "저장되지 않았습니다.", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void selectManualDate(View paramView) {
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker paramAnonymousDatePicker, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {
                ManualEntryActivity.this.mDateAndTime.set(Calendar.YEAR, paramAnonymousInt1);
                ManualEntryActivity.this.mDateAndTime.set(Calendar.MONTH, paramAnonymousInt2);
                ManualEntryActivity.this.mDateAndTime.set(Calendar.DAY_OF_MONTH, paramAnonymousInt3);
                ManualEntryActivity.entry.setmDateTime(ManualEntryActivity.this.mDateAndTime.getTimeInMillis());
            }
        }
                , this.mDateAndTime.get(Calendar.YEAR), this.mDateAndTime.get(Calendar.MONTH), this.mDateAndTime.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void selectManualSave(View paramView) {
        Toast.makeText(getApplicationContext(), "" + entry, Toast.LENGTH_SHORT).show();
        finish();
    }

    public void selectManualTime(View paramView) {
        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker paramAnonymousTimePicker, int paramAnonymousInt1, int paramAnonymousInt2) {
                ManualEntryActivity.this.mDateAndTime.set(Calendar.HOUR_OF_DAY, paramAnonymousInt1);
                ManualEntryActivity.this.mDateAndTime.set(Calendar.MINUTE, paramAnonymousInt2);
                ManualEntryActivity.this.mDateAndTime.set(Calendar.SECOND, 0);
                ManualEntryActivity.entry.setmDateTime(ManualEntryActivity.this.mDateAndTime.getTimeInMillis());
            }
        }
                , this.mDateAndTime.get(Calendar.HOUR_OF_DAY), this.mDateAndTime.get(Calendar.MINUTE), true).show();
    }
}