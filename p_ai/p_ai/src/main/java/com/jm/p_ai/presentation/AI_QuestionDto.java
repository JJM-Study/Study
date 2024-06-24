package com.jm.p_ai.presentation;

public class AI_QuestionDto {

    private Long id;

    private String contents;

    public AI_QuestionDto() {

    }

    public AI_QuestionDto(String contents) {
        this.contents = contents;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
