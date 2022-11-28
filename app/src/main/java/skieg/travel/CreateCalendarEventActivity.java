package skieg.travel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import skieg.travel.Utility.InputValidation;

public class CreateCalendarEventActivity extends AppCompatActivity {

    // Store references to the firebase database
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    // Button for creating a calendar event
    Button createEventBtn;

    // EditText objects for getting
    EditText titleInput;
    EditText descriptionInput;
    EditText locationInput;
    EditText dateInput;

    /**
     * When create calendar event activity page is created.
     * @param savedInstanceState: bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_calendar_event);

        // Add a click listener for creating a calendar event
        createEventBtn = findViewById(R.id.createEventBtn);
        createEventBtn.setOnClickListener(view -> {
            // Get the objects on the XML layout
            titleInput = findViewById(R.id.title);
            descriptionInput = findViewById(R.id.description);
            locationInput = findViewById(R.id.location);
            dateInput = findViewById(R.id.date);

            // Get the values from the EditText fields
            String title = titleInput.getText().toString();
            String description = descriptionInput.getText().toString();
            String location = locationInput.getText().toString();
            String date = dateInput.getText().toString();

            // Make sure all input fields are filled
            if (InputValidation.invalidStringInput(title) || InputValidation.invalidStringInput(description)
                    || InputValidation.invalidStringInput(location) || InputValidation.invalidStringInput(date)) {
                Toast toast = Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_LONG);
                toast.show();

                // MAke sure date field is valid
            } else if (InputValidation.invalidDateInput(date)) {
                Toast toast = Toast.makeText(getApplicationContext(), "Date must have the format: YYYY-MM-DD and be valid", Toast.LENGTH_LONG);
                toast.show();

            } else {
                // All data is valid so the new calendar event can be added to the firebase
                writeEventToFirebase(title, description, location, date);
            }
        });
    }


    /**
     * Creates a calendar event and writes it to the database.
     * @param title: String
     * @param description: String
     * @param location: String
     * @param date: String
     */
    public void writeEventToFirebase(String title, String description, String location, String date) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance("https://skieg-364814-default-rtdb.firebaseio.com/").getReference();

        String id = databaseReference.push().getKey();
        CalendarEvent calendarEvent = new CalendarEvent(id, title, description, location, date, MainActivity.USER.getId());

        Task setValueTask = databaseReference.child("CalendarEvent").child(id).setValue(calendarEvent);

        setValueTask.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast toast = Toast.makeText(getApplicationContext(), "Event added!", Toast.LENGTH_SHORT);
                toast.show();

                titleInput.setText("");
                descriptionInput.setText("");
                locationInput.setText("");
                dateInput.setText("");
            }
        });
    }


    /**
     * When back button is clicked, redirect to calendar activity.
     * @param view: View
     */
    public void backBtnClicked(View view) {
        Intent mainIntent = new Intent(this, CalendarActivity.class);
        startActivity(mainIntent);
    }
}