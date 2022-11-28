package skieg.travel.post;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import skieg.travel.R;

/**
 * Post fragment Class.
 */
public class PostFragment extends Fragment {


    private RecyclerView recyclerView;
    public PostAdapter postAdapter;

    // GRAB FROM DATA BASE FOR THIS.
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> content = new ArrayList<>();
    ArrayList<String> dates = new ArrayList<>();
    ArrayList<String> userID = new ArrayList<>();
    ArrayList<String> PostID = new ArrayList<>();
    ArrayList<String> countries = new ArrayList<>();

    /**
     * Post fragment blank constructor.
     */
    public PostFragment(){

    }

    /**
     * on Create View method.
     *
     * @param inflater an inflater.
     * @param container a container.
     * @param savedInstanceState a bundle representing the previously saved instance.
     * @return a view.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recyclerview_layout,container,false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager((view.getContext())));
        postAdapter = new PostAdapter(names,content,dates,userID,PostID, countries);
        recyclerView.setAdapter(postAdapter);

        return view;
    }

    /**
     * Initializes the adapter.
     *
     * @param adapter the adapter we are initializing.
     */
    public void initializeAdapter(RecyclerView.Adapter<PostAdapter.ViewHolder> adapter){
        recyclerView.setAdapter(adapter);
    }
}