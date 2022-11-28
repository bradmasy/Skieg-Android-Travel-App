package skieg.travel;

import androidx.fragment.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class EventFragment extends ListFragment {

    // Stores a list of events as strings
    String[] eventsArray;


    /**
     * When event activity is created.
     * @param savedInstanceState: bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Sets instance variable array to an array of values in the string.xml file
        eventsArray = getResources().getStringArray(R.array.eventsString);
    }

    /**
     * Sets up the fragment to a list item layout.
     * @param inflater: LayoutInflater
     * @param container: ViewGroup
     * @param savedInstanceState: bundle
     * @return view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events_layout, container, false);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1, eventsArray);
        setListAdapter(adapter);
        return view;
    }

    /**
     * When an item in the fragment is clicked, display it's value and position in the array.
     * @param l: ListView
     * @param v: View
     * @param position: integer
     * @param id: long
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Display text value and position of the item selected
        Toast.makeText(getActivity().getBaseContext(), eventsArray[position] + " Event: " + position + " ID: " + id, Toast.LENGTH_SHORT).show();
    }

}
