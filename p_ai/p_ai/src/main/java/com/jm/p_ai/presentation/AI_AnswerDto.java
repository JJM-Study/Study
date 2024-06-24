package com.jm.p_ai.presentation;

public class AI_AnswerDto {

    private Long id;

    private String contents;

    private String AI_question;

    public AI_AnswerDto() {

    }

    public AI_AnswerDto(String contents) {
        this.contents = contents;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
