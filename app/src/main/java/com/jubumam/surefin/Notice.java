package com.jubumam.surefin;

public class Notice {
    private String title;
    private String date;
    private String contents;

    public Notice(String title, String date, String contents) {
        this.title = title;
        this.date = date;
        this.contents = contents;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
