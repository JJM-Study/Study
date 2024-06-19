package com.jm.p_ai.infrastructure;

import com.jm.p_ai.domain.AI_Answer;
import com.jm.p_ai.domain.AI_User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;
import java.io.Serializable;

@NoRepositoryBean
public interface AI_Repository<T, ID extends Serializable> extends JpaRepository<T, ID> {
    // AI_Repository 인터페이스는 JpaRepository를 상속 받으며, T는 엔티티의 타입을,
    // ID는 엔티티의 기본 키 타입을 나타낸다.

}