package com.elise.elisefinal.model;

import java.io.Serializable;

public class Meme implements Serializable {
    private int id;
    private String name;
    private String url;
    private int width;
    private int height;
    private int boxCount;
    private int captions;

    public Meme() {
    }

    public Meme(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public Meme(int id, String name, String url, int width, int height, int boxCount, int captions) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.width = width;
        this.height = height;
        this.boxCount = boxCount;
        this.captions = captions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getBoxCount() {
        return boxCount;
    }

    public void setBoxCount(int boxCount) {
        this.boxCount = boxCount;
    }

    public int getCaptions() {
        return captions;
    }

    public void setCaptions(int captions) {
        this.captions = captions;
    }

    @Override
    public String toString() {
        return "\nMeme name: " + name +
                "\nID: " + id +
                "\nLink: " + url + "\n";
    }
}
