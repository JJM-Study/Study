package com.jm.p_ai.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "AI_TRAINING_ANSWERS")
public class AI_Training_Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contents;
    private Long user_id;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private String tag;

    @ManyToOne
    @JoinColumn(name = "training_question_id", nullable = true)
    @JsonIgnore
    private AI_Training_Question trainingQuestion;

    @ManyToOne
    @JoinColumn(name = "training_qanda_id", nullable = true)
    @JsonIgnore
    private AI_Training_QandA trainingQandA;

    public AI_Training_Answer() {}

    public AI_Training_Answer(Long id, String contents, Long user_id, LocalDateTime created_at, LocalDateTime updated_at, String tag, AI_Training_Question trainingQuestion, AI_Training_QandA trainingQandA) {
        this.id = id;
        this.contents = contents;
        this.user_id = user_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.tag = tag;
        this.trainingQuestion = trainingQuestion;
        this.trainingQandA = trainingQandA;
    }

    // Getter & Setter 추가 (setContents 포함)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getContents() { return contents; }
    public void setContents(String contents) { this.contents = contents; }

    public Long getUser_id() { return user_id; }
    public void setUser_id(Long user_id) { this.user_id = user_id; }

    public LocalDateTime getCreated_at() { return created_at; }
    public void setCreated_at(LocalDateTime created_at) { this.created_at = created_at; }

    public LocalDateTime getUpdated_at() { return updated_at; }
    public void setUpdated_at(LocalDateTime updated_at) { this.updated_at = updated_at; }

    public String getTag() { return tag; }
    public void setTag(String tag) { this.tag = tag; }

    public AI_Training_Question getTrainingQuestion() { return trainingQuestion; }
    public void setTrainingQuestion(AI_Training_Question trainingQuestion) { this.trainingQuestion = trainingQuestion; }

    public AI_Training_QandA getTrainingQandA() { return trainingQandA; }
    public void setTrainingQandA(AI_Training_QandA trainingQandA) { this.trainingQandA = trainingQandA; }
}
