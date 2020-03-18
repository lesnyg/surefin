package com.jubumam.surefin;

public class Answer {
    private String date;        //작성날짜
    private String responsibility;      //작성자(요양사)
    private String title;               //문의제목
    private String contents;            //문의내용
    private String answer;            //답변
    private String answerDate;            //답변날짜

    public Answer(String date, String responsibility, String title, String contents, String answer, String answerDate) {
        this.date = date;
        this.responsibility = responsibility;
        this.title = title;
        this.contents = contents;
        this.answer = answer;
        this.answerDate = answerDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswerDate() {
        return answerDate;
    }

    public void setAnswerDate(String answerDate) {
        this.answerDate = answerDate;
    }
}
