package com.jm.p_ai.presentation;

import com.jm.p_ai.domain.AI_Training_Question;
import java.time.LocalDateTime;

public class AI_Training_QuestionDto {

    private Long id;
    private String contents;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String tag;

    public AI_Training_QuestionDto() {}

    public AI_Training_QuestionDto(Long id, String contents, Long userId, LocalDateTime createdAt, LocalDateTime updatedAt, String tag) {
        this.id = id;
        this.contents = contents;
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.tag = tag;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getContents() { return contents; }
    public void setContents(String contents) { this.contents = contents; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public String getTag() { return tag; }
    public void setTag(String tag) { this.tag = tag; }

    // Entity → DTO 변환
    public static AI_Training_QuestionDto fromEntity(AI_Training_Question aiTrainingQuestion) {
        return new AI_Training_QuestionDto(
                aiTrainingQuestion.getId(),
                aiTrainingQuestion.getContents(),
                aiTrainingQuestion.getUser_id(),
                aiTrainingQuestion.getCreated_at(),
                aiTrainingQuestion.getUpdated_at(),
                aiTrainingQuestion.getTag()
        );
    }

    // DTO → Entity 변환
    public AI_Training_Question toEntity() {
        AI_Training_Question aiTrainingQuestion = new AI_Training_Question();
        aiTrainingQuestion.setId(this.id);
        aiTrainingQuestion.setContents(this.contents);
        aiTrainingQuestion.setUser_id(this.userId);
        aiTrainingQuestion.setCreated_at(this.createdAt);
        aiTrainingQuestion.setUpdated_at(this.updatedAt);
        aiTrainingQuestion.setTag(this.tag);

        return aiTrainingQuestion;
    }
}
