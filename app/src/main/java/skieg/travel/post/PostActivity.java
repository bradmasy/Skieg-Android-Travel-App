package skieg.travel.post;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import skieg.travel.DatabaseParse;
import skieg.travel.R;


public class PostActivity extends AppCompatActivity {

    PostAdapter PDAdapter;
    PostFragment postFragment;
    DatabaseReference db;
    ArrayList<String> names= new ArrayList<>();
    ArrayList<String> posts= new ArrayList<>();
    ArrayList<String> dates = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        Button postButton = findViewById(R.id.makePost);
        LinearLayout userInfo = findViewById(R.id.userInfo);
        LinearLayout userImageContainer = findViewById(R.id.userPhoto);
        Button filler = findViewById(R.id.filler);
        // setting colors

        postButton.setBackgroundColor(Color.parseColor("#BEDEFC"));
        userInfo.setBackgroundColor(Color.parseColor("#BEDEFC"));
        userImageContainer.setBackgroundColor(Color.parseColor("#BEDEFC"));
        filler.setBackgroundColor(Color.parseColor("#BEDEFC"));

        postFragment = new PostFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.recyclerPosts ,postFragment);
        fragmentTransaction.commit();
        getDataFromFirebase();
    }

    private void getDataFromFirebase() {
        // all the posts in line 1
        db = FirebaseDatabase.getInstance("https://skieg-364814-default-rtdb.firebaseio.com/").getReference().child("Forum").child("posts");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("LOG", "IN DATA SNAPSHOT METHOD");

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Log.d("Data Snap: ", dataSnapshot.getValue().toString());

                    String currSnapshot = String.valueOf(dataSnapshot.getValue());
                    String[] dataValues = currSnapshot.split(",");
                    String date = DatabaseParse.parseDataValue(dataValues[0]);
                    Log.d("DATE:", date);
                    String content = DatabaseParse.parseDataValue(dataValues[1]);
                    String id = DatabaseParse.parseDataValue(dataValues[2]);
                    String name = DatabaseParse.parseLastDataValue(dataValues[3]);
                    Log.d("Content:", content);
                    names.add(name);
                    dates.add(date);
                    posts.add(content);
                }

                PDAdapter = new PostAdapter(names, posts, dates);
                postFragment.initializeAdapter(PDAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("LOG", "DB ERROR");
            }
        });
    }

    public void makePost(View view){
        Intent intent = new Intent(PostActivity.this, PostPage.class);
        startActivity(intent);
    }
}