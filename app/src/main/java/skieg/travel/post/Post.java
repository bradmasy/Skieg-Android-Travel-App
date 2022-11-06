package skieg.travel.post;

import skieg.travel.user.User;

public class Post {

    private String information;
    private String date;
    private User user;

    public Post(User user){
        information = null;
        this.user = user;
    }

    public Post(String information){
        this.information = information;

    }


    public void addInformation(String info){
        this.information = info;

    }


}
