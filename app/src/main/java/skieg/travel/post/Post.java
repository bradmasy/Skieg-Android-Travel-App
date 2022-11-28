package skieg.travel.post;

import java.io.Serializable;

import skieg.travel.user.User;

/**
 * Post Class.
 */
public class Post implements Serializable {
    private String information;
    private String date;
    private String userID;
    private String username;
    private String postID;
    private String country;

    /**
     * Constructor for post.
     *
     * @param user the user who is making the post.
     * @param date the date of the post.
     * @param content the content of the post.
     * @param postID the id of the post.
     * @param country the country of the post.
     */
    public Post(User user, String date, String content, String postID, String country){
        this.userID = user.getId();
        this.username = user.getUsername();
        this.date = date;
        this.information = content;
        this.postID = postID;
        this.country = country;

    }
    /**
     * Gets the username.
     *
     * @return the users username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the users username.
     *
     * @param username the username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the information of the post.
     *
     * @return the post information.
     */
    public String getInformation() {
        return information;
    }

    /**
     * Sets the information of the post.
     *
     * @param information the information to be set.
     */
    public void setInformation(String information) {
        this.information = information;
    }

    /**
     * Gets the date of the post.
     *
     * @return the date of the post.
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the date of the post.
     *
     * @param date the date of the post.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Gets the user ID who posted.
     *
     * @return the users id.
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Sets the users ID.
     *
     * @param userID the id to set.
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * Sets the post ID.
     *
     * @param id the id to set.
     */
    public void setPostID(String id){
        this.postID = id;
    }

    /**
     * Adds the information to the post.
     *
     * @param info the information we are setting.
     */
    public void addInformation(String info){
        this.information = info;
    }

    /**
     * Gets the posts id.
     *
     * @return the id of the post.
     */
    public String getPostID(){
        return this.postID;
    }

    /**
     * Gets the country of the post.
     *
     * @return the posts country.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the country of the post.
     *
     * @param country the country we are setting for the post.
     */
    public void setCountry(String country) {
        this.country = country;
    }
}
