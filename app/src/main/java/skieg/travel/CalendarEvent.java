package skieg.travel;

public class CalendarEvent {
    String eventID;
    String title;
    String description;
    String location;
    String date;
    String userID;

    CalendarEvent(String eventID, String title, String description, String location, String date, String userID) {
        this.eventID = eventID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.date = date;
        this.userID = userID;
    }

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
