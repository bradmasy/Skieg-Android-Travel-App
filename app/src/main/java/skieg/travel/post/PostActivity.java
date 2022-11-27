package skieg.travel.post;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.ArrayList;

import skieg.travel.CalendarEventActivity;
import skieg.travel.DatabaseParse;
import skieg.travel.InputValidation;
import skieg.travel.MapsActivity;
import skieg.travel.PersonalProfileActivity;
import skieg.travel.R;


public class PostActivity extends AppCompatActivity {

    PostAdapter PDAdapter;
    PostFragment postFragment;
    DatabaseReference db;
    ArrayList<String> names= new ArrayList<>();
    ArrayList<String> posts= new ArrayList<>();
    ArrayList<String> dates = new ArrayList<>();
    ArrayList<String> ids = new ArrayList<>();
    ArrayList<String> postIds = new ArrayList<>();

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




        Button profileBtn = findViewById(R.id.profileBtn);
        profileBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, PersonalProfileActivity.class);
            startActivity(intent);
        });

        Button eventsBtn = findViewById(R.id.eventsBtn);
        eventsBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, CalendarEventActivity.class);

            // Set default date to today
            LocalDate currentDate = LocalDate.now();
            String selectedDate = currentDate.getYear() + "-" + InputValidation.makeValidDateValue(currentDate.getMonthValue()) + "-" + InputValidation.makeValidDateValue(currentDate.getDayOfMonth());

            System.out.println("DATE: " + selectedDate);

            Bundle bundle = new Bundle();
            bundle.putString("selectedDate", selectedDate);
            intent.putExtras(bundle);

            startActivity(intent);
        });
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

                    String postID = DatabaseParse.parseDataValue(dataValues[2]);

                    String userID = DatabaseParse.parseDataValue(dataValues[3]);

                    String username = DatabaseParse.parseLastDataValue(dataValues[4]);

                    Log.d("Content:", content);

                    names.add(username);
                    dates.add(date);
                    posts.add(content);
                    ids.add(userID);
                    postIds.add(postID);
                }

                PDAdapter = new PostAdapter(names, posts, dates,ids,postIds);
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