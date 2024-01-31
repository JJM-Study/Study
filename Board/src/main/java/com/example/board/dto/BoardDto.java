package com.example.board.dto;

import lombok.Data;

@Data
public class BoardDto {
	private int boardId;
	private String title;
	private String contents;
	private int hitCnt;
	private String createdAt;
	private String creatorId;
	private String updatedAt;
	private String updaterId;	
	
}