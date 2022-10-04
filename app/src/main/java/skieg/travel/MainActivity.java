package skieg.travel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import skieg.travel.planner.PlannerActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickMainEventsButton(View view) {
       Intent intent = new Intent(this, EventsActivity.class);
       startActivity(intent);
    }

    public void clickMainPlannerButton(View view) {
        Intent intent = new Intent(this, PlannerActivity.class);
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
}