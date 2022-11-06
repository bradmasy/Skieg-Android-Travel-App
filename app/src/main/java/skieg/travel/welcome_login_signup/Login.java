package skieg.travel.welcome_login_signup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import skieg.travel.MainActivity;
import skieg.travel.R;

public class Login extends AppCompatActivity {

    private FirebaseAuth authentication;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private DataSnapshot snapshot;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        authentication = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();



        Button backButton = findViewById(R.id.backButton);
        Button loginButton = findViewById(R.id.loginBtn);

        backButton.setOnClickListener(this::backBtnClicked);
        loginButton.setOnClickListener(this::logUserIn);
    }

    private void logUserIn(View view) {

        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.userPassword);

        String usernameString = username.getText().toString();
        String passwordString = password.getText().toString();



        if (usernameString.isEmpty() || passwordString.isEmpty()) {
            Toast.makeText(this, "Please make sure to fill in your email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        snapshot = databaseReference.get().getResult();
        Log.d("Snap: ", snapshot.toString());
//        databaseReference.child("Users").get().onSuccessTask(task->{

//            if(task.exists()){
//                Log.d("Task: ", task.toString());
//            }
//            return null;
//        });

//        Log.d("Users: ", databaseReference.child("Users").toString());


//    {
//                task ->
//                if (task.isSuccessful) {
//                    updateFirebaseUserDisplayName()
//                } else {
//                    Toast.makeText(this, "An error has occurred during login. Please try again later.", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//    }
    }


    @Override
    public void onStart(){
        super.onStart();

        FirebaseUser user = authentication.getCurrentUser();

        if(user != null){
            user.reload();
        }
    }



    public void backBtnClicked(View view) {
        Intent mainIntent = new Intent(this, Welcome.class);
        startActivity(mainIntent);
    }



}
