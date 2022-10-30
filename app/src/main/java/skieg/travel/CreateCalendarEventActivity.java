package skieg.travel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateCalendarEventActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Button createEventBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_calendar_event);

        createEventBtn = findViewById(R.id.createEventBtn);
        createEventBtn.setOnClickListener(view -> {
            EditText titleInput = findViewById(R.id.title);
            EditText descriptionInput = findViewById(R.id.description);
            EditText locationInput = findViewById(R.id.location);
            EditText dateInput = findViewById(R.id.date);

            String title = titleInput.getText().toString();
            String description = descriptionInput.getText().toString();
            String location = locationInput.getText().toString();
            String date = dateInput.getText().toString();

            writeUserToFirebase(title, description, location, date);
        });
    }


    public void writeUserToFirebase(String title, String description, String location, String date) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance("https://skieg-364814-default-rtdb.firebaseio.com/").getReference();

        String id = databaseReference.push().getKey();
        CalendarEvent calendarEvent = new CalendarEvent(id, title, description, location, date);

        Task setValueTask = databaseReference.child("CalendarEvent").child(id).setValue(calendarEvent);

        setValueTask.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast toast = Toast.makeText(getApplicationContext(), "Event added!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }


    public void backBtnClicked(View view) {
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }
}