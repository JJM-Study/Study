package com.jm.p_ai.presentation;

import com.jm.p_ai.application.AI_Service;
import com.jm.p_ai.domain.AI_Answer;
import com.jm.p_ai.domain.AI_Question;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class AI_Controller {

    private final AI_Service ai_service;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public AI_Controller(AI_Service ai_service, SimpMessagingTemplate simpMessagingTemplate)
    {
        this.ai_service = ai_service;
        this.simpMessagingTemplate = simpMessagingTemplate;
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

//    @PostMapping("/question") MessageMapping 대신 사용. (만약 http 기반 API 호출을 원할 경우 주석 해제해서 사용.)
//    public String question(@RequestBody AI_QuestionDto aiQuestionDto) {
//
//        ai_service.chatQuestion(aiQuestionDto);
//        return "redirect:/chat";
//    }

//    @PostMapping("/answer")
//        public String answer(@RequestBody AI_AnswerDto aianswer) {
//
//        ai_service.chatAnswer(aianswer);
//        return "redirect:/chat";
//    }

    @MessageMapping("/question")
    public void handleQuestion(AI_QuestionDto ai_questionDto, Principal principal) {
        // 각 답변을 클라이언트에게 전송

        List<AI_AnswerDto> ai_answerDtos = ai_service.chatQuestion(ai_questionDto);


        ai_answerDtos.forEach(answerDto ->
                simpMessagingTemplate.convertAndSendToUser(principal.getName(), "/queue/answers", answerDto)
        );

    }

}
