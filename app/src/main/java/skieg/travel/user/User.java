package skieg.travel.user;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class User {
    private String username;
    private String password;
    private String id;

    // For new user
    public User() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        this.id  = databaseReference.push().getKey();
//        Task setValueTask = databaseReference.child("Users").child(id).setValue(this);
//
//        setValueTask.addOnSuccessListener(new OnSuccessListener(){
//
//            @Override
//            public void onSuccess(Object o) {
//                Log.d("Success: ", "user: " + id + " has been created.");
//            }
//        });

    }

    // Existing user
    public User(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String toString() {
        return "Username: " +  username + ", Password: " + password;
    }
}
