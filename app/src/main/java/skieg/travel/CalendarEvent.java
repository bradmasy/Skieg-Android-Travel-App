package skieg.travel;

/**
 * Represents a single CalendarEvent object in the database.
 */
public class CalendarEvent {
    // Attributes of a CalendarEvent object.
    String eventID;
    String title;
    String description;
    String location;
    String date;
    String userID;

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
     * Getters and setters for all instance variables.
     */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
