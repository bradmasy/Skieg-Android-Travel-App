package skieg.travel.post;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.function.BiConsumer;

import skieg.travel.MainActivity;
import skieg.travel.R;


/**
 * Post adapter class.
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    int postCount; // get the user in the database and how many posts the have
    Button deleteButton;
    ArrayList<String>names;
    ArrayList<String> content ;
    ArrayList<String> dates ;
    ArrayList<String> userID;
    ArrayList<String> postID;
    ArrayList<String> countries;
    ViewHolder viewHolder;
    RecyclerViewClickListener itemListener;
    int currentPosition = 0;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    /**
     * Constructor for post adapter.
     *
     * @param names an arraylist of names
     * @param content an array list filled with the post content.
     * @param dates an array list of dates.
     * @param ids an array list of ids/
     * @param postIDS an array list of post ids.
     * @param countryVals an arraylist of countries.
     */
    public PostAdapter(ArrayList<String> names, ArrayList<String>content,
                       ArrayList<String> dates,ArrayList<String> ids,ArrayList<String>postIDS, ArrayList<String> countryVals){
        this.names   = names;
        this.content = content;
        this.dates   = dates;
        this.userID  = ids;
        this.postID = postIDS;
        this.countries = countryVals;
        this.itemListener = new RecyclerViewClickListener() {
            @Override
            public int recyclerViewListClicked(View v, int position) {
                return position;
            }
        };
        postCount    = names.size();//this.content.length;
    }


    /**
     * On Create View Holder.
     *
     * @param parent a parent view group.
     * @param viewType an int view type.
     *
     * @return a view holder.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item,parent,false);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    /**
     * Binds the view holder.
     *
     * @param holder a view holder.
     * @param position the position.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.name.setText(names.get(position) + " says...");
        holder.contentView.setText(content.get(position));
        holder.date.setText(dates.get(position));
    }

    /**
     * Gets the item count.
     *
     * @return the item amount.
     */
    @Override
    public int getItemCount() {
        return names.size();
    }

    /**
     * View Holder Class.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView name, date, contentView;

        /**
         * View Holder Constructor.
         *
         * @param postView a view.
         */
        public ViewHolder(@NonNull View postView){
            super(postView);
            Drawable deleteButtonImg = postView.getResources().getDrawable(R.drawable.delete);
            deleteButton = postView.findViewById(R.id.deleteButton);
            name         = postView.findViewById(R.id.usernamePost);
            contentView = postView.findViewById(R.id.contentPost);
            date         = postView.findViewById(R.id.datePost);

            int pos = currentPosition;
            if(MainActivity.USER.getId().equals(userID.get(currentPosition))){
                deleteButton.setBackground(deleteButtonImg);
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        databaseReference.child("Forum").child("posts").child(postID.get(pos)).setValue(null);
                    }
                });
            } else{
                deleteButton.setVisibility(View.GONE);
            }

            postView.setOnClickListener(this);
            currentPosition++;
        }

        /**
         * On Click for recycler view.
         *
         * @param view a view.
         */
        @Override
        public void onClick(View view) {
            itemListener.recyclerViewListClicked(view,this.getAdapterPosition());

        }
    }
}