package skieg.travel.welcome_login_signup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import skieg.travel.MainActivity;
import skieg.travel.R;

public class Signup extends AppCompatActivity {

    private FirebaseAuth authentication;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(this::backBtnClicked);
        authentication = FirebaseAuth.getInstance();

    }


    public void backBtnClicked(View view) {
        Intent mainIntent = new Intent(this, Welcome.class);
        startActivity(mainIntent);
    }


//    public void signUp(View view){
//
//
//        EditText firstNameInput = findViewById(R.id.firstName);
//        EditText lastNameInput = findViewById(R.id.lastName);
//        EditText usernameInput = findViewById(R.id.username);
//        EditText emailInput = findViewById(R.id.userEmail);
//        EditText passwordInput = findViewById(R.id.userPassword);
//
//        String firstName = firstNameInput.getText().toString();
//        String lastName = lastNameInput.getText().toString();
//        String username = usernameInput.getText().toString();
//        String email = emailInput.getText().toString();
//        String password = passwordInput.getText().toString();
//
//
//
//}
}

