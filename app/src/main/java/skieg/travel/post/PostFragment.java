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

import skieg.travel.R;

public class PostFragment extends Fragment {


    private RecyclerView recyclerView;
    PostAdapter postAdapter;

    String[] posts;
    String[] names;
    String[] content;
    String[] dates;

    public PostFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recyclerview_layout,container,false);
        recyclerView = view.findViewById(R.id.recyclerPosts);
        recyclerView.setLayoutManager(new LinearLayoutManager((view.getContext())));
        postAdapter = new PostAdapter(names,content,dates);
        recyclerView.setAdapter(postAdapter);

        return view;
    }
}
