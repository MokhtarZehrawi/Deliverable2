package com.uottawa.runnan.seg_deliberable1.Model;

public class Rate {

    private int rating;
    private String comment;

    public Rate(int rating, String comment){

        this.rating=rating;
        this.comment=comment;
    }
    public Rate(){}

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
