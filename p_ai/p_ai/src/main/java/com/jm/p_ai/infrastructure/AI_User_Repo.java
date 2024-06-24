package com.jm.p_ai.infrastructure;

import com.jm.p_ai.presentation.AI_UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AI_User_Repo extends JpaRepository<AI_UserDto, Long> {

}
