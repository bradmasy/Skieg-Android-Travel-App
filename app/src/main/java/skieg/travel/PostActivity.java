package skieg.travel;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PostActivity extends AppCompatActivity {

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






    }



}