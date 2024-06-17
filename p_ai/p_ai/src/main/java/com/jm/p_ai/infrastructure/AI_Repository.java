package com.jm.p_ai.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jm.p_ai.domain.AI_Entity;
import org.springframework.stereotype.Repository;

@Repository
public interface AI_Repository extends JpaRepository<AI_Entity, Long> {

}
