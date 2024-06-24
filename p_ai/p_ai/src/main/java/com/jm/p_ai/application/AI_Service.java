package com.jm.p_ai.application;

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
                      AI_Question_Repo ai_questionRepo) {

        this.ai_userRepo = ai_userRepo;
        this.ai_answerRepo = ai_answerRepo;
        this.ai_questionRepo = ai_questionRepo;
    }

    public AI_Question chatQuestion(AI_Question ai_question) {
        //AI_Question ai_question = new AI_Question(id, contents);
        return ai_questionRepo.save(ai_question);
    }

}