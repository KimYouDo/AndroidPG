package kangwon.cse2.kdy.friendlist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class FriendItemView extends LinearLayout {
    ImageView profileImage;
    TextView nameText;
    TextView messageText;

    public FriendItemView(Context context){
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.friend_item,this,true);
        profileImage = (ImageView)findViewById(R.id.imageView);
        nameText = (TextView)findViewById(R.id.nameText);
        messageText = (TextView)findViewById(R.id.messageText);
    }

    public FriendItemView(Context context, AttributeSet attrs) {
    super(context,attrs);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.friend_item,this,true);
        profileImage = (ImageView)findViewById(R.id.imageView);
        nameText = (TextView)findViewById(R.id.nameText);
        messageText = (TextView)findViewById(R.id.messageText);
    }


    public void setName(String name) {nameText.setText(name);}
    public void setMessage(String message){messageText.setText(message);}
    public void setImageId(int imageId){profileImage.setImageResource(imageId);}
}
