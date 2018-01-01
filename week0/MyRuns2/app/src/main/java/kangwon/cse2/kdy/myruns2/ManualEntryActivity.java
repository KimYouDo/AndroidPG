package kangwon.cse2.kdy.myruns2;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ManualEntryActivity extends ListActivity {

    String[] MANUAL_OPTIONS = {"날짜","시간","지속 시간", "거리","칼로리","맥박 수","메모"};
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_entry);

        ArrayAdapter<String> manualAdapter = new ArrayAdapter<  > (this, R.layout.list_manual,
                MANUAL_OPTIONS);
        setListAdapter(manualAdapter);

        final ListView manualListView = getListView();

        AdapterView.OnItemClickListener mListener = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {
                }
            }

        };
    }
}
