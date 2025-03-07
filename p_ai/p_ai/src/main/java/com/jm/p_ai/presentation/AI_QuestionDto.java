package com.jm.p_ai.presentation;

import com.jm.p_ai.domain.AI_Question;

public class AI_QuestionDto {

    private Long id;

    private String contents;

    private String userId;

    public AI_QuestionDto() {

    }

    public AI_QuestionDto(Long id, String contents) {
        this.id = id;
        this.contents = contents;
    }


    public AI_QuestionDto(String contents) {
        this.contents = contents;
    }

    public AI_QuestionDto(Long id, String contents, String userId) {
        this.id = id;
        this.contents = contents;
        this.userId = userId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public AI_QuestionDto toQuestionDto(AI_Question ai_question) {
        AI_QuestionDto ai_questionDto = new AI_QuestionDto(ai_question.getId(), ai_question.getContents(), ai_question.getUserId());

        return ai_questionDto;
    }

    public AI_Question toQuestionEntity(AI_QuestionDto ai_questionDto) {
        return new AI_Question(ai_questionDto.getId(), ai_questionDto.getContents(), ai_questionDto.getUserId());
    }

}
