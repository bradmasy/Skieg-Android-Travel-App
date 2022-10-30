package skieg.travel;

public class CalendarEvent {
    String eventID;
    String title;
    String description;
    String location;
    String date;

    CalendarEvent(String eventID, String title, String description, String location, String date) {
        this.eventID = eventID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.date = date;
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
}
