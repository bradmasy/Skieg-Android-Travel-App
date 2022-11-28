package skieg.travel;

/**
 * Represents a single CalendarEvent object in the database.
 */
public class CalendarEvent {
    // Attributes of a CalendarEvent object.
    String eventID;         // Unique ID of an event
    String title;           // Event title
    String description;     // Event description
    String location;        // Event location
    String date;            // Event date
    String userID;          // ID of user who created the event

    /**
     * 6 parameter constructor for a CalendarEvent object.
     * @param eventID: String, unique ID to identify the calendar event
     * @param title: String, title of the event
     * @param description: String, description of the event
     * @param location: String, a city where the event is happening
     * @param date: String, date of the event
     * @param userID: String, user who created the event
     */
    CalendarEvent(String eventID, String title, String description, String location, String date, String userID) {
        this.eventID = eventID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.date = date;
        this.userID = userID;
    }

    /**
     * Getter for title attribute.
     * @return title: string
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter for title attribute.
     * @param title: String
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter for description attribute.
     * @return description: String
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for description attribute.
     * @param description: String
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for location attribute.
     * @return location: String
     */
    public String getLocation() {
        return location;
    }

    /**
     * Setter for location attribute.
     * @param location: String
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Getter for date attribute.
     * @return  date: String
     */
    public String getDate() {
        return date;
    }

    /**
     * Setter for date attribute.
     * @param date: String
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Getter for event ID attribute.
     * @return eventID: String
     */
    public String getEventID() {
        return eventID;
    }

    /**
     * Setter for event ID attribute.
     * @param eventID: String
     */
    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    /**
     * Getter for user ID attribute.
     * @return userID: String
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Setter for user ID attribute.
     * @param userID: String
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }
}
