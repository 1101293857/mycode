package com.aim.questionnaire.dao.entity;

public class picture {
    private String username;
    private byte[] pic;
    public void setUsername(String User){
        username=User;
    }
    public String getUsername(){
        return username;
    }
    public void setPic(byte[] picc){
        pic=picc;
    }
    public byte[] getPic(){
        return pic;
    }

}
