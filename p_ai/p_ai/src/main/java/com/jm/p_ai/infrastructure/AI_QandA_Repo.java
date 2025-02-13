package com.jm.p_ai.infrastructure;


import com.jm.p_ai.domain.AI_QandA;
import com.jm.p_ai.domain.AI_Question;
import com.jm.p_ai.presentation.AI_QandADto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AI_QandA_Repo extends JpaRepository<AI_QandA, Long> {
    // 2025-02-13 추가 사용자 구분 추가.
    @Query("SELECT new com.jm.p_ai.presentation.AI_QandADto(q.id, q.contents, a.id, a.contents, qa.userId) " +
            "FROM AI_QandA qa " +
            "JOIN qa.question q " +
            "LEFT JOIN qa.answers a " +
            "WHERE qa.userId = :userId")
    List<AI_QandADto> findAllAnswerWithQuestionsByUser(@Param("userId") String userId);

    // 전체 조회
    @Query("SELECT new com.jm.p_ai.presentation.AI_QandADto(q.id, q.contents, a.id, a.contents, qa.userId) " +
            "FROM AI_QandA qa " +
            "JOIN qa.question q " +
            "LEFT JOIN qa.answers a")
    List<AI_QandADto> findAllAnswerWithQuestions();

    Optional<AI_QandA> findByQuestion(AI_Question question);

}
