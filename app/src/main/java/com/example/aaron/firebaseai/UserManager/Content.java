package com.example.aaron.firebaseai.UserManager;

public class Content {

    String title;
    String content;
    String time;
    String tag;

    public Content() {
    }

    public Content(String title, String content, String time, String tag) {
        this.title = title;
        this.content = content;
        this.time = time;
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
