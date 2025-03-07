package com.jm.p_ai.presentation;

import com.jm.p_ai.domain.AI_QandA;
import com.jm.p_ai.infrastructure.AI_QandA_Repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AI_QandADto {

    private Long questionId;
    private String questionContents;
    private Long answerId;
    private String answerContents;
    private String userId;

    public AI_QandADto(Long questionId, String questionContents, Long answerId, String answerContents, String userId) {
        this.questionId = questionId;
        this.questionContents = questionContents;
        this.answerId = answerId;
        this.answerContents = answerContents;
        this.userId = userId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public static List<AI_QandADto> toQandADto(List<AI_QandADto> rawData) {
        Map<Long, AI_QandADto> groupedData = new HashMap<>();

        for (AI_QandADto dto : rawData) {
            // 이미 존재하는 질문인지 확인하고 추가
            groupedData.putIfAbsent(dto.getQuestionId(), dto);
        }

        return new ArrayList<>(groupedData.values());
    }
}
