package com.example.study.ui.faculty;

import java.io.Serializable;

public class TeacherData implements Serializable {
    String name, email, post, image, category, key;

    public TeacherData() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public TeacherData(String name, String email, String post, String image, String category, String key) {
        this.name = name;
        this.email = email;
        this.post = post;
        this.image = image;
        // Check if category is null, set a default value if it is
        this.category = (category != null) ? category : "Unknown";
        this.key = key;
    }
}
