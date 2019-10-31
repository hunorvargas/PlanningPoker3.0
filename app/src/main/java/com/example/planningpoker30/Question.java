package com.example.planningpoker30;

public class Question {

    private int questionID;
    private String question;
    private String questionInf;

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestionInf() {
        return questionInf;
    }

    public void setQuestionInf(String questionInf) {
        this.questionInf = questionInf;
    }


    @Override
    public String toString() {
        return "Question{" +
                "questionID=" + questionID +
                ", question='" + question + '\'' +
                ", questionInf='" + questionInf + '\'' +
                '}';
    }
}
