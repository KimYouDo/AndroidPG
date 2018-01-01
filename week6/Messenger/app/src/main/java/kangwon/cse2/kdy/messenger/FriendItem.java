package kangwon.cse2.kdy.messenger;

public class FriendItem {

    private String name;
    private String message;
    private int imageId;

    public FriendItem(String name){
        this.name = name;
        this.message ="";
        this.imageId = R.mipmap.ic_launcher;
    }

    public FriendItem(String name, String message, int imageId){
        this.name = name;
        this.message = message;
        this.imageId = imageId;
    }

    public String getName(){ return this.name;}
    public String getMessage(){return this.message;}
    public int getImageId(){return this.imageId;}
    public void setName(String name) {this.name=name;}
    public void setMessage(String message){this.message=message;}
    public void setImageId(int imageId){this.imageId=imageId;}
}
