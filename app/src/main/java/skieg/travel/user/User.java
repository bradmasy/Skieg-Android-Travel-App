package skieg.travel.user;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class User {
    private String firstName;
    private String lastName;
    private String city;
    private String username;
    private String email;
    private String password;
    private String id;

    // For new user
    public User() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        this.id  = databaseReference.push().getKey();
    }

    // Existing user
    public User(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }


    public User(String id, String firstName, String lastName, String city, String username, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
