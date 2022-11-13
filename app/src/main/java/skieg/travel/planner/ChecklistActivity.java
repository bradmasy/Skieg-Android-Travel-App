package skieg.travel.planner;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import skieg.travel.R;
import skieg.travel.planner.fragments.ChecklistFragment;

public class ChecklistActivity extends AppCompatActivity {

    EditText checklistEditText;

    ChecklistFragment checklistFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);

        System.out.println("CHECKLIST PAGE LOADED");
        Log.d("CHECK", "CHECKLIST PAGE LOADED");

        // Create initial blank fragment
        checklistFragment = new ChecklistFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, checklistFragment);
        fragmentTransaction.commit();

        checklistEditText = findViewById(R.id.checklistEditText);
    }

}
