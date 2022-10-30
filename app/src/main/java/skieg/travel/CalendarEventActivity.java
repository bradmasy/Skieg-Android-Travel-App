package skieg.travel;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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

    Button button;
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


        // Once you can access DB, commit all of button click listener and just call this line
//        getDataFromFirebase();

        button = findViewById(R.id.button);
        button.setOnClickListener(view -> {
            titles.add("title");
            descriptions.add("description");
            locations.add("location");
            dates.add("date");

            titles.add("title2");
            descriptions.add("description2");
            locations.add("location2");
            dates.add("date2");

            titles.add("title3");
            descriptions.add("description3");
            locations.add("location3");
            dates.add("date3");

            // Do not need these lines to open the page, but removing them won't display the dummy data above
            recyclerViewCalendar = new RecyclerViewCalendar(titles, descriptions, locations, dates);
            calendarFragment.initializeAdapter(recyclerViewCalendar);
        });

    }

    private void getDataFromFirebase() {
        Log.d("LOG", "IN FIREBASE METHOD");


        Log.d("LOG", "BEFORE DB REF");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        Log.d("LOG", "AFTER DB REF");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("LOG", "IN DATA SNAPSHOT METHOD");

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String currSnapshot = String.valueOf(dataSnapshot.getValue());
                    String[] dataValues = currSnapshot.split(",");

                    String date = parseDataValue(dataValues[0]);
                    Log.d("TEST1:", date);
                    String description = parseDataValue(dataValues[1]);
                    Log.d("TEST2:", description);
                    String location = parseDataValue(dataValues[2]);
                    Log.d("TEST3:", location);
                    String title = parseLastDataValue(dataValues[3]);
                    Log.d("TEST4:", title);

                    titles.add(title);
                    descriptions.add(description);
                    locations.add(location);
                    dates.add(date);

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


    private String parseDataValue(String value) {
        String[] data = value.split("=");
        return data[1];
    }

    private String parseLastDataValue(String value) {
        String[] data = value.split("=");
        // Remove last curly bracket
        return data[1].substring(0, data[1].length()-1);
    }
}
