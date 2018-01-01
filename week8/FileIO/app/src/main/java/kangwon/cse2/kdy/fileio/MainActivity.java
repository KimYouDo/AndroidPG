package kangwon.cse2.kdy.fileio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private static  final String TAG_CONTACTS_FRAGMENT = "contacts_fragment";
    private ContactsFragment fragment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null){
            fragment = new ContactsFragment();

            getSupportFragmentManager().beginTransaction()
                    .replace(android.R.id.content, fragment, TAG_CONTACTS_FRAGMENT).commit();
        }else{
            fragment = (ContactsFragment)getSupportFragmentManager().findFragmentByTag(TAG_CONTACTS_FRAGMENT);
        }
    }
}
