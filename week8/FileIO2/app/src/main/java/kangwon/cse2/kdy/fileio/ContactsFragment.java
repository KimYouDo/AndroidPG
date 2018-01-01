package kangwon.cse2.kdy.fileio;


import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends Fragment implements View.OnClickListener{

    private Button addButton;
    private Button clearButton;
    private Button saveButton;
    private Button loadButton;
    private EditText messageText;
    private ListView listView;
    private ArrayAdapter adapter;
    private ArrayList<String> contactsList;

    private  static  final String STATE_CONTACTS ="contacts";

    public ContactsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        addButton = (Button) view.findViewById(R.id.addButton);
        clearButton = (Button) view.findViewById(R.id.clearButton);
        saveButton = (Button) view.findViewById(R.id.saveButton);
        loadButton = (Button) view.findViewById(R.id.loadButton);
        addButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        loadButton.setOnClickListener(this);

        messageText = (EditText) view.findViewById(R.id.messageText);
        listView = (ListView) view.findViewById(R.id.listView);
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        if(savedInstanceState != null){
            contactsList = (ArrayList<String>)savedInstanceState.getStringArrayList(STATE_CONTACTS);
        }else {
            contactsList = new ArrayList<String>();
        }

        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, contactsList);
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.addButton:
                add();
                break;
            case R.id.clearButton:
                clear();
                break;
            case R.id.saveButton:
                save();
                break;
            case R.id.loadButton:
                load();
                break;
        }
    }

    private void add(){
        String message = messageText.getText().toString();
        messageText.setText("");
        adapter.add(message);
    }

    private void clear(){
        adapter.clear();
    }

    private void save(){
        File file = null;
        if(isExternalStorageWritable())
            file = new File(getContext().getExternalFilesDir(null),"myData.txt");

        try {
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
            BufferedWriter bw = new BufferedWriter(osw);

            for(int i =0; i<adapter.getCount(); i++){
                bw.write(adapter.getItem(i).toString());
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            Log.e(getClass().getSimpleName(),"Data saving error!");
            e.printStackTrace();
        }
    }
    private void load(){
        File file = null;
        if(isExternalStorageReadable())
           file = new File(getContext().getExternalFilesDir(null),"myData.txt");
        
        
        try {
            FileInputStream fos = new FileInputStream(file);
            InputStreamReader osw = new InputStreamReader(fos, "UTF-8");
            BufferedReader bw = new BufferedReader(osw);

            String str;
            adapter.clear();
            while ((str=bw.readLine())!=null)
                adapter.add(str);

            bw.close();
        } catch (IOException e) {
            Log.e(getClass().getSimpleName(),"Data loading error!");
            e.printStackTrace();
        }

    }

    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(STATE_CONTACTS, contactsList);
    }

    public boolean isExternalStorageWritable(){
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)){
            return true;
        }
        return false;
    }

    public boolean isExternalStorageReadable(){
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)){
            return true;
        }
        return false;
    }

}
