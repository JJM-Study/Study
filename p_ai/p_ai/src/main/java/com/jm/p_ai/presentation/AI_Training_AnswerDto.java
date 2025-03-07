package com.jm.p_ai.presentation;

import com.jm.p_ai.domain.AI_Training_Answer;
import com.jm.p_ai.domain.AI_Training_QandA;
import com.jm.p_ai.domain.AI_Training_Question;
import com.jm.p_ai.infrastructure.AI_Training_QandA_Repo;
import com.jm.p_ai.infrastructure.AI_Training_Question_Repo;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;

public class AI_Training_AnswerDto {

    private Long id;
    private String contents;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String tag;
    private Long qandAId;  // AI_Training_QandA ID
    private Long questionId;  // AI_Training_Question ID

    @Autowired
    private AI_Training_QandA_Repo aiTrainingQandARepo;

    @Autowired
    private AI_Training_Question_Repo aiTrainingQuestionRepo;

    public AI_Training_AnswerDto() {}

    public AI_Training_AnswerDto(Long id, String contents, Long userId, LocalDateTime createdAt, LocalDateTime updatedAt, String tag, Long qandAId, Long questionId) {
        this.id = id;
        this.contents = contents;
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.tag = tag;
        this.qandAId = qandAId;
        this.questionId = questionId;
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

    public Long getQandAId() { return qandAId; }
    public void setQandAId(Long qandAId) { this.qandAId = qandAId; }

    public Long getQuestionId() { return questionId; }
    public void setQuestionId(Long questionId) { this.questionId = questionId; }

    // DTO → Entity 변환 (toEntity)
    public AI_Training_Answer toEntity() {
        AI_Training_Answer aiTrainingAnswer = new AI_Training_Answer();
        aiTrainingAnswer.setId(this.id);
        aiTrainingAnswer.setContents(this.contents);
        aiTrainingAnswer.setUser_id(this.userId);
        aiTrainingAnswer.setCreated_at(this.createdAt);
        aiTrainingAnswer.setUpdated_at(this.updatedAt);
        aiTrainingAnswer.setTag(this.tag);

        // 🔥 AI_Training_QandA 연관 관계 설정
        if (this.qandAId != null) {
            AI_Training_QandA trainingQandA = aiTrainingQandARepo.findById(this.qandAId).orElse(null);
            aiTrainingAnswer.setTrainingQandA(trainingQandA);
        }

        // 🔥 AI_Training_Question 연관 관계 설정
        if (this.questionId != null) {
            AI_Training_Question trainingQuestion = aiTrainingQuestionRepo.findById(this.questionId).orElse(null);
            aiTrainingAnswer.setTrainingQuestion(trainingQuestion);
        }

        return aiTrainingAnswer;
    }

    // Entity → DTO 변환 (fromEntity)
    public static AI_Training_AnswerDto fromEntity(AI_Training_Answer aiTrainingAnswer) {
        return new AI_Training_AnswerDto(
                aiTrainingAnswer.getId(),
                aiTrainingAnswer.getContents(),
                aiTrainingAnswer.getUser_id(),
                aiTrainingAnswer.getCreated_at(),
                aiTrainingAnswer.getUpdated_at(),
                aiTrainingAnswer.getTag(),
                aiTrainingAnswer.getTrainingQandA() != null ? aiTrainingAnswer.getTrainingQandA().getId() : null,
                aiTrainingAnswer.getTrainingQuestion() != null ? aiTrainingAnswer.getTrainingQuestion().getId() : null
        );
    }
}
