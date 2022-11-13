package skieg.travel.welcome_login_signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import skieg.travel.R;
import skieg.travel.activity_splashscreen;

public class Welcome extends AppCompatActivity {

    float y1;
    float y2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_signup);

        Button loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this::redirectToLogin);

        Button signupBtn = findViewById(R.id.signupBtn);
        signupBtn.setOnClickListener(this::redirectToSignup);

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
                    Intent intent = new Intent(this, activity_splashscreen.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.animation_slideout_reverse,R.anim.animation_slidein_reverse);
                }
                break;

        }
        // Does not fit any above requirements.
        return false;
    }


    public void redirectToLogin(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        overridePendingTransition(R.anim.animation_slidein,R.anim.animation_slideout);
    }

        public void redirectToSignup(View view) {
        Intent intent = new Intent(this, Signup.class);
        startActivity(intent);
        overridePendingTransition(R.anim.animation_slidein,R.anim.animation_slideout);
    }
}
