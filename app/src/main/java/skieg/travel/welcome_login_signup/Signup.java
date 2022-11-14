package skieg.travel.welcome_login_signup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import skieg.travel.MainActivity;
import skieg.travel.R;
import skieg.travel.activity_splashscreen;
import skieg.travel.user.User;

public class Signup extends AppCompatActivity {

//    private FirebaseAuth authentication;

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    float y1;
    float y2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(this::backBtnClicked);
//        authentication = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        Button signupButton = findViewById(R.id.signupBtn);
        signupButton.setOnClickListener(view -> {
            signUp();
        });

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


    public void backBtnClicked(View view) {
        Intent mainIntent = new Intent(this, Welcome.class);
        startActivity(mainIntent);
    }


    public void signUp(){

        EditText firstNameInput = findViewById(R.id.firstName);
        EditText lastNameInput = findViewById(R.id.lastName);
        EditText usernameInput = findViewById(R.id.username);
        EditText emailInput = findViewById(R.id.userEmail);
        EditText passwordInput = findViewById(R.id.userPassword);

        String firstName = firstNameInput.getText().toString();
        String lastName = lastNameInput.getText().toString();
        String username = usernameInput.getText().toString();
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);


        // create a task to set the value of the node as the new user
        Task setValueTask = databaseReference.child("Users").child(user.getId()).setValue(user);

        // add a success listener to the task
        setValueTask.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(Signup.this, "Account created", Toast.LENGTH_LONG).show();

                redirectMainPage(user.getId(), user.getUsername(), user.getPassword());
            }
        });
    }


    public void redirectMainPage(String id, String username, String password) {
        Intent intent = new Intent(Signup.this, MainActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("username", username);
        bundle.putString("password", password);
        intent.putExtras(bundle);

        startActivity(intent);
    }
}

