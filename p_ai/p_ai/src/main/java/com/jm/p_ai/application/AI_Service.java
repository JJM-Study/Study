package com.jm.p_ai.application;

import com.jm.p_ai.domain.AI_Answer;
import com.jm.p_ai.domain.AI_Question;
import com.jm.p_ai.infrastructure.AI_Answer_Repo;
import com.jm.p_ai.infrastructure.AI_Question_Repo;
import com.jm.p_ai.infrastructure.AI_User_Repo;
import com.jm.p_ai.presentation.AI_AnswerDto;
import com.jm.p_ai.presentation.AI_QuestionDto;
import com.jm.p_ai.presentation.AI_UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AI_Service {

    private AI_User_Repo ai_userRepo;
    private AI_Answer_Repo ai_answerRepo;
    private AI_Question_Repo ai_questionRepo;

    @Autowired
    public AI_Service(AI_User_Repo ai_userRepo,
                      AI_Answer_Repo ai_answerRepo,
                      AI_Question_Repo ai_questionRepo)
    {

        this.ai_userRepo = ai_userRepo;
        this.ai_answerRepo = ai_answerRepo;
        this.ai_questionRepo = ai_questionRepo;
    }

    public AI_QuestionDto chatQuestion(AI_QuestionDto ai_questionDto) {

        AI_Question ai_question = ai_questionDto.toQuestionEntity(ai_questionDto);

        AI_Question saveQuestion = ai_questionRepo.save(ai_question);

        AI_QuestionDto questionDto = ai_questionDto.toQuestionDto(saveQuestion);

        return questionDto;
    }

    // 529p 참고해서, Dto -> Entity (저장, 유효성 검사 수행) -> Dto 전환 후 return 하는 거 수행.

    public AI_AnswerDto chatAnswer(AI_AnswerDto ai_answerDto) { // 529p

        AI_Answer ai_answer = ai_answerDto.toAnswerEntity(ai_answerDto);

        AI_Answer saveAnswer = ai_answerRepo.save(ai_answer);

        AI_AnswerDto aiAnswerDto = ai_answerDto.toAnswerDto(saveAnswer);

        return aiAnswerDto;
    }

}