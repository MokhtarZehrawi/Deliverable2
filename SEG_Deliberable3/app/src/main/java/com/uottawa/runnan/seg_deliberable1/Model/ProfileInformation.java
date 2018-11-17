package com.uottawa.runnan.seg_deliberable1.Model;

public class ProfileInformation {

    String name;
    String address;
    boolean licensed;
    String description;


    public ProfileInformation(){


    }

    public ProfileInformation(String name, String address, boolean licensed, String description){

        this.name = name;
        this.address = address;
        this.licensed = licensed;
        this.description = description;

    }

    public String getName(){ return name; }
    public void setName(String newName){ name = newName;}

    public String getAddress(){ return  address; }
    public void setAddress(String newAddress){ address = newAddress; }

    public String getDescription(){ return  description; }
    public void setDescription(String newDescription){ description = newDescription; }

    public boolean isLicensed(){ return licensed; }
    public void setLicensed(boolean nLicensed){ licensed = nLicensed; }

}
