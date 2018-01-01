package kangwon.cse2.kdy.lyrics;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements TitlesFragment.TitleSelectionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(findViewById(R.id.fragment_container) != null){
            if(savedInstanceState != null){
                return;
            }

            TitlesFragment titlesFragment = new TitlesFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, titlesFragment).commit();
        }
    }

    @Override
    public void onTitleSelect(int position) {
        LyricsFragment lyricsFragment = (LyricsFragment)
                getSupportFragmentManager().findFragmentById(R.id.lyrics_fragment);

        if(lyricsFragment !=null) {
            lyricsFragment.updataLyricsView(position);
        }
        else{
            LyricsFragment newFragment = new LyricsFragment();
            Bundle args = new Bundle();
            args.putInt(LyricsFragment.ARG_POSITION, position);
            newFragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.fragment_container,newFragment);
            transaction.addToBackStack(null);

            transaction.commit();
        }
    }
}
