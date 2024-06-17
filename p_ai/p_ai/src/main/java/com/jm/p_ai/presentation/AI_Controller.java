package com.jm.p_ai.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class AI_Controller {

    @GetMapping("/chat")
    public String chat() {
        return "Chat";
    }
}
