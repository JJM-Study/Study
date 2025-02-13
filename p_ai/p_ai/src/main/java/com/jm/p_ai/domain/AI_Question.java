package com.jm.p_ai.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class AI_Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contents;
    private String userId; // 2025/02/13 회원가입 기능 구현 전까진 get token 방식으로 활용할 것.

    @OneToMany(mappedBy = "question")   // AI_Answer 엔티티의 question 필드와 매핑된다. 2024/12/03 추ㄱ
    private List<AI_Answer> answers;

    public AI_Question() {

    }

    public AI_Question(Long id, String contents) {
        this.id = id;
        this.contents = contents;
    }

    public AI_Question(String contents, String userId) {
        this.contents = contents;
        this.userId = userId;
    }

    public AI_Question(Long id, String contents, String userId) {
        this.id = id;
        this.contents = contents;
        this.userId = userId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<AI_Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AI_Answer> answers) {
        this.answers = answers;
    }
}
