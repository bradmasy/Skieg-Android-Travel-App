package skieg.travel.welcome_login_signup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import skieg.travel.user.User;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import skieg.travel.MainActivity;
import skieg.travel.R;

public class Login extends AppCompatActivity {

    FirebaseDatabase database;
    private DatabaseReference databaseReference;

    float y1;
    float y2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();


        Button backButton = findViewById(R.id.backButton);
        Button loginButton = findViewById(R.id.loginBtn);

        backButton.setOnClickListener(this::backBtnClicked);
        loginButton.setOnClickListener(this::logUserIn);
    }

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

        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.userPassword);

        String usernameString = username.getText().toString();
        String passwordString = password.getText().toString();


        if (usernameString.isEmpty() || passwordString.isEmpty()) {
            Toast.makeText(this, "Please make sure to fill in your username and password", Toast.LENGTH_SHORT).show();
            return;
        }


        // get the reference to the JSON tree

        // add a value event listener to the Users node
        databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            // called to read a static snapshot of the contents at a given path
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean match = false;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    Log.d("User: ", user.toString());
                    Log.d("trying to log in: ", username.getText().toString());
                    if (username.getText().toString().equals(user.getUsername()) && password.getText().toString().equals(user.getPassword())) {
                        match = true;
                        Toast.makeText(Login.this, "Access granted", Toast.LENGTH_LONG).show();

                        redirectMainPage(user.getId(), user.getFirstName(), user.getLastName(), user.getCity(), user.getUsername(), user.getEmail(), user.getPassword());
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


    public void backBtnClicked(View view) {
        Intent mainIntent = new Intent(this, Welcome.class);
        startActivity(mainIntent);
    }


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
