package skieg.travel.post;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import skieg.travel.R;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    int postCount; // get the user in the database and how many posts the have
    ArrayList<String>names;
    ArrayList<String> content ;
    ArrayList<String> dates ;


    public PostAdapter(ArrayList<String> names, ArrayList<String>content, ArrayList<String> dates){
        this.names = names;
        this.content = content;
        this.dates = dates;
        postCount = names.size();//this.content.length;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        System.out.println("position: " + position);
        holder.name.setText(names.get(position));
        holder.content.setText(content.get(position));
        holder.date.setText(dates.get(position));

    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView name, date, content;
        public ViewHolder(@NonNull View postView){
            super(postView);
            name    = postView.findViewById(R.id.usernamePost);
            content = postView.findViewById(R.id.contentPost);
            date    = postView.findViewById(R.id.datePost);
        }

        @Override
        public void onClick(View view) {

        }
    }
}