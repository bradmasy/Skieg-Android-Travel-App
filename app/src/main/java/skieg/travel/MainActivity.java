package skieg.travel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.FirebaseApp;
//import com.google.firebase.auth.FirebaseAuth;

import skieg.travel.planner.PlannerActivity;
import skieg.travel.planner.fragments.MainPlanner;
import skieg.travel.user.User;

public class MainActivity extends AppCompatActivity {

   //private FirebaseAuth authentication;

    public static User USER;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//   authentication = FirebaseAuth.getInstance();
//        authentication = FirebaseAuth.getInstance();
//        USER = savedInstanceState.get("");

        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("id");
        String username = bundle.getString("username");
        String password = bundle.getString("password");

        USER = new User(id, username, password);
        Log.d("MAIN", USER.toString());
    }



    public void clickMainEventsButton(View view) {
       Intent intent = new Intent(this, EventsActivity.class);
       startActivity(intent);
    }

    public void clickMainPlannerButton(View view) {
        Intent intent = new Intent(this, MainPlanner.class);
        startActivity(intent);
    }

    public void clickMainCalendarButton(View view) {
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
    }

    public void clickMainPersonalProfileButton(View view) {
        Intent intent = new Intent(this, PersonalProfileActivity.class);
        startActivity(intent);
    }

    public void clickMainPostButton(View view) {
        Intent intent = new Intent(this, PostActivity.class);
        startActivity(intent);
    }

    public void clickMapsButton(View view){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

}