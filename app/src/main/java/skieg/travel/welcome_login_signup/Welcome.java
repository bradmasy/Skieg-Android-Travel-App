package skieg.travel.welcome_login_signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import skieg.travel.R;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_signup);

        Button loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this::redirectToLogin);

        Button signupBtn = findViewById(R.id.signupBtn);
        signupBtn.setOnClickListener(this::redirectToSignup);
    }



    public void redirectToLogin(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

        public void redirectToSignup(View view) {
        Intent intent = new Intent(this, Signup.class);
        startActivity(intent);
    }
}
