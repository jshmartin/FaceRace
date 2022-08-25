package com.example.facerace;

import java.io.Serializable;

public class Face implements Serializable {

    private String name;
    private String imageUrl;
    private boolean isSelected;

    public Face() {
    }

    public Face(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.isSelected = false;
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    public void setSelected(boolean value) {
        this.isSelected = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
