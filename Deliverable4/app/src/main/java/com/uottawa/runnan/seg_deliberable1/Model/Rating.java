package com.uottawa.runnan.seg_deliberable1.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Rating{

    private ArrayList<Rate> rateList;
    private double avgRating;
    private String name;

    public Rating(String name, double avgRating){
        this.avgRating=avgRating;
        this.name=name;
        rateList = new ArrayList<>();

    }

    public Rating(Rating rating){
        rateList=rating.getRateList();
        updateAvgRate();
    }

    public Rating() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
        updateAvgRate();
    }

    public double getAvgRating() {
        return avgRating;
    }

    public void setRateList(ArrayList<Rate> rateList) {
        this.rateList = rateList;
    }

    public ArrayList<Rate> getRateList() {
        return rateList;
    }

    public void removeRating(Rate rate){

        for(int i=0; i<rateList.size(); i++){

            if(rateList.get(i).equals(rate)){
                rateList.remove(i);
            }
        }
        updateAvgRate();
    }


    public void addRating(Rate rate){
        rateList.add(rate);
        updateAvgRate();
    }

    public void updateAvgRate(){
        int count=0;
        for(int i = 0; i<rateList.size(); i++){
            count= count + rateList.get(i).getRating();

        }
        avgRating = Math.round( ((double) count)/rateList.size() *10 )/10.0;
    }

}
