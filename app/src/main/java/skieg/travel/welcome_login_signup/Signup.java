package skieg.travel.welcome_login_signup;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
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

import skieg.travel.InputValidation;
import skieg.travel.MainActivity;
import skieg.travel.R;
import skieg.travel.user.User;

public class Signup extends AppCompatActivity {

    // Stores firebase database values
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    // Store text fields for user's input
    EditText firstNameInput;
    EditText lastNameInput;
    EditText usernameInput;
    EditText emailInput;
    EditText passwordInput;
    EditText cityInput;

    // Stores x and y coordinates of the cursor
    float y1;
    float y2;

    /**
     * When Signup page is created.
     * @param savedInstanceState: Bundle
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Add click listener for back button to redirect to the welcome page
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(this::backBtnClicked);

        // Get access to the database
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        Button signupButton = findViewById(R.id.signupBtn);
        signupButton.setOnClickListener(view -> {
            signUp();
        });

    }

    /**
     * Redirects to the appropriate page when the user swipes in a certain direction (up or down).
     * @param event: MotionEvent
     * @return boolean: whether or not a valid switch case was chosen.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                y1 = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                y2 = event.getY();
                if (y1 < y2){
                    // User Swiped Down
                    Intent intent = new Intent(this, Welcome.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.animation_slideout_reverse,R.anim.animation_slidein_reverse);
                }
                break;

        }
        // Does not fit any above requirements.
        return false;
    }


    /**
     * Redirects to the welcome page when the back button is clicked.
     * @param view: View
     */
    public void backBtnClicked(View view) {
        Intent mainIntent = new Intent(this, Welcome.class);
        startActivity(mainIntent);
    }


    /**
     * Gets the values from the text fields and checks are they are valid (not empty) before
     * allowing the user to sign up.
     */
    public void signUp(){
        // Get all the EditText objects
        firstNameInput = findViewById(R.id.firstName);
        lastNameInput = findViewById(R.id.lastName);
        usernameInput = findViewById(R.id.username);
        emailInput = findViewById(R.id.userEmail);
        passwordInput = findViewById(R.id.userPassword);
        cityInput = findViewById(R.id.city);

        // Store the values entered in the text fields
        String firstName = firstNameInput.getText().toString();
        String lastName = lastNameInput.getText().toString();
        String username = usernameInput.getText().toString();
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        String city = cityInput.getText().toString();

        // Check if any fields have invalid input
        if (InputValidation.invalidStringInput(firstName) || InputValidation.invalidStringInput(lastName) || InputValidation.invalidStringInput(username)
            || InputValidation.invalidStringInput(email) || InputValidation.invalidStringInput(password) || InputValidation.invalidStringInput(city)) {
            Toast.makeText(Signup.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }


        // Check that the city entered is valid for the weather API
        String url = "https://api.openweathermap.org/data/2.5/weather";
        String appid = "fa211ad253385ab5e5f303af6dfebb44";
        String tempUrl = url + "?q=" + city + "&appid=" + appid;

        // Run an async task to validate the city input using the weather API
        Signup.AsyncTaskRunner runner = new Signup.AsyncTaskRunner();
        runner.execute(tempUrl);
    }


    /**
     * Redirects to the main page when the signup button is clicked and the input is valid.
     * Sends a bundle of a user's information to the main page.
     * @param id: string
     * @param firstName: string
     * @param lastName: string
     * @param city: string
     * @param username: string
     * @param email: string
     * @param password: string
     */
    public void redirectMainPage(String id, String firstName, String lastName, String city, String username, String email, String password) {
        Intent intent = new Intent(Signup.this, MainActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("username", username);
        bundle.putString("password", password);
        bundle.putString("firstName", firstName);
        bundle.putString("lastName", lastName);
        bundle.putString("city", city);
        bundle.putString("email", email);
        intent.putExtras(bundle);

        startActivity(intent);
    }


    /**
     * Writes a user's information to the firebase database.
     * @param firstName: string
     * @param lastName: string
     * @param city: string
     * @param username: string
     * @param email: string
     * @param password: string
     */
    public void addUserToDatabase(String firstName, String lastName, String city, String username, String email, String password) {
        User user = new User(databaseReference.push().getKey(), firstName, lastName, city, username, email, password);

        // create a task to set the value of the node as the new user
        Task setValueTask = databaseReference.child("Users").child(user.getId()).child("Profile").setValue(user);

        // add a success listener to the task
        setValueTask.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(Signup.this, "Account created", Toast.LENGTH_LONG).show();

                // Pass the user's information to the main page
                redirectMainPage(user.getId(), user.getFirstName(), user.getLastName(), user.getCity(), user.getUsername(), user.getEmail(), user.getPassword());
            }
        });
    }


    /**
     * Private Async Task class that is used when validating a user's city input.
     */
    class AsyncTaskRunner extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            RequestQueue queue = Volley.newRequestQueue(Signup.this);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, strings[0], null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        // If there is a response with no error, the city passed to the URL is valid
                        response.getJSONObject("main");

                        // Get user's input from the text fields
                        String firstName = firstNameInput.getText().toString();
                        String lastName = lastNameInput.getText().toString();
                        String username = usernameInput.getText().toString();
                        String email = emailInput.getText().toString();
                        String password = passwordInput.getText().toString();
                        String city = cityInput.getText().toString();

                        // Add them to the database
                        addUserToDatabase(firstName, lastName, city, username, email, password);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(Signup.this, "Invalid city entered.", Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Signup.this, "Invalid city entered.", Toast.LENGTH_SHORT).show();
                }
            });
            queue.add(request);
            return null;
        }
    }
}

