package skieg.travel.post;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import skieg.travel.MainActivity;
import skieg.travel.R;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

/**
 * Post page activity.
 */
public class PostPage extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private EditText infoBlock;
    private Button postButton;
    private String currentCountry;

    /**
     * On create method.
     *
     * @param savedInstanceState a bundle of saved instance data from the previous activity.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_page_activity);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance("https://skieg-364814-default-rtdb.firebaseio.com/").getReference();
        postButton = findViewById(R.id.post);
        Intent intent = getIntent();
        currentCountry = intent.getStringExtra("country");
        postButton.setOnClickListener(new View.OnClickListener() {

            /**
             * On click method set for post button.
             *
             * @param view a view.
             */
            @Override
            public void onClick(View view) {
                post(view);
            }
        });
    }


    /**
     * To go back to the main activity.
     *
     * @param view a view.
     */
    public void back(View view){
        Intent intent = new Intent(PostPage.this,MainActivity.class);
        startActivity(intent);
    }


    /**
     * Posts the data to the database.
     *
     * @param view a view.
     * @param post a post object we are writing to the database.
     */
    private void postToDatabase(View view, Post post){

         Task setValueTask = databaseReference.child("Forum").child("posts").child(post.getPostID()).setValue(post);

         setValueTask.addOnSuccessListener(new OnSuccessListener(){

             /**
              * On a successful write.
              *
              * @param o an object.
              */
             @Override
             public void onSuccess(Object o) {
                 infoBlock.setText(""); // clear the text.
             }
         });
    }

    /**
     * Creates a post and posts it to the database.
     *
     * @param view a view.
     */
    public void post(View view){
        String postID = databaseReference.push().getKey();
        infoBlock  = findViewById(R.id.postContent);
        String info = infoBlock.getText().toString();
        String date = CurrentDateTime.date();
        Post post = new Post(MainActivity.USER, date, info,postID, currentCountry);
        postToDatabase(view,post);
    }

    /**
     * Gets the current date and time.
     */
    private static class CurrentDateTime{
        public static String date() {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
            return dtf.format(LocalDateTime.now());
        }
    }
}
