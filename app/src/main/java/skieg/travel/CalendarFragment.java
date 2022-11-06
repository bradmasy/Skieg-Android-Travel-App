package skieg.travel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CalendarFragment extends Fragment {

    private RecyclerView recyclerView;
    RecyclerViewCalendar recyclerViewCalendar;

    ArrayList<String> titles = new ArrayList<>();
    ArrayList<String> descriptions = new ArrayList<>();
    ArrayList<String> locations = new ArrayList<>();
    ArrayList<String> dates = new ArrayList<>();

    public CalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recyclerview_layout, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        recyclerViewCalendar = new RecyclerViewCalendar(titles, descriptions, locations, dates);
        recyclerView.setAdapter(recyclerViewCalendar);

        return view;
    }

    // Sets a RecyclerViewProduct adapter to this fragment's recycler view
    public void initializeAdapter(RecyclerView.Adapter<RecyclerViewCalendar.MyViewHolder> adapter) {
        recyclerView.setAdapter(adapter);
    }
}
