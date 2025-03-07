package com.jm.p_ai.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class AI_QandA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private AI_Question question;

    @OneToMany
    @JoinTable(
            name = "qanda_answer",
            joinColumns = @JoinColumn(name = "qanda_id"),
            inverseJoinColumns = @JoinColumn(name = "answer_id")
    )
    private List<AI_Answer> answers;

    // 2025/02/13 추가
    private String userId;

    public AI_QandA(AI_Question question, List<AI_Answer> answers, String userId) {
        this.question = question;
        this.answers = answers;
        this.userId = userId;
    }

    public AI_QandA() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AI_Question getQuestion() {
        return question;
    }

    public void setQuestion(AI_Question question) {
        this.question = question;
    }

    public List<AI_Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AI_Answer> answers) {
        this.answers = answers;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
