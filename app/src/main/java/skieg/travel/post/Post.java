package skieg.travel.post;

import skieg.travel.user.User;

public class Post {

    private String information;
    private String date;
    private String userID;

    public Post(User user, String date, String content){
        this.userID = user.getId();
        this.date = date;
        this.information = content;
    }

    public Post(String information){
        this.information = information;

    }


    public void addInformation(String info){
        this.information = info;

    }


}
