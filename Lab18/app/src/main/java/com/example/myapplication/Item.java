package com.example.myapplication;

public class Item {
    private String title, id;
    private int like;
    public Item(){
    }
    public Item(String title, String id, int like){
        this.title = title;
        this.id = id;
        this.like = like;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
