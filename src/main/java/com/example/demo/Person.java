package com.example.demo;

public class Person {
    private String username;
    private String password;
    private String type;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUsername(String name){
        this.username=name;
    }

    public void setPassword(String pas){
        this.password=pas;
    }

    public void setType(String type){
        this.type=type;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public String getType(){
        return type;
    }
}