package skieg.travel;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;


import skieg.travel.forum.ForumBoardFragment;

public class PostActivity extends AppCompatActivity {

    String[] posts;
    final public static int MARGIN_BOTTOM = 5;
    final public static int MARGIN_TOP = 5;
    final public static int MARGIN_LEFT = 5;
    final public static int MARGIN_RIGHT = 5;

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


        Fragment forum = new ForumFragment();
        //FragmentManager fragmentManager = getSupportFragmentManager();


//        LinearLayout postScroll = findViewById(R.id.postScroll);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT);
//        posts = getResources().getStringArray(R.array.postTest); // should be dynamic from database wen connected
//
//        for(int i = 0; i < posts.length; i++){
//            TextView post = new TextView(this);
//            post.setBackgroundColor(Color.parseColor("#BEDEFC"));
//            post.setText(posts[i]);
//
//            post.setLayoutParams(params);
//
//            params.setMargins(MARGIN_LEFT, MARGIN_TOP, MARGIN_RIGHT, MARGIN_BOTTOM);
//            postScroll.addView(post);
//
//        }

    }




}