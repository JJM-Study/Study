package com.jm.p_ai.presentation;

import com.jm.p_ai.application.AI_Service;
import com.jm.p_ai.domain.AI_Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class AI_Controller {

    @Autowired
    private AI_Service ai_service;

    @GetMapping("/chat")
    public String chatView(AI_Entity ai_entity) {
        return "Chat";
    }

    @PostMapping("/send")
    public String sendMessage(AI_Entity ai_entity) {
        ai_service.chatSend(ai_entity);
        return "redirect:/chat";
    }

}
