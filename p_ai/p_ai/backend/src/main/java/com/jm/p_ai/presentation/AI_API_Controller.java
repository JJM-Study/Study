package com.jm.p_ai.presentation;

import com.jm.p_ai.application.AI_Service;
import com.jm.p_ai.config.JwtRequestFilter;
import com.jm.p_ai.config.JwtUtil;
import com.jm.p_ai.domain.AI_Answer;
import com.jm.p_ai.domain.AI_Question;
import java.util.Map;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AI_API_Controller {

    private final AI_Service ai_service;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Autowired
    public AI_API_Controller(AI_Service ai_service, JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.ai_service = ai_service;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

//    @GetMapping("all-data")
//    public HashMap<String, List<?>> getAlldata() {
//        // 질문과 답변 데이터를 모두 가져와서 하나의 Map에 담아 반환
//        List<AI_Question> questions = ai_service.view_Question();
//        List<AI_Answer> answers = ai_service.view_Answer();
//
//        HashMap<String, List<?>> response = new HashMap<>();
//        response.put("questions", questions);
//        response.put("answers", answers);
//
//        return response;
//    }

//    // 질문을 받아 파이썬 API로 보내고, 생성된 답변을 반환하는 엔드포인트 2024/12/04 주석
//    @PostMapping("/generate-answer")
//    public ResponseEntity<List<String>> generateAnswer(@RequestBody String question) {
//        // 파이썬 API로 질문을 보내고 답변을 받아옴
//        List<String> answers = ai_service.getAnswersFromPython(question);
//
//        return ResponseEntity.ok(answers);
//    }

    @GetMapping("/Question-And-Answer") // 2024/12/03 추가
    public List<AI_QandADto> AandQ() {

        return ai_service.view_QandA();
    }

    @GetMapping("/questions")
    public List<AI_Question> S_Questions() {

        return ai_service.view_Question();
    }

    @GetMapping("/answers")
    public List<AI_Answer> S_Answers() {

        return ai_service.view_Answer();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Map<String, String>> createAuthenticationToken(@RequestBody Map<String, String> authenticationRequest) {
        String username = authenticationRequest.get("username");
        // 원래는 비밀번호를 통해 사용자 인증을 해야하지만, 일단 간단히 사용자 이름만으로 인증 가능하도록 함.
        String jwt = jwtUtil.generateToken(username);
        Map<String, String> response = new HashMap<>();
        response.put("token", jwt);
        return ResponseEntity.ok(response);
    }

    // 2025/01/23 추가
    // POST의 경우 요청 본문(body)에 데이터를 포함해 전송하므로 헤더나 본문을 통해 민감한 데이터를 전달하는 데 주로 사용.
    @PostMapping("/validate-token")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authorizationHeader) {
        Map<String, String> response = new HashMap<>();

        try {
            // Authorization 헤더에서 토큰 추출
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                response.put("message", "Invalid Authorization header");
                return ResponseEntity.badRequest().body(response);
            }

            String token = authorizationHeader.substring(7);
            boolean isValid = jwtUtil.isTokenValid(token);

            if (isValid) {
                response.put("message", "Token is Valid");
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "Invalid or expired token");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } catch (Exception e) {
            response.put("message", "Token validation failed");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
