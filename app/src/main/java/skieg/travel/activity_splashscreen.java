package skieg.travel;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import skieg.travel.welcome_login_signup.Welcome;

public class activity_splashscreen extends AppCompatActivity {

    // Stores x and y coordinates of the cursor
    float y1;
    float y2;


    /**
     * When splash screen page is created.
     * @param savedInstanceState: Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

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
                if (y1 > y2){
                    // User Swiped Up
                    Intent intent = new Intent(this, Welcome.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.animation_slidein,R.anim.animation_slideout);
                }
                break;

        }
        // Does not fit any above requirements.
        return false;
    }

    /**
     * When the back button is clicked.
     */
    @Override
    public void onBackPressed() {
        // Override the default behaviour to disable going back from this page.
        // Do nothing...
    }
}