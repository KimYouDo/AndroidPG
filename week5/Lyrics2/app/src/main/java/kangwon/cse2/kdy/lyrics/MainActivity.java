package kangwon.cse2.kdy.lyrics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements TitlesFragment.TitleSelectionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onTitleSelect(int position) {
        LyricsFragment lyricsFragment = (LyricsFragment)
                getSupportFragmentManager().findFragmentById(R.id.lyrics_fragment);
        lyricsFragment.updataLyricsView(position);
    }
}
