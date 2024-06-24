package com.jm.p_ai.infrastructure;

import com.jm.p_ai.domain.AI_Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AI_Question_Repo extends JpaRepository<AI_Question, Long> {

}
