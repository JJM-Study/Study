

package com.example.board.HN.HN_repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.board.HN.HN_model.HN_BoardEntity;

public interface HN_BoardRepository extends JpaRepository<HN_BoardEntity, Long> {

	List<HN_BoardEntity> findByIsDeletedNot(String del);
	
}
