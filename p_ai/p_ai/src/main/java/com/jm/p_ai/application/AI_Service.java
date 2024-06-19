package com.jm.p_ai.application;

import com.jm.p_ai.domain.AI_Answer;
import com.jm.p_ai.infrastructure.AI_Repository;
import com.jm.p_ai.presentation.AI_Controller;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AI_Service {

    @Autowired
    private AI_Repository ai_repository;

    public AI_Answer chatAnswer(AI_Answer answer) {
        return ai_repository.save(answer);
    }

}
