package skieg.travel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
<<<<<<< HEAD
import androidx.fragment.app.FragmentActivity;
=======
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
>>>>>>> master

// consider this main activity for events
public class EventsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);


<<<<<<< HEAD

=======
        EventFragment eventFragment = new EventFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.eventsMain, eventFragment);
        fragmentTransaction.commit();
>>>>>>> master
    }

<<<<<<< HEAD

=======
    public void clickMainButton(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
>>>>>>> 851dcd6b97534359230fb3bf2d87256e635d3483


}