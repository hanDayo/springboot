package com.example.demo;

/**
 * Created by sunlu on 2018/6/14.
 */
public class Course {
    private String cid;
    private String name;
    private String people_num;
    private String student;
    private String lid;

    public String getLid() {
        return lid;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }

    public void setName(String name){
        this.name=name;
    }

    public void setCid(String id){
        this.cid =id;
    }

    public void setPeople_num(String num){
        this.people_num=num;
    }

    public void setStudent(String student){ this.student=student;}

    public String getCid(){
        return cid;
    }

    public String getName(){
        return name;
    }

    public String getPeople_num(){
        return people_num;
    }

    public  String getStudent(){return student;}
}
