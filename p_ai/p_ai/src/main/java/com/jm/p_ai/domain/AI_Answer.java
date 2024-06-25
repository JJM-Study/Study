package com.jm.p_ai.domain;

import jakarta.persistence.*;
import org.aspectj.weaver.patterns.TypePatternQuestions;

@Entity
public class AI_Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contents;

    @ManyToOne
    private AI_Question question;

    public AI_Answer() {

    }

    public AI_Answer(Long id, String contents) {
        this.id = id;
        this.contents = contents;
    }

    public AI_Answer(Long id, String contents, AI_Question question) {
        this.id = id;
        this.contents = contents;
        this.question = question;
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

    public AI_Question getquestion() {
        return question;
    }

    public void setquestion(AI_Question question) {
        this.question = question;
    }
}
