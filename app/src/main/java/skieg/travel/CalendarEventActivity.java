package skieg.travel;

import android.content.Intent;
import android.os.Bundle;
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

import skieg.travel.Utility.DatabaseParse;

public class CalendarEventActivity extends AppCompatActivity {
    // Store reference to a CalendarFragment
    CalendarFragment calendarFragment;
    String selectedDate;

    // ArrayLists to store a list of values from the database for each calendar event attribute
    ArrayList<String> titles = new ArrayList<>();
    ArrayList<String> descriptions = new ArrayList<>();
    ArrayList<String> locations = new ArrayList<>();
    ArrayList<String> dates = new ArrayList<>();

    // Store reference to a RecyclerViewCalendar
    RecyclerViewCalendar recyclerViewCalendar;

    // Store reference to the firebase database
    DatabaseReference databaseReference;


    /**
     * When calendar event activity page is created.
     * @param savedInstanceState: Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_events);

        // Get selected date from other calendar activity
        Bundle bundle = getIntent().getExtras();
        selectedDate = bundle.getString("selectedDate");

        // Display a title listing the events for the date selected in the previous activity
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


    /**
     * Private method to fetch data from the firebase database.
     */
    private void getDataFromFirebase() {
        databaseReference = FirebaseDatabase.getInstance("https://skieg-364814-default-rtdb.firebaseio.com/").getReference().child("CalendarEvent");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Loop through each value in the CalendarEvent collection of the database
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    // Get the current CalendarEvent object
                    String currSnapshot = String.valueOf(dataSnapshot.getValue());
                    String[] dataValues = currSnapshot.split(",");

                    // Parse the individual values of that CalendarEvent
                    String date = DatabaseParse.parseDataValue(dataValues[0]);
                    String description = DatabaseParse.parseDataValue(dataValues[2]);
                    String location = DatabaseParse.parseDataValue(dataValues[3]);
                    String title = DatabaseParse.parseDataValue(dataValues[4]);

                    // Only display an event if the date matches the date selected by the user
                    if (selectedDate.equals(date)) {
                        // Add the values to the ArrayLists
                        titles.add(title);
                        descriptions.add(description);
                        locations.add(location);
                        dates.add(date);
                    }
                }

                // Stores values added to the ArrayLists into the recycler view
                recyclerViewCalendar = new RecyclerViewCalendar(titles, descriptions, locations, dates);
                // Set the fragment adapter to the recycler view
                calendarFragment.initializeAdapter(recyclerViewCalendar);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


    /**
     * Redirects to the calendar activity page.
     * @param view: View
     */
    public void backBtnClicked(View view) {
        Intent mainIntent = new Intent(this, CalendarActivity.class);
        startActivity(mainIntent);
    }
}
