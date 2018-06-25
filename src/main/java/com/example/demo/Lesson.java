package com.example.demo;

/**
 * Created by sunlu on 2018/6/13.
 */
public class Lesson {
    private String id;
    private String name;
    private String people_num;
    private String teacher;

    public void setName(String name){
        this.name=name;
    }

    public void setID(String id){
        this.id =id;
    }

    public void setPeople_num(String num){
        this.people_num=num;
    }

    public void setTeacher(String teacher){ this.teacher=teacher;}

    public String getID(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getPeople_num(){
        return people_num;
    }

    public  String getTeacher(){return teacher;}
}
