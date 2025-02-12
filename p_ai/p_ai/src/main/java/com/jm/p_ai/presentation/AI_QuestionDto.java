package com.jm.p_ai.presentation;

import com.jm.p_ai.domain.AI_Question;

public class AI_QuestionDto {

    private Long id;

    private String contents;

    public AI_QuestionDto() {

    }

    public AI_QuestionDto(Long id, String contents) {
        this.id = id;
        this.contents = contents;
    }


    public AI_QuestionDto(String contents) {
        this.contents = contents;
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

    public AI_QuestionDto toQuestionDto(AI_Question ai_question) {
        AI_QuestionDto ai_questionDto = new AI_QuestionDto(ai_question.getId(), ai_question.getContents());

        return ai_questionDto;
    }

    public AI_Question toQuestionEntity(AI_QuestionDto ai_questionDto) {
        return new AI_Question(ai_questionDto.getId(), ai_questionDto.getContents());
    }

}
