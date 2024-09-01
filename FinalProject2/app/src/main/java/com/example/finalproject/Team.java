package com.example.finalproject;

import java.io.Serializable;

public class Team implements Serializable {
    private String name;
    private float score;

    private boolean isUp;
    private boolean startsUp;

    private boolean isEven;

    public Team(){
        this.name = "Team";
        this.score = 0;
        this.isUp = false;
        this.startsUp = false;
        this.isEven = false;
    }

    public Team(String name, float score, boolean isUp){
        this.name = name;
        this.score = score;
        this.isUp = isUp;
    }

    public String getName(){
        return name;
    }

    public float getScore(){
        return score;
    }

    public boolean getIsUp() {return isUp;}

    public void setName(String name){
        this.name = name;
    }

    public void setScore(float score){
        this.score = score;
    }

    public void setIsUp(boolean isUp) {this.isUp = isUp;}

    public boolean getStartsUp() {return startsUp;}

    public void setStartsUp(boolean startsUp){this.startsUp = startsUp;}

    public boolean getisEven() {return isEven;}

    public void setisEven(boolean isEven){this.isEven = isEven;}

}
