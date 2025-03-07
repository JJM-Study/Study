package com.jm.p_ai.presentation;

import com.jm.p_ai.domain.AI_Training_Answer;
import com.jm.p_ai.domain.AI_Training_Question;

public class AI_Training_QandADto {

    private Long questionId;
    private String questionContents;
    private Long answerId;
    private String answerContents;

    public AI_Training_QandADto() {}

    public AI_Training_QandADto(Long questionId, String questionContents, Long answerId, String answerContents) {
        this.questionId = questionId;
        this.questionContents = questionContents;
        this.answerId = answerId;
        this.answerContents = answerContents;
    }

    // Getter & Setter 추가
    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getQuestionContents() {
        return questionContents;
    }

    public void setQuestionContents(String questionContents) {
        this.questionContents = questionContents;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public String getAnswerContents() {
        return answerContents;
    }

    public void setAnswerContents(String answerContents) {
        this.answerContents = answerContents;
    }

    // Entity → DTO 변환
    public static AI_Training_QandADto fromEntities(AI_Training_Question question, AI_Training_Answer answer) {
        return new AI_Training_QandADto(
                question.getId(),
                question.getContents(),
                answer.getId(),
                answer.getContents()
        );
    }
}
