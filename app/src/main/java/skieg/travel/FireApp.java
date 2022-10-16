package skieg.travel;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FireApp extends AppCompatActivity {
    private EditText edtUsername, edtPassword;
    private Button btnSignup, btnLogin, btnDelete;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_firebase);

        // get the instance of the Firebase database
        firebaseDatabase = FirebaseDatabase.getInstance();
        // get the reference to the JSON tree
        databaseReference = firebaseDatabase.getReference();

        edtUsername = findViewById(R.id.editTextUsername);
        edtPassword = findViewById(R.id.editTextPassword);

        btnSignup = findViewById(R.id.btnSignUp);
        btnSignup.setOnClickListener(view -> {
            if(checkFields()) {
                addData();
            }
        });

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(view -> {
            if(checkFields()) {
                login();
            }
        });

        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(view -> {
            if(checkFields()) {
                deleteData();
            }
        });
    }

    private boolean checkFields() {
        username = edtUsername.getText().toString().trim();
        password = edtPassword.getText().toString().trim();
        if(TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Please enter a username", Toast.LENGTH_LONG).show();
            return false;
        }
        if(TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void addData() {
        // use push method to generate a unique key for a new child node
        String id = databaseReference.push().getKey();
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        // create a task to set the value of the node as the new user
        Task setValueTask = databaseReference.child("Users").child(id).setValue(user);

        // add a success listener to the task
        setValueTask.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(FireApp.this, "Account created", Toast.LENGTH_LONG).show();
                edtUsername.setText("");
                edtPassword.setText("");
            }
        });

        // add a failure listener to the task
        setValueTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(FireApp.this, e.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void login() {
        // get the reference to the JSON tree
        databaseReference = firebaseDatabase.getReference();

        // add a value event listener to the Users node
        databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            // called to read a static snapshot of the contents at a given path
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean match = false;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    if(username.equals(user.getUsername()) && password.equals(user.getPassword())) {
                        match = true;
                        Toast.makeText(FireApp.this, "Access granted", Toast.LENGTH_LONG).show();
                    }
                }
                if(!match) {
                    Toast.makeText(FireApp.this, "Access denied", Toast.LENGTH_LONG).show();
                }
            }

            // called when the client doesn't have permission to access the data
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void deleteData() {
        // get the reference to the JSON tree
        databaseReference = firebaseDatabase.getReference();

        // add a value event listener to the Users node
        databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            // called to read a static snapshot of the contents at a given path
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean found = false;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    if(username.equals(user.getUsername()) && password.equals(user.getPassword())) {
                        found = true;
                        snapshot.getRef().setValue(null);
                        Toast.makeText(FireApp.this, "Account deleted", Toast.LENGTH_LONG).show();
                    }
                }
                if(!found) {
                    Toast.makeText(FireApp.this, "No matching account found", Toast.LENGTH_LONG).show();
                }
            }

            // called when the client doesn't have permission to access the data
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}