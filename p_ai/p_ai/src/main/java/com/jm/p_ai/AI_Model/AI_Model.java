package com.jm.p_ai.AI_Model;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AI_Model {
    public List<String> getMultipleAnswers(String question) {
        // 실제 AI 모델을 호출하여 여러 개의 답변을 생성하는 로직
        // 일단 간단한 예시로 여러 개의 직접적인 응답을 반환

        return List.of(
                "Generated Answer 1 for" + question,
                "Generated Answer 2 for" + question,
                "Generated Answer 3 for" + question
        );
    }
}
