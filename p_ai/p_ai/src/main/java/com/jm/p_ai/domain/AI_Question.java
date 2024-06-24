package com.jm.p_ai.domain;

import jakarta.persistence.*;

@Entity
public class AI_Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contents;

    public AI_Question() {

    }

    public AI_Question(Long id, String contents) {
        this.id = id;
        this.contents = contents;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
