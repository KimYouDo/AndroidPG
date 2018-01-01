package kangwon.cse2.kdy.myruns2;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.widget.EditText;


public class MyRunsDialogFragment extends DialogFragment {
    public static final int CALORIES_PICKER_ID = 3;
    public static final int COMMENT_PICKER_ID = 5;
    private static final String DIALOG_ID_KEY = "which_dialog";
    public static final int DISTANCE_PICKER_ID = 2;
    public static final int DURATION_PICKER_ID = 1;
    public static final int HEARTRATE_PICKER_ID = 4;
    public static final int PHOTO_PICKER_ID = 0;
    public static final int SELECT_FROM_CAMERA = 0;
    public static final int SELECT_FROM_GALLERY = 1;

    public static MyRunsDialogFragment newInstance(int dialog_id) {

        MyRunsDialogFragment dialogFrag = new MyRunsDialogFragment();
        Bundle inputBundle = new Bundle();
        inputBundle.putInt(DIALOG_ID_KEY, dialog_id);
        dialogFrag.setArguments(inputBundle);

        return dialogFrag;
    }


    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Activity localActivity = getActivity();
        int dialog_id = getArguments().getInt(DIALOG_ID_KEY);

        android.app.AlertDialog.Builder localBuilder = new android.app.AlertDialog.Builder(localActivity);

        switch (dialog_id) {

            case PHOTO_PICKER_ID:
                buildPhotoPickerDialog(localBuilder,localActivity);
                return localBuilder.create();

            case DURATION_PICKER_ID:
                buildEditPickerDialog(localBuilder, localActivity, "지속 시간", dialog_id);
                return localBuilder.create();

            case DISTANCE_PICKER_ID:
                buildEditPickerDialog(localBuilder, localActivity, "거리", dialog_id);
                return localBuilder.create();

            case CALORIES_PICKER_ID:
                buildEditPickerDialog(localBuilder, localActivity, "칼로리", dialog_id);
                return localBuilder.create();

            case HEARTRATE_PICKER_ID:
                buildEditPickerDialog(localBuilder, localActivity, "백박 수", dialog_id);
                return localBuilder.create();

            case COMMENT_PICKER_ID:
                buildEditPickerDialog(localBuilder, localActivity, "메모", dialog_id);
                return localBuilder.create();

            default:
                return null;
        }
    }


    private void buildPhotoPickerDialog(android.app.AlertDialog.Builder dialogBuilder, final Activity parent){
        dialogBuilder.setTitle("사진 가져오는 방법");

        // Create listener
        DialogInterface.OnClickListener photoPickerlistener =
                new DialogInterface.OnClickListener() {

                    @Override // Override the onClick method
                    public void onClick(DialogInterface dialog, int item) {
                        ((UserProfileActivity) parent).selectPhotoPickerItem(item);
                    }
                };

        // Create the dialog
        dialogBuilder.setItems(R.array.ui_photo_picker_list, photoPickerlistener);
    }

    /**
     * build the edit number or text picker dialog
     * @param dialogBuilder
     * @param parent
     */
    private void buildEditPickerDialog(final android.app.AlertDialog.Builder dialogBuilder, final Activity parent,
                                       final String title, int id){
        dialogBuilder.setTitle(title);

        // Use an EditText view to get user input
        final EditText input = new EditText(parent);

        if(id == DISTANCE_PICKER_ID || id == DURATION_PICKER_ID) {
            input.setInputType(InputType.TYPE_CLASS_NUMBER |
                    InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        }else if (id == COMMENT_PICKER_ID) {
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            input.setHint("어땠어요?");
            input.setLines(4);
        }else{
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
        }

        dialogBuilder.setView(input);

        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int btnid) {
                String value = input.getText().toString();

                switch (title) {
                    case "지속 시간":
                        double valueInSeconds = Double.parseDouble(value)*60;
                        ((ManualEntryActivity) parent).entry.
                                setmDuration(!value.equals("") ? valueInSeconds : 0);
                        break;
                    case "거리":
                        double val;
                        if(!value.equals(""))
                            val = Double.parseDouble(value);
                        else
                            val = 0;

                        // Find unit
                        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                        String unitPref = pref.getString("unit_preference","Kilometers");

                        if (unitPref.equals("Miles")) {
                            val *= ManualEntryActivity.MILES2KM;
                        }
                        ((ManualEntryActivity) parent).entry.
                                setmDistance(!value.equals("") ? val : 0);
                        break;
                    case "칼로리":
                        ((ManualEntryActivity) parent).entry.
                                setmCalorie(!value.equals("") ? Integer.parseInt(value) : 0);
                        break;
                    case "백박 수":
                        ((ManualEntryActivity) parent).entry.
                                setmHeartRate(!value.equals("") ? Integer.parseInt(value) : 0);
                        break;
                    case "메모":
                        ((ManualEntryActivity) parent).entry.setmComment(value);
                        break;
                }
                return;
            }
        });

        dialogBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int btnid) {
                return;
            }
        });
    }
}