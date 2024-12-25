package com.jm.p_ai.presentation;

import com.jm.p_ai.infrastructure.AI_QandA_Repo;

public class AI_QandADto {

    private Long questionId;
    private String questionContents;
    private Long answerId;
    private String answerContents;

    public AI_QandADto(Long questionId, String questionContents, Long answerId, String answerContents) {
        this.questionId = questionId;
        this.questionContents = questionContents;
        this.answerId = answerId;
        this.answerContents = answerContents;
    }

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
}
