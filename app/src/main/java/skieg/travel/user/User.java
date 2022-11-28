package skieg.travel.user;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * User Class.
 */
public class User {
    private String firstName;
    private String lastName;
    private String city;
    private String username;
    private String email;
    private String password;
    private String id;

    /**
     * User Constructor.
     */
    public User() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        this.id  = databaseReference.push().getKey();
    }

    /**
     * User Constructor.
     *
     * @param id id of the user.
     * @param username username of the user.
     * @param password password of the user.
     */
    public User(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    /**
     * Used Constructor.
     *
     * @param id id of the user.
     * @param firstName the users first name.
     * @param lastName the users last name.
     * @param city the users city.
     * @param username the username of the user.
     * @param email the email of the user.
     * @param password the password of the user.
     */
    public User(String id, String firstName, String lastName, String city, String username, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    /**
     * Gets the first name of the user.
     *
     * @return the users first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the user.
     *
     * @param firstName the first name of the user.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the user.
     *
     * @return the last name of the user.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the user.
     *
     * @param lastName the last name of the user.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the city.
     *
     * @return the city.
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city.
     *
     * @param city gets the city.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the users email.
     *
     * @return the users email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the users email.
     *
     * @param email the email to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the users username.
     *
     * @return the users username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the users username.
     *
     * @param username the name to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password.
     *
     * @return the users password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the users password.
     *
     * @param password the password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the ID of the user.
     *
     * @return the id of the user.
     */
    public String getId(){
        return this.id;
    }

    /**
     * Sets the ID of the user.
     *
     * @param id the id to set.
     */
    public void setId(String id){
        this.id = id;
    }

    /**
     * The Overwritten ToString method.
     *
     * @return the users information.
     */
    public String toString() {
        return "Username: " +  username + ", Password: " + password;
    }
}
