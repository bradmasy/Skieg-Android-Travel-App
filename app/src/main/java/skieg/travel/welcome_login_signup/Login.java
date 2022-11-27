package skieg.travel.welcome_login_signup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import skieg.travel.MainActivity;
import skieg.travel.R;

public class Login extends AppCompatActivity {

    // Stores firebase database values
    FirebaseDatabase database;
    private DatabaseReference databaseReference;

    // Stores x and y coordinates of the cursor
    float y1;
    float y2;

    /**
     * When Login page is created.
     * @param savedInstanceState: Bundle
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Get access to the database
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        // Store buttons on the page
        Button backButton = findViewById(R.id.backButton);
        Button loginButton = findViewById(R.id.loginBtn);

        // When the buttons are clicked, redirect to the appropriate pages
        backButton.setOnClickListener(this::backBtnClicked);
        loginButton.setOnClickListener(this::logUserIn);
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

    private void logUserIn(View view) {
        // Get the username and password values from the text fields
        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.userPassword);
        String usernameString = username.getText().toString();
        String passwordString = password.getText().toString();

        // Check for invalid input
        if (usernameString.isEmpty() || passwordString.isEmpty()) {
            Toast.makeText(this, "Please make sure to fill in your username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Add a value event listener to loop through all users in the database
        databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            // Read a snapshot of the contents at a database collection
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean match = false;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Get each user's information from their profile collection
                    DataSnapshot profile = snapshot.child("Profile");
                    String profileString = String.valueOf(profile.getValue());
                    System.out.println("CHILD: " + profileString);

                    try {
                        JSONObject object = new JSONObject(profileString);
                        String currentUsername = String.valueOf(object.get("username"));
                        String currentPassword = String.valueOf(object.get("password"));

                        Log.d("trying to log in: ", username.getText().toString());

                        // Check if username and password match the user's input
                        if (username.getText().toString().equals(currentUsername) && password.getText().toString().equals(currentPassword)) {
                            match = true;
                            Toast.makeText(Login.this, "Access granted", Toast.LENGTH_LONG).show();

                            // Get remaining user information from the database
                            String currentID = String.valueOf(object.get("id"));
                            String currentFirstName = String.valueOf(object.get("firstName"));
                            String currentLastName = String.valueOf(object.get("lastName"));
                            String currentCity = String.valueOf(object.get("city"));
                            String currentEmail = String.valueOf(object.get("email"));

                            // Redirect user to main page
                            redirectMainPage(currentID, currentFirstName, currentLastName, currentCity, currentUsername, currentEmail, currentPassword);
                        }

                    } catch (JSONException e) {
                        e.getMessage();
                    }

                }
                if (!match) {
                    Toast.makeText(Login.this, "Access denied", Toast.LENGTH_LONG).show();
                }
            }

            // called when the client doesn't have permission to access the data
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
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
     * Redirects to the main page when the login button is clicked.
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
        Intent intent = new Intent(Login.this, MainActivity.class);

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

}
