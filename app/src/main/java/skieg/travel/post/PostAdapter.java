package skieg.travel.post;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import skieg.travel.R;
import skieg.travel.RecyclerViewCalendar;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    Context c;
    int postCount; // get the user in the database and how many posts the have
    String[] names;
    String[] content;
    String[] dates;


    public PostAdapter( String[] names, String[] content, String[] dates){
        this.names = names;
        this.content = content;
        this.dates = dates;
        postCount = this.content.length;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(names[position]);
        holder.content.setText(content[position]);
        holder.date.setText(names[position]);

    }

    @Override
    public int getItemCount() {
        return postCount;
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