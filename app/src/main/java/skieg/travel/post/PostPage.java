package skieg.travel.post;

import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import skieg.travel.R;
import skieg.travel.user.User;

public class PostPage extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_page_activity);
        user = (User) savedInstanceState.get("user"); // pass the user with a bundle


    }


    private void postToDatabase(Post post){
         DatabaseReference databaseReference;
         FirebaseDatabase firebaseDatabase;

         firebaseDatabase = FirebaseDatabase.getInstance();
         databaseReference = firebaseDatabase.getReference();
         String id = user.getId();

         Task setValueTask = databaseReference.child("Users").child(id).child("Posts").setValue(this);

    }

    public void post(){

        Post post = new Post(user);
        EditText infoBlock = findViewById(R.id.postContent);
        String info = infoBlock.getText().toString();
        post.addInformation(info);
        postToDatabase(post);


    }


}
