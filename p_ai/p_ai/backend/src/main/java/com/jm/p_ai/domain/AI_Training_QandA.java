package com.jm.p_ai.domain;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "AI_TRAINING_QANDA")
public class AI_Training_QandA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "training_question_id", nullable = false)
    private AI_Training_Question trainingQuestion;

    @OneToMany(mappedBy = "trainingQandA")
    private List<AI_Training_Answer> trainingAnswers;

    public AI_Training_QandA() {}

    public AI_Training_QandA(AI_Training_Question trainingQuestion, List<AI_Training_Answer> trainingAnswers) {
        this.trainingQuestion = trainingQuestion;
        this.trainingAnswers = trainingAnswers;
    }

    // Getter & Setter 추가
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public AI_Training_Question getTrainingQuestion() { return trainingQuestion; }
    public void setTrainingQuestion(AI_Training_Question trainingQuestion) { this.trainingQuestion = trainingQuestion; }

    public List<AI_Training_Answer> getTrainingAnswers() { return trainingAnswers; }
    public void setTrainingAnswers(List<AI_Training_Answer> trainingAnswers) { this.trainingAnswers = trainingAnswers; }
}