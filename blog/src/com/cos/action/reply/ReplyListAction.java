package com.cos.action.reply;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cos.action.Action;
import com.cos.models.Reply;
import com.cos.util.Script;
import com.google.gson.Gson;

import dao.ReplyDao;

public class ReplyListAction implements Action{
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader in = request.getReader();
		int commentId = Integer.parseInt(in.readLine());
		
		System.out.println("commentId : " + commentId);
		
		ReplyDao dao = new ReplyDao();
		List<Reply> replys = dao.findByCommentId(commentId);
		
		if(replys != null) {
			
			Gson gson = new Gson();
			String replyJson = gson.toJson(replys);
			System.out.println("replyJson"+replyJson);
			//Image를 전송할 때 MIME타입은?
			response.setContentType("application/json; charset=utf-8");
			
			PrintWriter out = response.getWriter();
			out.print(replyJson);
			out.flush();
			
		}else {
			Script.back(response);
		}
		
	}
}
