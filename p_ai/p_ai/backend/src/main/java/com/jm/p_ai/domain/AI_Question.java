package com.jm.p_ai.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class AI_Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contents;

    @OneToMany(mappedBy = "question")   // AI_Answer 엔티티의 question 필드와 매핑된다. 2024/12/03 추ㄱ
    private List<AI_Answer> answers;

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

    public List<AI_Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AI_Answer> answers) {
        this.answers = answers;
    }
}
