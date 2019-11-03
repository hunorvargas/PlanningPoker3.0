package com.example.planningpoker30;

import java.util.ArrayList;

public class Session {

    private String sessionID;
    private Question question;
    private ArrayList<User> users;

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Session{" +
                "sessionID='" + sessionID + '\'' +
                ",question=" + question +
                ", users=" + users +
                '}';
    }
}
