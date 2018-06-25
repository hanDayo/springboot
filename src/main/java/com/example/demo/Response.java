package com.example.demo;

/**
 * Created by sunlu on 2018/6/13.
 */
public class Response {
    String status;
    String type;
    String username;

    public void setStatus(String name){
        this.status=name;
    }

    public void setUsername(String pas){
        this.username=pas;
    }

    public void setType(String type){
        this.type=type;
    }

    public String getUsername(){
        return username;
    }

    public String getStatus(){
        return status;
    }

    public String getType(){
        return type;
    }
}
