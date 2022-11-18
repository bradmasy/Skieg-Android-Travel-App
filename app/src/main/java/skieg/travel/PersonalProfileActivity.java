package skieg.travel;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;
import skieg.travel.user.User;

public class PersonalProfileActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    EditText firstNameInput;
    EditText lastNameInput;
    EditText usernameInput;
    EditText emailInput;
    EditText passwordInput;
    EditText cityInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_profile);

        firstNameInput = findViewById(R.id.firstName);
        lastNameInput = findViewById(R.id.lastName);
        usernameInput = findViewById(R.id.username);
        emailInput = findViewById(R.id.userEmail);
        passwordInput = findViewById(R.id.userPassword);
        cityInput = findViewById(R.id.city);

        fillUserInfo();

        Button saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(view -> {
            saveProfile();
        });

        Button backBtn = findViewById(R.id.backButton);
        backBtn.setOnClickListener(view -> {
            backBtnClicked();
        });
    }


    private void fillUserInfo() {
        User currentUser = MainActivity.USER;
        firstNameInput.setText(currentUser.getFirstName());
        lastNameInput.setText(currentUser.getLastName());
        usernameInput.setText(currentUser.getUsername());
        emailInput.setText(currentUser.getEmail());
        passwordInput.setText(currentUser.getPassword());
        cityInput.setText(currentUser.getCity());
    }


    public void saveProfile() {
        String firstName = firstNameInput.getText().toString();
        String lastName = lastNameInput.getText().toString();
        String username = usernameInput.getText().toString();
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        String city = cityInput.getText().toString();

        if (InputValidation.invalidStringInput(firstName) || InputValidation.invalidStringInput(lastName) || InputValidation.invalidStringInput(username)
                || InputValidation.invalidStringInput(email) || InputValidation.invalidStringInput(password) || InputValidation.invalidStringInput(city)) {
            Toast.makeText(PersonalProfileActivity.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "https://api.openweathermap.org/data/2.5/weather";
        String appid = "fa211ad253385ab5e5f303af6dfebb44";
        String tempUrl = url + "?q=" + city + "&appid=" + appid;

        PersonalProfileActivity.AsyncTaskRunner runner = new PersonalProfileActivity.AsyncTaskRunner();
        runner.execute(tempUrl);
    }


    private void saveUserInfo(String firstName, String lastName, String city, String username, String email, String password) {
        MainActivity.USER.setFirstName(firstName);
        MainActivity.USER.setLastName(lastName);
        MainActivity.USER.setCity(city);

        System.out.println("CITY: " + MainActivity.USER.getCity());
        MainActivity.USER.setUsername(username);
        MainActivity.USER.setEmail(email);
        MainActivity.USER.setPassword(password);
    }


    public void backBtnClicked() {
        Intent mainIntent = new Intent(PersonalProfileActivity.this, MainActivity.class);
        startActivity(mainIntent);
    }


    class AsyncTaskRunner extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            RequestQueue queue = Volley.newRequestQueue(PersonalProfileActivity.this);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, strings[0], null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        response.getJSONObject("main");

                        String userID = MainActivity.USER.getId();
                        firebaseDatabase = FirebaseDatabase.getInstance();
                        databaseReference = FirebaseDatabase.getInstance("https://skieg-364814-default-rtdb.firebaseio.com/").getReference().child("Users").child(userID);

                        String firstName = firstNameInput.getText().toString();
                        String lastName = lastNameInput.getText().toString();
                        String username = usernameInput.getText().toString();
                        String email = emailInput.getText().toString();
                        String password = passwordInput.getText().toString();
                        String city = cityInput.getText().toString();

                        User updatedUser = new User(userID, firstName, lastName, city, username, email, password);
                        databaseReference.setValue(updatedUser);

                        Toast.makeText(PersonalProfileActivity.this, "Profile updated!", Toast.LENGTH_SHORT).show();

                        saveUserInfo(firstName, lastName, city, username, email, password);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(PersonalProfileActivity.this, "Invalid city entered.", Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(PersonalProfileActivity.this, "Invalid city entered.", Toast.LENGTH_SHORT).show();
                }
            });
            queue.add(request);
            return null;
        }
    }
}
