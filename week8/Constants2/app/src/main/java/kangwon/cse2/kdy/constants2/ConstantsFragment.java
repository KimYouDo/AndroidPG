package kangwon.cse2.kdy.constants2;


import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class ConstantsFragment extends ListFragment implements
        DialogInterface.OnClickListener {

    private DatabaseHelper dbHelper = null;
    private Cursor current = null;

    DialogInterface deleteDialog = null;
    DialogInterface addDialog = null;

    public ConstantsFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.actions, menu);

        super.onCreateOptionsMenu(menu,inflater);
    }

    public void onClick(DialogInterface di, int whichButton){
        AlertDialog dig =  (AlertDialog) di;

        if(addDialog == di) {
            ContentValues values = new ContentValues(2);

            EditText title = (EditText) dig.findViewById(R.id.title);
            EditText value = (EditText) dig.findViewById(R.id.value);

            values.put(DatabaseHelper.TITLE, title.getText().toString());
            values.put(DatabaseHelper.VALUE, value.getText().toString());

            doInsert(values);
        }
        if(deleteDialog == di){
            EditText title = (EditText) dig.findViewById(R.id.title);

            doDelete(title.getText().toString());
        }
        current = doQuery();
        CursorAdapter adapter = (CursorAdapter)getListAdapter();
        adapter.changeCursor(current);
    }

    private  void doDelete(String title){
        dbHelper.getWritableDatabase().delete(DatabaseHelper.TABLE,
                DatabaseHelper.TITLE+"='"+title+"'",null);
    }
    private void doInsert(ContentValues... values){
        dbHelper.getWritableDatabase().insert(DatabaseHelper.TABLE,
                DatabaseHelper.TITLE, values[0]);
    }

    private void delete(){
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View addView = inflater.inflate(R.layout.delete_edit,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        deleteDialog = builder.setTitle("Delete a Constanst").setView(addView)
                .setPositiveButton("OK", this)
                .setNegativeButton("Cancel", null).show();
    }
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==R.id.add){
            add();
            return (true);
        }
        else if (item.getItemId()==R.id.delete){
            delete();
            return (true);
        }
        return (super.onOptionsItemSelected(item));
    }

    private void add(){
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View addView = inflater.inflate(R.layout.add_edit,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

       addDialog = builder.setTitle("Add Constanst").setView(addView)
                .setPositiveButton("OK",  this)
                .setNegativeButton("Cancel",null).show();
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
