package skieg.travel.post;

import java.io.Serializable;

import skieg.travel.user.User;

public class Post implements Serializable {

    private String information;
    private String date;
    private String userID;
    private String username;

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Post(User user, String date, String content){
        this.userID = user.getId();
        this.username = user.getUsername();
        this.date = date;
        this.information = content;
    }

    public void addInformation(String info){
        this.information = info;

    }


}
