package skieg.travel.welcome_login_signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import skieg.travel.R;
import skieg.travel.activity_splashscreen;

public class Welcome extends AppCompatActivity {

    // Stores x and y coordinates of the cursor
    float y1;
    float y2;

    /**
     * When Welcome page is created.
     * @param savedInstanceState: Bundle
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_signup);

        Button loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this::redirectToLogin);

        Button signupBtn = findViewById(R.id.signupBtn);
        signupBtn.setOnClickListener(this::redirectToSignup);
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
                    Intent intent = new Intent(this, activity_splashscreen.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.animation_slideout_reverse,R.anim.animation_slidein_reverse);
                }
                break;

        }
        // Does not fit any above requirements.
        return false;
    }


    /**
     * Redirects to the login page.
     * @param view: View
     */
    public void redirectToLogin(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        overridePendingTransition(R.anim.animation_slidein,R.anim.animation_slideout);
    }

    /**
     * Redirects to the signup page.
     * @param view: View
     */
    public void redirectToSignup(View view) {
        Intent intent = new Intent(this, Signup.class);
        startActivity(intent);
        overridePendingTransition(R.anim.animation_slidein,R.anim.animation_slideout);
    }
}
