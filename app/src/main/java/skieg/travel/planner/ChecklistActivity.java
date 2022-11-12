package skieg.travel.planner;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import skieg.travel.InputValidation;
import skieg.travel.MainActivity;
import skieg.travel.R;
import skieg.travel.RecyclerViewCalendar;
import skieg.travel.planner.fragments.ChecklistFragment;

public class ChecklistActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

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

//        Button addItemBtn = findViewById(R.id.addItemBtn);
//        addItemBtn.setOnClickListener(view -> {
//            System.out.println("ADD BUTTON CLICKED");
//            Log.d("CHECK", "ADD BUTTON CLICKED");
//
//            String checklistItemStr = checklistEditText.getText().toString();
//            if (InputValidation.invalidStringInput(checklistItemStr)) {
//                Toast toast = Toast.makeText(getApplicationContext(), "Checklist item cannot be empty.", Toast.LENGTH_SHORT);
//                toast.show();
//
//            } else {
//                addItemToFirebase(checklistItemStr);
//            }
//        });
//
//        Button clearItemBtn = findViewById(R.id.clearItemBtn);
//        clearItemBtn.setOnClickListener(view -> {
//            Log.d("CHECK", "CLEAR BUTTON CLICKED");
//            checklistEditText.setText("");
//        });
//
//        Button backBtn = findViewById(R.id.backBtn);
//        backBtn.setOnClickListener(view -> {
//            Log.d("CHECK", "BACK BUTTON CLICKED");
//            String id = MainActivity.USER.getId();
//            Log.d("CHECK", id);
//        });

    }


//    public void addItemToFirebase(String checklistItem) {
//        System.out.println("FIREBASE METHOD");
//        Log.d("CHECK", "FIREBASE");
//
//        firebaseDatabase = FirebaseDatabase.getInstance();
//        databaseReference = FirebaseDatabase.getInstance("https://skieg-364814-default-rtdb.firebaseio.com/").getReference();
//
//        String id = MainActivity.USER.getId();
////        CalendarEvent calendarEvent = new CalendarEvent();
//
//        Task setValueTask = databaseReference.child("Users").child(id).child("Planner").child("Checklist").setValue(checklistItem);
//
//        setValueTask.addOnSuccessListener(new OnSuccessListener() {
//            @Override
//            public void onSuccess(Object o) {
//                Toast toast = Toast.makeText(getApplicationContext(), "Checklist item added!", Toast.LENGTH_SHORT);
//                toast.show();
//
//                checklistEditText.setText("");
//            }
//        });
//    }
//
//
//
//    private void getDataFromFirebase() {
//        String id = MainActivity.USER.getId();
//        databaseReference = FirebaseDatabase.getInstance("https://skieg-364814-default-rtdb.firebaseio.com/").getReference().child("Users").child(id).child("Planner").child("Checklist");
//
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Log.d("LOG", "IN DATA SNAPSHOT METHOD");
//
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    String currSnapshot = String.valueOf(dataSnapshot.getValue());
////                    String[] dataValues = currSnapshot.split(",");
////                    String data = parseDataValue(dataValues[0]);
////                    Log.d("DATA:", currSnapshot);
//
//
//                    Log.d("DATASNAP:", currSnapshot);
//                }
//
////                recyclerViewCalendar = new RecyclerViewCalendar(titles, descriptions, locations, dates);
////                calendarFragment.initializeAdapter(recyclerViewCalendar);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.d("LOG", "DB ERROR");
//            }
//        });
//    }
}
