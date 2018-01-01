package kangwon.cse2.kdy.constants3;


import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConstantsFragment extends ListFragment implements
        DialogInterface.OnClickListener {

    private DatabaseHelper dbHelper = null;
    private Cursor current = null;
    private AsyncTask task = null;

    DialogInterface deleteDialog = null;
    DialogInterface addDialog = null;

    public ConstantsFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.actions, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    public void onClick(DialogInterface di, int whichButton) {
        AlertDialog dig = (AlertDialog) di;

        if (addDialog == di) {
            ContentValues values = new ContentValues(2);
            EditText title = (EditText) dig.findViewById(R.id.title);
            EditText value = (EditText) dig.findViewById(R.id.value);

            values.put(DatabaseHelper.TITLE, title.getText().toString());
            values.put(DatabaseHelper.VALUE, value.getText().toString());

            task = new InsertTask().execute(values);
        }

        if (deleteDialog == di) {
            EditText title = (EditText) dig.findViewById(R.id.title);

            task = new DeleteTask().execute(title.getText().toString());
        }

    }

    private void doDelete(String title) {
        dbHelper.getWritableDatabase().delete(DatabaseHelper.TABLE,
                DatabaseHelper.TITLE + "='" + title + "'", null);
    }

    private void doInsert(ContentValues... values) {
        dbHelper.getWritableDatabase().insert(DatabaseHelper.TABLE,
                DatabaseHelper.TITLE, values[0]);
    }

    private void delete() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View addView = inflater.inflate(R.layout.delete_edit, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        deleteDialog = builder.setTitle("Delete a Constanst").setView(addView)
                .setPositiveButton("OK", this)
                .setNegativeButton("Cancel", null).show();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add) {
            add();
            return (true);
        } else if (item.getItemId() == R.id.delete) {
            delete();
            return (true);
        }
        return (super.onOptionsItemSelected(item));
    }

    private void add() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View addView = inflater.inflate(R.layout.add_edit, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        addDialog = builder.setTitle("Add Constanst").setView(addView)
                .setPositiveButton("OK", this)
                .setNegativeButton("Cancel", null).show();
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

            task = new LoadCursorTask().execute();
        }
        return inflater.inflate(android.R.layout.list_content, container, false);
    }

    public void onDestroy() {
        if (task != null) {
            task.cancel(false);
        }

        ((CursorAdapter) getListAdapter()).getCursor().close();
        dbHelper.close();

        super.onDestroy();
    }

    abstract private class BaseTask<T> extends AsyncTask<T, Void, Cursor> {
        public void onPostExecute(Cursor result) {
            ((CursorAdapter) getListAdapter()).changeCursor(result);
            current = result;
            task = null;
        }

        Cursor doQuery() {
            Cursor result =
                    dbHelper
                            .getReadableDatabase()
                            .query(DatabaseHelper.TABLE,
                                    new String[]{"ROWID AS _id",
                                            DatabaseHelper.TITLE,
                                            DatabaseHelper.VALUE},
                                    null, null, null, null, DatabaseHelper.TITLE);

            result.getCount();

            return (result);
        }
    }

    private class LoadCursorTask extends BaseTask<Void> {
        protected Cursor doInBackground(Void... params) {
            return (doQuery());
        }
    }

    private class InsertTask extends BaseTask<ContentValues> {
        protected Cursor doInBackground(ContentValues... values) {
            dbHelper.getWritableDatabase().insert(DatabaseHelper.TABLE,
                    DatabaseHelper.TITLE, values[0]);

            return (doQuery());
        }
    }

    private class DeleteTask extends BaseTask<String> {
        protected Cursor doInBackground(String... title) {

            dbHelper.getWritableDatabase().delete(DatabaseHelper.TABLE,
                    DatabaseHelper.TITLE + "='" + title[0] + "'", null);

            return (doQuery());
        }
    }
}
