package com.aim.questionnaire.beans;

import com.aim.questionnaire.dao.entity.UserEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by myz on 2017/7/8.
 */
//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
//@Data
public class HttpResponseEntity implements Serializable {

    private String code; //状态码
    private Object data; //内容
    private String message; //状态消息
    private List<Object> user;

    public List<Object> getUser(){
        return user;
    }
    public void setUser(List<Object> uu){
        user=uu;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
