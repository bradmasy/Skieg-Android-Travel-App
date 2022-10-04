package skieg.travel.planner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import skieg.travel.EventsActivity;
import skieg.travel.R;

public class PlannerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner);
    }

    public void clickPlannerBudgetButton(View view) {
        Intent intent = new Intent(this, EventsActivity.class);
        startActivity(intent);
    }

    public void clickPlannerChecklistButton(View view) {
        Intent intent = new Intent(this, EventsActivity.class);
        startActivity(intent);
    }

    public void clickPlannerItineraryButton(View view) {
        Intent intent = new Intent(this, EventsActivity.class);
        startActivity(intent);
    }

    public void clickPlannerJournalButton(View view) {
        Intent intent = new Intent(this, EventsActivity.class);
        startActivity(intent);
    }
}
