package com.jm.p_ai.presentation;

import com.jm.p_ai.application.AI_Service;
import com.jm.p_ai.config.JwtUtil;
import com.jm.p_ai.domain.AI_Answer;
import com.jm.p_ai.domain.AI_Question;
import jakarta.validation.constraints.Null;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AI_Controller {

    private final AI_Service ai_service;

    private final SimpMessagingTemplate simpMessagingTemplate;

    private final JwtUtil jwtUtil;

    @Autowired
    public AI_Controller(AI_Service ai_service, SimpMessagingTemplate simpMessagingTemplate, JwtUtil jwtUtil)
    {
        this.ai_service = ai_service;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.jwtUtil = jwtUtil;
    }


    @GetMapping({"/", "/health"}) // 클라우드 배포 시, 헬스 체크를 위해 인증 예외 지정.
    @ResponseBody
    public Map<String, String> healthCheck() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "OK");
        return response;
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

    @GetMapping("/loginForm")
    public String loginForm() {


        return "loginForm";

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
//    @GetMapping("/chat/info")
//    public ResponseEntity<String> chatInfo() {
//        return ResponseEntity.ok("Chat info endpoint is working");
//    }


    @MessageMapping("/question")
    //public void handleQuestion(AI_QuestionDto ai_questionDto, Principal principal) {
    public void handleQuestion(AI_QuestionDto ai_questionDto, Principal principal, SimpMessageHeaderAccessor headerAccessor) {
        // 각 답변을 클라이언트에게 전송

        //String username = ( principal != null ) ? principal.getName() : "anonymous"; 2024/08/13 주석
        if (principal == null || principal.getName() == null) {
            throw new IllegalArgumentException("User is not authenticated");
        }


        String username = principal.getName();
        System.out.println("User : " + username);

        AI_QuestionDto savedQuestionDto = ai_service.handleQuestion(ai_questionDto, username);
        Long questionId = savedQuestionDto.getId();

        List<AI_AnswerDto> ai_answerDtos = ai_service.handleAnswer(savedQuestionDto, questionId);

        //simpMessagingTemplate.convertAndSendToUser(username, "/user/queue/question", ai_questionDto);

        System.out.println("Sending confimration to user : " + username);
        this.simpMessagingTemplate.convertAndSendToUser(username, "/queue/question/confirmation", savedQuestionDto);

        ai_answerDtos.forEach(answerDto -> {
            //simpMessagingTemplate.convertAndSendToUser(principal.getName(), "/queue/answers", answerDto)
            System.out.println(answerDto.getquestionId());
            this.simpMessagingTemplate.convertAndSendToUser(username, "/queue/answers", answerDto);

            //simpMessagingTemplate.convertAndSendToUser(username, "/queue/success/question", "Question saved successfully!"); // 메세지 DB 저장 성공 여부를 클라이언트에 전달하기 위함.

        });

    }

}