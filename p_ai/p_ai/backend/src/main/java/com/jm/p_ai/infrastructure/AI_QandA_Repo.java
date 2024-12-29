package com.jm.p_ai.infrastructure;


import com.jm.p_ai.domain.AI_QandA;
import com.jm.p_ai.presentation.AI_QandADto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AI_QandA_Repo extends JpaRepository<AI_QandA, Long> {
    @Query(value = "SELECT new com.jm.p_ai.presentation.AI_QandADto(q.id, q.contents, a.id, a.contents) " +
            "FROM AI_Answer a LEFT JOIN a.question q")
    List<AI_QandADto> findAllAnswerWithQuestions();
}
