package com.cos.models;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
	
	private ResponseData responseData = new ResponseData();	//DB와 상관없음.
	private int id;
	private int userId;
	private int boardId;
	private String content;
	private Timestamp createDate;
	private User user = new User(); //DB와 상관없음.
	
}
