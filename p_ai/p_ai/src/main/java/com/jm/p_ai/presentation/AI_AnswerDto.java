package com.jm.p_ai.presentation;

import com.jm.p_ai.domain.AI_Answer;
import com.jm.p_ai.domain.AI_Question;
import com.jm.p_ai.infrastructure.AI_Question_Repo;
import org.springframework.beans.factory.annotation.Autowired;

public class AI_AnswerDto {

    private Long id;

    private String contents;

    private Long questionId;

    @Autowired
    private AI_Question_Repo ai_question_repo;

    public AI_AnswerDto() {

    }

    public AI_AnswerDto(String contents) {
        this.contents = contents;
    }

    public AI_AnswerDto(Long id, String contents, Long questionId) {
        this.id = id;
        this.contents = contents;
        this.questionId = questionId;
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

    public Long getquestionId() {
        return questionId;
    }

    public void setquestionId(Long questionId) {
        this.questionId = questionId;
    }

    //public AI_Answer toAnswerEntity() {
    public AI_Answer toAnswerEntity(AI_AnswerDto ai_answerDto) {
        AI_Answer ai_answer = new AI_Answer();
               ai_answer.setId(ai_answerDto.getId());
               ai_answer.setContents(ai_answerDto.getContents());

                if(ai_answerDto.getquestionId() != null) {
                    AI_Question question = ai_question_repo.findById(ai_answerDto.getquestionId()).orElse(null);

                    ai_answer.setquestion(question);
                }

        return ai_answer;
    }

    public AI_AnswerDto toAnswerDto(AI_Answer ai_answer) {
        AI_AnswerDto ai_answerDto = new AI_AnswerDto();
        ai_answerDto.setId(ai_answer.getId());
        ai_answerDto.setContents(ai_answer.getContents());

        if ( ai_answer.getquestion() != null) {
            ai_answerDto.setquestionId(ai_answer.getquestion().getId());
        }

        return ai_answerDto;
    }

}
