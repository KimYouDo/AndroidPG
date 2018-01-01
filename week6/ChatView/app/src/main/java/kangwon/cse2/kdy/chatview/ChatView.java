package kangwon.cse2.kdy.chatview;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * Created by Gigabyte_P15F on 2017-04-11.
 */

public class ChatView extends LinearLayout {

    private Button sendButton;
    private EditText messageText;
    private ListView messageListView;
    private ArrayAdapter adapter;

    public ChatView(Context context){
        super(context);
        init(context);
    }

    public ChatView(Context context, AttributeSet attrs){
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.chat_layout, this, true);

        sendButton = (Button)findViewById(R.id.sendButton);
        messageText = (EditText)findViewById(R.id.messageText);
        messageListView = (ListView)findViewById(R.id.messageListView);

        adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1);
        messageListView.setAdapter(adapter);

        sendButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String message = messageText.getText().toString();
                messageText.setText("");
                adapter.add(message);
            }
        });
    }

    private void init(Context context, AttributeSet attrs){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.chat_layout, this, true);

        sendButton = (Button)findViewById(R.id.sendButton);
        messageText = (EditText)findViewById(R.id.messageText);
        messageListView = (ListView)findViewById(R.id.messageListView);

        adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1);
        messageListView.setAdapter(adapter);

        sendButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String message = messageText.getText().toString();
                messageText.setText("");
                adapter.add(message);
            }
        });
    }
    public Parcelable onSaveInstanceState(){
        Bundle bundle = new Bundle();

        bundle.putParcelable("instanceState", super.onSaveInstanceState());

        String[] messages = new String[adapter.getCount()];
        for(int i=0; i<adapter.getCount(); i++)
            messages[i] = adapter.getItem(i).toString();

        bundle.putCharSequenceArray("messages", messages);

        return bundle;
    }

    public void onRestoreInstanceState(Parcelable state){
        if(state instanceof Bundle){
            Bundle bundle = (Bundle) state;
            String[] messages = (String[])bundle.getCharSequenceArray("messages");
            adapter.addAll(messages);
            super.onRestoreInstanceState(((Bundle)state).getParcelable("instanceState"));
            return;
        }
        super.onRestoreInstanceState(state);
    }



}
