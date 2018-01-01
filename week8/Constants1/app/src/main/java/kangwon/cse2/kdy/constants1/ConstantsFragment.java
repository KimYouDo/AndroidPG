package kangwon.cse2.kdy.constants1;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.ContentValues;
import android.os.Bundle;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConstantsFragment extends ListFragment {

    private DatabaseHelper dbHelper = null;
    private Cursor current = null;

    public ConstantsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SimpleCursorAdapter adapter =
                new SimpleCursorAdapter(getContext(), R.layout.row,
                        current, new String[]{
                        DatabaseHelper.TITLE,
                        DatabaseHelper.VALUE},
                        new int[]{R.id.title, R.id.value},
                        0);
        setListAdapter(adapter);

        if (current == null) {
            dbHelper = new DatabaseHelper(getContext());
            current = doQuery();
            adapter.changeCursor(current);
        }
        return inflater.inflate(android.R.layout.list_content, container, false);
    }

    private Cursor doQuery() {
        Cursor result = dbHelper.getReadableDatabase().query(DatabaseHelper.TABLE,
                new String[]{"ROWID AS _id", DatabaseHelper.TITLE, DatabaseHelper.VALUE},
                null, null, null, null, DatabaseHelper.TITLE);

        result.getCount();

        return (result);
    }

    public void onDestroy() {
        ((CursorAdapter) getListAdapter()).getCursor().close();
        dbHelper.close();

        super.onDestroy();
    }
}
