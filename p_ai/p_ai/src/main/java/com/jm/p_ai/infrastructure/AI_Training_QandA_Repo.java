package com.jm.p_ai.infrastructure;


import com.jm.p_ai.domain.AI_QandA;
import com.jm.p_ai.domain.AI_Training_QandA;
import com.jm.p_ai.presentation.AI_QandADto;
import com.jm.p_ai.presentation.AI_Training_QandADto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AI_Training_QandA_Repo extends JpaRepository<AI_Training_QandA, Long> {
    @Query(value = "SELECT new com.jm.p_ai.presentation.AI_Training_QandADto(q.id, q.contents, a.id, a.contents) " +
            "FROM AI_Training_Answer a LEFT JOIN a.trainingQuestion  q")
    List<AI_Training_QandADto> findAllAnswerWithQuestions();

}
