package skieg.travel.post;

import java.io.Serializable;

import skieg.travel.user.User;

public class Post implements Serializable {

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String information;
    private String date;
    private String userID;
    private String username;
    private String postID;

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

    public Post(User user, String date, String content, String postID){
        this.userID = user.getId();
        this.username = user.getUsername();
        this.date = date;
        this.information = content;
        this.postID = postID;

    }

    public void setPostID(String id){
        this.postID = id;
    }

    public void addInformation(String info){
        this.information = info;

    }

    public String getPostID(){
        return this.postID;
    }


}
