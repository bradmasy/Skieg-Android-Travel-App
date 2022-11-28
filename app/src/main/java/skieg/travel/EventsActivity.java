package skieg.travel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * Main activity for events.
 */
public class EventsActivity extends AppCompatActivity {

    /**
     * When events activity page is created.
     * @param savedInstanceState: bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        // Create event fragment to store events
        EventFragment eventFragment = new EventFragment();

        // Set up fragment to be displayed in a ListView
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.eventsMain, eventFragment);
        fragmentTransaction.commit();
    }

    /**
     * When back button is clicked, redirect to main activity page.
     * @param view: View
     */
    public void clickMainButton(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
