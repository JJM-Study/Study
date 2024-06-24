package com.jm.p_ai.presentation;

import com.jm.p_ai.application.AI_Service;
import com.jm.p_ai.domain.AI_Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class AI_Controller {

    private AI_Service ai_service;

    @Autowired
    public AI_Controller(AI_Service ai_service) {
        this.ai_service = ai_service;
    }

    @GetMapping("/chat")
    public String chatView()
    {
        return "Chat";
    }

    @PostMapping("/question")
    public String question(AI_Question aiQuestion) {

        ai_service.chatQuestion(aiQuestion);
        return "redirect:/chat";
    }

}