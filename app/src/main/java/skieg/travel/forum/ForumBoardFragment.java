package skieg.travel.forum;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import skieg.travel.R;

/**
 * Forum board fragment class.
 */
public class ForumBoardFragment extends ListFragment {

    String[] posts;

    /**
     * On Create View.
     *
     * @param inflater a layout inflater.
     * @param container a container.
     * @param savedInstanceState a bundle of saved instance data from the previous activity.
     * @return an inflated view.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events_layout, container, false);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1, posts);
        setListAdapter(adapter);
        return view;
    }
}
