package com.jm.p_ai.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "AI_TRAINING_QUESTIONS")
public class AI_Training_Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contents;
    private Long user_id;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private String tag;

    @OneToMany(mappedBy = "trainingQuestion")  // AI_Training_Answer와 매핑
    private List<AI_Training_Answer> trainingAnswers;

    public AI_Training_Question() {}

    public AI_Training_Question(Long id, String contents, Long user_id, LocalDateTime created_at, LocalDateTime updated_at, String tag, List<AI_Training_Answer> trainingAnswers) {
        this.id = id;
        this.contents = contents;
        this.user_id = user_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.tag = tag;
        this.trainingAnswers = trainingAnswers;
    }

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

    public List<AI_Training_Answer> getTrainingAnswers() { return trainingAnswers; }
    public void setTrainingAnswers(List<AI_Training_Answer> trainingAnswers) { this.trainingAnswers = trainingAnswers; }
}
