package com.example.board.HN.HN_service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.board.HN.HN_model.HN_BoardEntity;
import com.example.board.HN.HN_repository.HN_BoardRepository;

import ch.qos.logback.classic.Logger;

@Service
public class HN_BoardService {
	
	@Autowired
	private HN_BoardRepository boardRepository;
	
	public List<HN_BoardEntity> getAllBoards() { 
		
		return boardRepository.findByIsDeletedNot("Y");
		
	}
	
	public HN_BoardEntity ViewPost(Long id) {
		HN_BoardEntity entity = boardRepository.findById(id).orElse(null);
		entity.setHitCnt(entity.getHitCnt() + 1);
		
		boardRepository.findById(id).orElse(null);
		
		return boardRepository.save(entity); 
	}
	
	//public HN_BoardEntity Save(String title, String contents, Date created_at, String creator_id) {
	  public HN_BoardEntity Save(HN_BoardEntity entity) {
	    if(isValid_Create(entity)) {
		  //entity.setCreatedAt(new Date());
		  entity.setCreatorId("admin");
		  entity.setCreatedAt(new Date());
		  entity.setIsDeleted("N");
		  
		  return boardRepository.save(entity);
		  
	    } else {
	    	throw new IllegalArgumentException("Invalid Data");
	    }
	}
	  
	public boolean isValid_Create(HN_BoardEntity entity) {	// 유효성 검사
		return !entity.getTitle().isEmpty() && !entity.getContents().isEmpty();
	}
	 
	 public HN_BoardEntity Update(Long boardId, String title, String contents) {
		 Optional<HN_BoardEntity> entityOptional = boardRepository.findById(boardId);
		 
		 if(entityOptional.isPresent()) {
			HN_BoardEntity entity = entityOptional.get();
			entity.setUpdaterId("admin");
			entity.setUpdatedAt(new Date());
			entity.setTitle(title);
			entity.setContents(contents);	
				
			return boardRepository.save(entity);
			
		 }
		 	
		 else {
		 	throw new IllegalArgumentException("Invaild Date");
		 }
	 }
	  
//	 public boolean isValid_Update(Long boardId, String title, String contents) {
//		 	
//		 	
//		 
//		 	return
//	 }

		
	public HN_BoardEntity delete(Long id) {
		Optional<HN_BoardEntity> entityOptional = boardRepository.findById(id);
		
		if (entityOptional.isPresent()) {
			HN_BoardEntity entity = entityOptional.get(); 
			entity.setIsDeleted("Y");
			
			return boardRepository.save(entity);
					
		} else {
			throw new IllegalArgumentException("Invaild Date");
		}
		
		
	}
}
