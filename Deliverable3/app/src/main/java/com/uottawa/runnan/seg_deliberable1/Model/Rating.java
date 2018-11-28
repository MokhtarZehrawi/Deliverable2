package com.uottawa.runnan.seg_deliberable1.Model;

public class Rating {

    private int rating;
    private String comment;
    public Rating(int rating, String comment){

        this.rating=rating;
        this.comment=comment;

    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }


}
