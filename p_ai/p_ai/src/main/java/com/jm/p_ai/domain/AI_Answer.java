package com.jm.p_ai.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.aspectj.weaver.patterns.TypePatternQuestions;

@Entity
public class AI_Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contents;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)  // 2024/12/03 추가  // 엔티티나 DTO가 아니라, 테이블의 필드 자체를 직접 참조함.
    @JsonIgnore  // JSON 직렬화 시 무한 루프 방지
    private AI_Question question;

    private String userId; // 2024/12/04 사용자별 답변 관리를 위해서 추가함.

    public AI_Answer() {

    }

    public AI_Answer(Long id, String contents) {
        this.id = id;
        this.contents = contents;
    }

    public AI_Answer(AI_Question question, String contents) {
        this.question = question;
        this.contents = contents;
    }

    public AI_Answer(AI_Question question, String contents, String userId) {
        this.question = question;
        this.contents = contents;
        this.userId = userId;
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

    public AI_Question getQuestion() {
        return question;
    }

    public void setQuestion(AI_Question question) {
        this.question = question;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
