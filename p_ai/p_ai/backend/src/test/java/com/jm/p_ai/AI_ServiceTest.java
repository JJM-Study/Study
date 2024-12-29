package com.jm.p_ai;

import com.jm.p_ai.application.AI_Service;
import com.jm.p_ai.domain.AI_QandA;
import com.jm.p_ai.domain.AI_Question;
import com.jm.p_ai.domain.AI_User;
import com.jm.p_ai.infrastructure.AI_Answer_Repo;
import com.jm.p_ai.infrastructure.AI_QandA_Repo;
import com.jm.p_ai.infrastructure.AI_Question_Repo;
import com.jm.p_ai.infrastructure.AI_User_Repo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AI_ServiceTest {

    private AI_Service ai_service;

    @BeforeEach
    public void setUp() { // 필요 의존성을 Mock로 생성.
        AI_User_Repo mockUserRepo = Mockito.mock(AI_User_Repo.class);
        AI_Answer_Repo mockAnswerRepo = Mockito.mock(AI_Answer_Repo.class);
        AI_Question_Repo mockQuestionRepo = Mockito.mock(AI_Question_Repo.class);
        AI_QandA_Repo mockQandARepo = Mockito.mock(AI_QandA_Repo.class);
        RestTemplate mockRestTemplate = Mockito.mock(RestTemplate.class);

        // Mock 객체를 AI_Service 생성자에 주입
        ai_service = new AI_Service(
          null, // AI_Model. 사용하지 않으므로 null
                mockUserRepo,
                mockAnswerRepo,
                mockQuestionRepo,
                mockQandARepo,
                mockRestTemplate
        );
    }

    @Test
    public void testServiceInitialization() {
        // 서비스 객체가 정상적으로 생성되었는지 확인
        assertNotNull(ai_service, "AI_Service should not be null");
    }
}
