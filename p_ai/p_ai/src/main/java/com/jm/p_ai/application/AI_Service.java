package com.jm.p_ai.application;

import com.jm.p_ai.AI_Model.AI_Model;
import com.jm.p_ai.domain.AI_Answer;
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

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Service
public class AI_Service {

    private AI_Model ai_model;  // 2024/07/17
    private AI_User_Repo ai_userRepo;
    private AI_Answer_Repo ai_answerRepo;
    private AI_Question_Repo ai_questionRepo;

    @Autowired
    public AI_Service(AI_Model ai_model, // 2024/07/17 추가
                      AI_User_Repo ai_userRepo,
                      AI_Answer_Repo ai_answerRepo,
                      AI_Question_Repo ai_questionRepo)
    {
        this.ai_model = ai_model;
        this.ai_userRepo = ai_userRepo;
        this.ai_answerRepo = ai_answerRepo;
        this.ai_questionRepo = ai_questionRepo;
    }



    // --- 이하 소켓 통신으로 바꾸는 중 ... --- //


    public List<AI_AnswerDto> chatQuestion(AI_QuestionDto ai_questionDto) {
        // 질문을 엔티티 변환하여 데이터베이스에 저장
        AI_Question ai_question = ai_questionDto.toQuestionEntity(ai_questionDto);

        AI_Question savedQuestion = ai_questionRepo.save(ai_question);

        List<String> answerContents = ai_model.getMultipleAnswers(ai_questionDto.getContents()); // 해당 매서드 구현 후 주석 풀 것.

        // 생성된 답변들을 저장
        List<AI_Answer> savedAnswers = answerContents.stream()
                .map(contents -> {
                AI_Answer ai_answer = new AI_Answer();
                ai_answer.setQuestion(savedQuestion);
                ai_answer.setContents(contents);
                return ai_answerRepo.save(ai_answer);
        })
                .collect(Collectors.toList());

        // 답변 엔티티들을 DTO로 변환
        List<AI_AnswerDto> ai_answerDtos = savedAnswers.stream()
                .map(ai_answer -> {
                    AI_AnswerDto ai_answerDto = new AI_AnswerDto();
                    ai_answerDto.setContents(ai_answer.getContents());
                    return ai_answerDto;
                })
                .collect(Collectors.toList());

        return ai_answerDtos;
    }
//    public AI_QuestionDto chatQuestion(AI_QuestionDto ai_questionDto) {
//
//        AI_Question ai_question = ai_questionDto.toQuestionEntity(ai_questionDto);
//
//        AI_Question saveQuestion = ai_questionRepo.save(ai_question);
//
//        AI_QuestionDto questionDto = ai_questionDto.toQuestionDto(saveQuestion);
//
//        return questionDto;
//    }
//
//    // 529p 참고해서, Dto -> Entity (저장, 유효성 검사 수행) -> Dto 전환 후 return 하는 거 수행.
//
//    public AI_AnswerDto chatAnswer(AI_AnswerDto ai_answerDto) { // 529p
//
//        AI_Answer ai_answer = ai_answerDto.toAnswerEntity(ai_answerDto);
//
//        AI_Answer saveAnswer = ai_answerRepo.save(ai_answer);
//
//        AI_AnswerDto aiAnswerDto = ai_answerDto.toAnswerDto(saveAnswer);
//
//        return aiAnswerDto;
//    }

    // --------------------------------

    // 단일 책임 원칙 SRP에 따라서 DTO를 처리하기 위한 별도의 메서드 구분.

    public List<AI_Question> view_Question() {

        return ai_questionRepo.findAll();
    }

    public List<AI_Answer> view_Answer() {

        return ai_answerRepo.findAll();
    }

}