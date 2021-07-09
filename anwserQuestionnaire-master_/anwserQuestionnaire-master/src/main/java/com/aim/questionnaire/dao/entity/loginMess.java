package com.aim.questionnaire.dao.entity;

import java.util.Date;

public class loginMess {
    private  String id;
    private String user_id;
    private Date operate_time;
    public String getId(){
        return id;
    }
    public void setId(String id1){
        id=id1;
    }
    public String getUser_id(){
        return user_id;
    }
    public void setUser_id(String user){
        user_id=user;
    }
    public Date getOperate_time(){
        return operate_time;
    }
    public void setOperate_time(Date date){
        operate_time=date;
    }


}
