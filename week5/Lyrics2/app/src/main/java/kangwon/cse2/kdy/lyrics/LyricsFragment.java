package kangwon.cse2.kdy.lyrics;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */


public class LyricsFragment extends Fragment {

    int mCurrentPosition=-1;
    final static  String ARG_POSITION = "position";

    public LyricsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(savedInstanceState != null)
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lyrics, container, false);
    }

    public void onStart(){
        super.onStart();
        if(mCurrentPosition != -1)
            updataLyricsView(mCurrentPosition);
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ARG_POSITION, mCurrentPosition);
    }

    public void updataLyricsView(int position){
        TextView lyricsTextView = (TextView) getView().findViewById(R.id.lyricsTextView);
        lyricsTextView.setText(SongData.Lyrics[position]);
        mCurrentPosition = position;
    }
}
