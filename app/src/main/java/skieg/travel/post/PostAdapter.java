package skieg.travel.post;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import skieg.travel.MainActivity;
import skieg.travel.R;




public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    int postCount; // get the user in the database and how many posts the have
    Button deleteButton;
    ArrayList<String>names;
    ArrayList<String> content ;
    ArrayList<String> dates ;
    ArrayList<String> userID;
    ViewHolder viewHolder;
    RecyclerViewClickListener itemListener;
    int currentPosition;

    public PostAdapter(ArrayList<String> names, ArrayList<String>content, ArrayList<String> dates,ArrayList<String> ids){
        this.names   = names;
        this.content = content;
        this.dates   = dates;
        this.userID  = ids;
        this.itemListener = new RecyclerViewClickListener() {
            @Override
            public void recyclerViewListClicked(View v, int position) {
                System.out.println("POSITION: " + position);
            }
        };
        postCount    = names.size();//this.content.length;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item,parent,false);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

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
            deleteButton = postView.findViewById(R.id.deleteButton);
            name         = postView.findViewById(R.id.usernamePost);
            content      = postView.findViewById(R.id.contentPost);
            date         = postView.findViewById(R.id.datePost);

            postView.setOnClickListener(this);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    System.out.println("IDS: " + userID);
                    System.out.println("HERE");
                    Drawable deleteButton = view.getResources().getDrawable(R.drawable.delete);
                    System.out.println("VIEW POSITION:" + currentPosition);
                    System.out.println("ID: " + viewHolder);
                    String postID = userID.get(currentPosition);
                    System.out.println("POST ID: " + postID);
                    System.out.println("USER ID: " + MainActivity.USER.getId());
                    if(MainActivity.USER.getId().equals(postID)){
                        System.out.println("THIS WORKED");
                    };
                    // if the users id matches give them the option to delete the post.
                    // Toast.makeText(,"Post Deleted",Toast.LENGTH_SHORT,false);
                }
            });
        }




        @Override
        public void onClick(View view) {
            itemListener.recyclerViewListClicked(view,this.getAdapterPosition());
        }
    }
}