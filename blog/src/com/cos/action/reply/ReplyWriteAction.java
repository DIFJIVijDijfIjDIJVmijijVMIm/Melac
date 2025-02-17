package com.cos.action.reply;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cos.action.Action;

import com.cos.models.Reply;
import com.cos.util.Script;
import com.google.gson.Gson;


import dao.ReplyDao;

public class ReplyWriteAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int commentId = Integer.parseInt(request.getParameter("commentId"));
		int userId = Integer.parseInt(request.getParameter("userId"));
		String content = request.getParameter("content");

		System.out.println("commentId : " + commentId);
		System.out.println("userId : " + userId);
		System.out.println("content : " + content);

		Reply replyForm = new Reply();
		replyForm.setCommentId(commentId);
		replyForm.setUserId(userId);
		replyForm.setContent(content);

		ReplyDao dao = new ReplyDao();
		int result = dao.save(replyForm);

		if (result == 1) {
			System.out.println("ReplyWriteAction : 성공!!");
			Reply reply = dao.findByMaxId();
			reply.getResponseData().setStatusCode(1);
			reply.getResponseData().setStatus("ok");
			reply.getResponseData().setStatusMessage("Write is completed");

			Gson gson = new Gson();
			String replyJson = gson.toJson(reply);
			System.out.println("replyJson : " + replyJson);
			System.out.println("여기 말하는건가?");

			response.setContentType("applicaion/json; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print(replyJson);
			out.flush();
		} else {
			Script.back(response);
		}

	}
}
