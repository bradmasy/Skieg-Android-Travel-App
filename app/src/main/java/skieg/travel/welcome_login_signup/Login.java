package skieg.travel.welcome_login_signup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import skieg.travel.MainActivity;
import skieg.travel.R;

public class Login extends AppCompatActivity {

    private FirebaseAuth authentication;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        authentication = FirebaseAuth.getInstance();



        Button backButton = findViewById(R.id.backButton);
        Button loginButton = findViewById(R.id.loginBtn);

        backButton.setOnClickListener(this::backBtnClicked);
        loginButton.setOnClickListener(this::logUserIn);
    }

    private void logUserIn(View view) {


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
