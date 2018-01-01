package kangwon.cse2.kdy.lyrics;

import android.app.Activity;
import android.content.Context;
import android.provider.Settings;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class TitlesFragment extends ListFragment  {

    private TitleSelectionListener listener;

    public TitlesFragment() {
        // Required empty public constructor
    }



    public interface TitleSelectionListener {
        public void onTitleSelect(int position);
    }

    public
    void onActivityCreated(Bundle savedlnstanceState) {
        super.onActivityCreated(savedlnstanceState);
        setListAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_activated_1, SongData.Title));
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (TitleSelectionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnShowButtonTouchListener");
        }
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        listener.onTitleSelect(position);
    }


    public void onStart() {
        super.onStart();
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

    }

}