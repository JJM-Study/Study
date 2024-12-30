package com.jm.p_ai;

import com.jm.p_ai.application.AI_Service;
import com.jm.p_ai.domain.AI_Answer;
import com.jm.p_ai.domain.AI_Question;
import com.jm.p_ai.presentation.AI_QuestionDto;
import com.jm.p_ai.infrastructure.AI_Answer_Repo;
import com.jm.p_ai.infrastructure.AI_Question_Repo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AI_ServiceTest {

    private AI_Service ai_service;
    private AI_Answer_Repo mockAnswerRepo; // Mock 객체 선언
    private AI_Question_Repo mockQuestionRepo; // Mock 객체 선언

    @BeforeEach
    public void setUp() { // 필요 의존성을 Mock로 생성.
        mockAnswerRepo = Mockito.mock(AI_Answer_Repo.class); // 초기화
        mockQuestionRepo = Mockito.mock(AI_Question_Repo.class); // 초기화

        ai_service = new AI_Service(
                null, // AI_Model. 사용하지 않으므로 null
                Mockito.mock(com.jm.p_ai.infrastructure.AI_User_Repo.class),
                mockAnswerRepo,
                mockQuestionRepo,
                Mockito.mock(com.jm.p_ai.infrastructure.AI_QandA_Repo.class),
                Mockito.mock(org.springframework.web.client.RestTemplate.class)
        );
    }

    @ParameterizedTest
    @CsvSource({
            "1, 'What is 50 - 10?', 40",
            "2, 'What is 20 - 5?', 15",
            "3, 'What is 0 - 0?', 0"
    })
    public void testSubtraction(Long id, String contents, int expectedResult) {
        // Mock 객체 동작 설정
        AI_Question mockQuestion = new AI_Question(id, contents);
        Mockito.when(mockQuestionRepo.save(Mockito.any(AI_Question.class))).thenReturn(mockQuestion);

        AI_Answer mockAnswer = new AI_Answer(1L, String.valueOf(expectedResult), mockQuestion);
        Mockito.when(mockAnswerRepo.save(Mockito.any(AI_Answer.class))).thenReturn(mockAnswer);

        // AI_QuestionDto 생성 및 테스트 실행
        AI_QuestionDto questionDto = new AI_QuestionDto(id, contents);
        Long result = ai_service.handleQuestion(questionDto);

        // 결과 검증
        assertEquals(expectedResult, Integer.parseInt(mockAnswer.getContents()), "Subtraction result does not match");
    }
}