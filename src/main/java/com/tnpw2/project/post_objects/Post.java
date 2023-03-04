package com.tnpw2.project.post_objects;

import jakarta.persistence.*;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String header;
    private String text;
    private Long user_id;
    private String username;

    public Post() {
    }

    public Post(String header, String text, Long user_id) {
        this.header = header;
        this.text = text;
        this.user_id = user_id;
    }

    public Post(Long id, String header, String text, Long user_id) {
        this.id = id;
        this.header = header;
        this.text = text;
        this.user_id = user_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
