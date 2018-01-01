package kangwon.cse2.kdy.friendlist;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String[] friendList = {"홍길동", "박보영", "박형식", "지수", "도봉순", "안민력", "인국두"};

    FriendAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new FriendAdapter();

        adapter.addItem(new FriendItem("홍길동", "아버지를 아버지라 부르지 못하고...",R.drawable.profile_icon));
        adapter.addItem(new FriendItem("박보영", "안녕하세요.",R.drawable.profile_icon));
        adapter.addItem(new FriendItem("박형식", "열심히 살자!",R.drawable.profile_icon));
        adapter.addItem(new FriendItem("지수", "잘 부탁드립니다.",R.drawable.profile_icon));
        adapter.addItem(new FriendItem("도봉순", "특별한 킹통",R.drawable.profile_icon));
        adapter.addItem(new FriendItem("안인혁", "친구에서 애인이 되는법은 간단한데...",R.drawable.profile_icon));
        adapter.addItem(new FriendItem("인국두", "봉순",R.drawable.profile_icon));


       // final ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, friendList);
        ListView friendView = (ListView)findViewById(R.id.friendLostView);
        friendView.setAdapter(adapter);
        friendView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);


        friendView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                FriendItem item = (FriendItem)adapter.getItem(position);
                Toast.makeText(getApplicationContext(), item.getName()+"님에게 메시지를 보냅니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }



    public class FriendAdapter extends BaseAdapter{
        ArrayList<FriendItem> items = new ArrayList<FriendItem>();

        public int getCount(){
            return items.size();
        }

        public void addItem(FriendItem item){
            items.add(item);
        }

        public Object getItem(int position){
            return items.get(position);
        }

        public  long getItemId(int position){
            return position;
        }

        public View getView(int position, View convertView, ViewGroup viewGroup){
            FriendItemView view;
            if(convertView==null)
                view= new FriendItemView(getApplicationContext());
            else
                view = (FriendItemView)convertView;

            FriendItem item = items.get(position);
            view.setName(item.getName());
            view.setMessage(item.getMessage());
            view.setImageId(item.getImageId());

            return view;
        }


    }
}

