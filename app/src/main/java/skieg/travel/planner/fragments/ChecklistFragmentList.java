//package skieg.travel.planner.fragments;
//
//import androidx.fragment.app.ListFragment;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//import android.widget.Toast;
//
//import androidx.fragment.app.Fragment;
//
//public class ChecklistFragmentList extends ListFragment {
//
//    String[] eventsArray;
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        eventsArray = getResources().getStringArray(R.array.eventsString);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_events_layout, container, false);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1, eventsArray);
//        setListAdapter(adapter);
//        return view;
//    }
//
//
//    @Override
//    public void onListItemClick(ListView l, View v, int position, long id) {
//        Toast.makeText(getActivity().getBaseContext(), eventsArray[position] + " Event: " + position + " ID: " + id, Toast.LENGTH_SHORT).show();
//    }
//
//}
