package com.example.board.HN.HN_model;


import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "t_board")
public class HN_BoardEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "board_id")
	private Long boardId;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "contents")
	private String contents;
	
	@Column(name = "hit_cnt")
	private int hitCnt;
	
	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt;
	
	@Column(name = "creator_id")
	private String creatorId;
	
	@Column(name = "updated_at")
	private Date updatedAt;
	
	@Column(name = "updater_id")
	private String updaterId;
	
	@Column(name = "is_deleted")
	private String isDeleted;
	
    }
