package com.jm.p_ai.application;

import com.jm.p_ai.AI_Model.AI_Model;
import com.jm.p_ai.domain.AI_Answer;
import com.jm.p_ai.domain.AI_QandA;
import com.jm.p_ai.domain.AI_Question;
import com.jm.p_ai.infrastructure.*;
import com.jm.p_ai.presentation.AI_AnswerDto;
import com.jm.p_ai.presentation.AI_QandADto;
import com.jm.p_ai.presentation.AI_QuestionDto;
import com.jm.p_ai.presentation.AI_Training_QandADto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Service
public class AI_Service {

    private AI_Model ai_model;  // 2024/07/17
    private AI_User_Repo ai_userRepo;
    private AI_Answer_Repo ai_answerRepo;
    private AI_Question_Repo ai_questionRepo;
    private AI_QandA_Repo ai_QandA_repo; // 2024/12/03 추가
    private AI_Training_QandA_Repo ai_training_qandA_repo; // 2025/02/04 추가

    private final RestTemplate restTemplate;

    @Autowired
    public AI_Service(AI_Model ai_model, // 2024/07/17 추가
                      AI_User_Repo ai_userRepo,
                      AI_Answer_Repo ai_answerRepo,
                      AI_Question_Repo ai_questionRepo,
                      AI_QandA_Repo ai_QandA_repo,
                      AI_Training_QandA_Repo ai_training_qandA_repo,
                      RestTemplate restTemplate)
    {
        this.ai_model = ai_model;
        this.ai_userRepo = ai_userRepo;
        this.ai_answerRepo = ai_answerRepo;
        this.ai_questionRepo = ai_questionRepo;
        this.ai_QandA_repo = ai_QandA_repo;
        this.ai_training_qandA_repo = ai_training_qandA_repo;
        this.restTemplate = restTemplate;
    }

    public AI_QuestionDto handleQuestion(AI_QuestionDto ai_questionDto, String userId) {
        // 질문을 엔티티 변환하여 데이터베이스에 저장
        //AI_Question ai_question = ai_questionDto.toQuestionEntity(ai_questionDto);

        // 엔티티를 생성. / Dto가 엔티티 변환 책임까지 가지는 것은 단일 책임 원칙에 위배된다고 판단.
        // 나중에 변환 전담 Class를 추가하든 할 것.
        AI_Question ai_question = new AI_Question(ai_questionDto.getContents(), userId);

        AI_Question savedQuestion = ai_questionRepo.save(ai_question);

        System.out.println("Saved Question: ID = " + savedQuestion.getId() + ", Contents = " + savedQuestion.getContents());

        //ai_questionDto = ai_questionDto.toQuestionDto(savedQuestion); // Dto 변환이 안 되면 없앨 것.

        // AI_QandA 생성 및 저장 / 2025/02/13 추가 / 아예 QandA에 레코드들을 저장해서 확장성 및 레코드 관리 용이하게 함.
        AI_QandA aiQandA = new AI_QandA(savedQuestion, null, userId);
        ai_QandA_repo.save(aiQandA);

        //return savedQuestion.getId();
        //return ai_questionDto.toQuestionDto(savedQuestion);
        return new AI_QuestionDto(savedQuestion.getId(), savedQuestion.getContents(), userId);

    }

    public List<AI_AnswerDto> handleAnswer(AI_QuestionDto ai_questionDto, Long questionId) {
        AI_Question question = ai_questionRepo.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Invalid question ID"));

        AI_QandA aiQandA = ai_QandA_repo.findByQuestion(question)
                .orElseThrow(() -> new RuntimeException("AI_QandA record not found for question ID: " + question.getId()));

        String userId = question.getUserId();

        List<AI_Answer> savedAnswers = getAnswersFromPython(question.getContents()).stream()
                .map(contents -> new AI_Answer(question, contents, userId))
                .map(ai_answerRepo::save)
                .collect(Collectors.toList());

        aiQandA.setAnswers(savedAnswers);
        ai_QandA_repo.save(aiQandA);

        return savedAnswers.stream()
                .map(answer -> new AI_AnswerDto(answer.getId(), answer.getContents(), answer.getQuestion().getId(), answer.getUserId()))
                .collect(Collectors.toList());
    }

    // 20224/08/28
    public List<String> getAnswersFromPython(String question) {

        //String pythonApiUrl = "http://localhost:5000/generate_answer";
        String pythonApiUrl = "http://127.0.0.1:5000/generate-answer";

        // 파이썬 API로 질문 전송
        Map<String, String> requestPayload = new HashMap<>();
        requestPayload.put("questions", question);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestPayload, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(pythonApiUrl, entity, Map.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                // 응답에서 답변 리스트를 추출
                List<String> answers = (List<String>) response.getBody().get("answers");
                return answers;
            } else {
                throw new RuntimeException("Failed to get response from Python API");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while connecting to Python API: " + e.getMessage());
        }




    }

    // 단일 책임 원칙 SRP에 따라서 DTO를 처리하기 위한 별도의 메서드 구분.

//    public List<AI_QandADto> view_QandA() {
//
//        return ai_QandA_repo.findAllAnswerWithQuestions();
//    }

    // 전체 조회
    public List<AI_QandADto> view_QandA() {

        return ai_QandA_repo.findAllAnswerWithQuestions();
    }

    // 특정 userID에 따라 조회 됨. / 2025/02/13 추가
    public List<AI_QandADto> view_QandAByUser(String userId) {

        return ai_QandA_repo.findAllAnswerWithQuestionsByUser(userId);
    }

    public List<AI_Training_QandADto> view_Training_QandA() {

        return ai_training_qandA_repo.findAllAnswerWithQuestions();
    }

    public List<AI_Question> view_Question() {

        return ai_questionRepo.findAll();
    }

    public List<AI_Answer> view_Answer() {

        return ai_answerRepo.findAll();
    }


    // ************* 이하 단위 테스트를 위한 코드 분류 ************* //

    public boolean validateQuestionLength(String question, int minLength, int maxLength) {
        if (question == null || question.isEmpty()) {
            return false;
        }

        int length = question.length();
        return length >= minLength && length <= maxLength;
    }

}