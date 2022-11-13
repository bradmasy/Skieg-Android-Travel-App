package skieg.travel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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

public class CalendarEventActivity extends AppCompatActivity {
    CalendarFragment calendarFragment;
    String selectedDate;

    ArrayList<String> titles = new ArrayList<>();
    ArrayList<String> descriptions = new ArrayList<>();
    ArrayList<String> locations = new ArrayList<>();
    ArrayList<String> dates = new ArrayList<>();

    RecyclerViewCalendar recyclerViewCalendar;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_events);

        // Get selected date from other calendar activity
        Bundle bundle = getIntent().getExtras();
        selectedDate = bundle.getString("selectedDate");
        Log.d("LOG", selectedDate);

        TextView textViewEventsDay = findViewById(R.id.textViewEventsDay);
        String eventsDayText = "Calendar events for " + selectedDate;
        textViewEventsDay.setText(eventsDayText);


        // Create initial blank fragment
        calendarFragment = new CalendarFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, calendarFragment);
        fragmentTransaction.commit();


        // Retrieve data from database and display in the frame layout
        getDataFromFirebase();
    }

    private void getDataFromFirebase() {
        databaseReference = FirebaseDatabase.getInstance("https://skieg-364814-default-rtdb.firebaseio.com/").getReference().child("CalendarEvent");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("LOG", "IN DATA SNAPSHOT METHOD");

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String currSnapshot = String.valueOf(dataSnapshot.getValue());
                    String[] dataValues = currSnapshot.split(",");

                    String date = DatabaseParse.parseDataValue(dataValues[0]);
                    Log.d("DATE:", date);
                    String description = DatabaseParse.parseDataValue(dataValues[1]);
                    Log.d("DESCRIBE:", description);
                    String location = DatabaseParse.parseDataValue(dataValues[2]);
                    Log.d("LOCATION:", location);
                    String title = DatabaseParse.parseLastDataValue(dataValues[3]);
                    Log.d("TITLE:", title);

                    // Only display an event if the date matches the date selected by the user
                    if (selectedDate.equals(date)) {
                        titles.add(title);
                        descriptions.add(description);
                        locations.add(location);
                        dates.add(date);
                    }

                    Log.d("DATASNAP:", currSnapshot);
                }

                recyclerViewCalendar = new RecyclerViewCalendar(titles, descriptions, locations, dates);
                calendarFragment.initializeAdapter(recyclerViewCalendar);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("LOG", "DB ERROR");
            }
        });
    }


    public void backBtnClicked(View view) {
        Intent mainIntent = new Intent(this, CalendarActivity.class);
        startActivity(mainIntent);
    }
}
