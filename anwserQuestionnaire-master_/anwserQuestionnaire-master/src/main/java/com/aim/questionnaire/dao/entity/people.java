package com.aim.questionnaire.dao.entity;

public class people {
    private int id;
    private String problem;
    private String answer;
    public int getId(){
        return id;
    }
    public void setId(int ID){
        id=ID;
    }
    public String getProblem(){
        return problem;
    }
    public void setProblem(String Problem){
        problem=Problem;
    }
    public String getAnswer(){
        return answer;
    }
    public void setAnswer(String Answer){
        answer=Answer;
    }
}
