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

public class PostFragment extends Fragment {


    private RecyclerView recyclerView;
    PostAdapter postAdapter;

    // GRAB FROM DATA BASE FOR THIS.
//    String[] posts;
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> content = new ArrayList<>();
    ArrayList<String> dates = new ArrayList<>();

    public PostFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recyclerview_layout,container,false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager((view.getContext())));

        postAdapter = new PostAdapter(names,content,dates);
        recyclerView.setAdapter(postAdapter);

        return view;
    }

    public void initializeAdapter(RecyclerView.Adapter<PostAdapter.ViewHolder> adapter){
        recyclerView.setAdapter(adapter);
    }


}
