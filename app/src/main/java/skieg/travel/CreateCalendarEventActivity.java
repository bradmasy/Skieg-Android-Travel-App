package skieg.travel;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import skieg.travel.Utility.InputValidation;
import skieg.travel.welcome_login_signup.Signup;

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
                // Check that the city entered is valid for the weather API
                String url = "https://api.openweathermap.org/data/2.5/weather";
                String appid = "fa211ad253385ab5e5f303af6dfebb44";
                String tempUrl = url + "?q=" + location + "&appid=" + appid;

                // Run an async task to validate the city input using the weather API
                CreateCalendarEventActivity.AsyncTaskRunner runner = new CreateCalendarEventActivity.AsyncTaskRunner();
                runner.execute(tempUrl);
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
     * Private Async Task class that is used when validating a user's city input.
     */
    class AsyncTaskRunner extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            RequestQueue queue = Volley.newRequestQueue(CreateCalendarEventActivity.this);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, strings[0], null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        // If there is a response with no error, the city passed to the URL is valid
                        response.getJSONObject("main");

                        // Get the values for all the text fields
                        String title = titleInput.getText().toString();
                        String description = descriptionInput.getText().toString();
                        String location = locationInput.getText().toString();
                        String date = dateInput.getText().toString();

                        // All data is valid so the new calendar event can be added to the firebase
                        writeEventToFirebase(title, description, location, date);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(CreateCalendarEventActivity.this, "Invalid city entered.", Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(CreateCalendarEventActivity.this, "Invalid city entered.", Toast.LENGTH_SHORT).show();
                }
            });
            queue.add(request);
            return null;
        }
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