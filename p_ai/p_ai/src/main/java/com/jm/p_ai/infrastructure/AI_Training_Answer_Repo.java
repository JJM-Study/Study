package com.jm.p_ai.infrastructure;

import com.jm.p_ai.domain.AI_Training_Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AI_Training_Answer_Repo extends JpaRepository<AI_Training_Answer, Long> {

}
