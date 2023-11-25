package com.example.exhibitionguide;
import java.io.Serializable;
public class exhibition implements Serializable  {

    private String name;
   private int visitor;
    private String date;
    private String Time;
    private int dayOfWeek;

    public exhibition(String name,String date,String time,int visitor,int dayOfWeek){
        this.date = date;
        this.name = name;
        this.Time = time;
        this.visitor=visitor;
        this.dayOfWeek = dayOfWeek;
    }

    public String getName(){
        return name;
    }
    public String getTime(){
        return Time;
    }
    public int getVisitor(){return visitor;}
    public int getDayOfWeek(){return dayOfWeek;}
    public String getDate(){return date;}

}
