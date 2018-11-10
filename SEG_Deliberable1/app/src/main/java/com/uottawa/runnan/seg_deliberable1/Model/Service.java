package com.uottawa.runnan.seg_deliberable1.Model;






import java.util.ArrayList;
import java.util.List;


public class Service {

    private String nameOfService;
    private double hourlyRate;

    public Service(){

    }
    public Service(String nameOfService, double hourlyRate){

        this.nameOfService = nameOfService;
        this.hourlyRate = hourlyRate;

    }

    public String getNameOfService(){
        return nameOfService;
    }
    public void setNameOfService(String newName){
        nameOfService = newName;
    }

    public  double getHourlyRate(){
        return hourlyRate;
    }
    public void setHourlyRate(double newRate){
        hourlyRate = newRate;
    }

}
