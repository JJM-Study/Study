package com.jm.p_ai.presentation;

import com.jm.p_ai.application.AI_Service;
import com.jm.p_ai.domain.AI_Answer;
import com.jm.p_ai.domain.AI_Question;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AI_Controller {

    private AI_Service ai_service;

    @Autowired
    public AI_Controller(AI_Service ai_service) {
        this.ai_service = ai_service;
    }

    @GetMapping("/chat")
    public String chatView(Model model)
    {
        List<AI_Question> ai_question = ai_service.view_Question();
        List<AI_Answer> ai_answers = ai_service.view_Answer();

        model.addAttribute("question", ai_question);
        model.addAttribute("answer", ai_answers);

        return "Chat";
    }

    @PostMapping("/question")
    public String question(@RequestBody AI_QuestionDto aiQuestionDto) {

        ai_service.chatQuestion(aiQuestionDto);
        return "redirect:/chat";
    }

    @PostMapping("/answer")
        public String answer(@RequestBody AI_AnswerDto aianswer) {

        ai_service.chatAnswer(aianswer);
        return "redirect:/chat";
    }

}
