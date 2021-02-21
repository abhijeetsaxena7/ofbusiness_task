package com.ofbusiness.task.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ofbusiness.task.config.Constants;
import com.ofbusiness.task.model.MessageModel;
import com.ofbusiness.task.model.Reply;
import com.ofbusiness.task.model.ServiceReplyModel;
import com.ofbusiness.task.service.ChatLogService;
import com.ofbusiness.task.util.GenUtil;

@RestController
@RequestMapping("/chatlogs/{user}")
public class ChatLogController {

	private ChatLogService chatlogService;
	private GenUtil genutil;

	@Autowired
	public ChatLogController(ChatLogService chatlogService, GenUtil genutil) {
		this.chatlogService = chatlogService;
		this.genutil = genutil;
	}
	
	@PostMapping(path = "", consumes = {MediaType.APPLICATION_JSON_VALUE},produces = MediaType.APPLICATION_JSON_VALUE)
	public Reply addUpdateMessageJson(@PathVariable String user,@RequestBody MessageModel messageModel) {
		return addUpdateMessage(user, messageModel);
	}
	
	@PostMapping(path = "", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},produces = MediaType.APPLICATION_JSON_VALUE)
	public Reply addUpdateMessageUrlEncodedd(@PathVariable String user, MessageModel messageModel) {
		return addUpdateMessage(user, messageModel);
	}
	
	private Reply addUpdateMessage(String user,MessageModel messageModel){
			messageModel.setUserId(user);
		ServiceReplyModel serviceReplyModel = chatlogService.addUpdateMessage(genutil.getListWrapper(messageModel));
		Reply reply = new Reply();
		if(serviceReplyModel.isError()) {
			reply.setErrorMsg(serviceReplyModel.getError().toString());
		}else {
			reply.setInfoMsg(Constants.Messages.UPDATED_SUCCESSFULLY.toString());
			List<MessageModel> messageModels = (List<MessageModel>) serviceReplyModel.getData();
			reply.setData(messageModels.get(0).getMessageId());
		}
		return reply;
	}
	
	@GetMapping("")
	public Reply getMessages(@PathVariable String user,@RequestParam(required = false) Integer limit, @RequestParam(required=false) Integer start) {
		Reply reply = new Reply();
		if(limit ==null) {
			limit =10;
		}
		if(start==null) {
			start = 0;
		}
		ServiceReplyModel serviceReplyModel = chatlogService.getMessages(user, limit , start);
		reply.setData(serviceReplyModel.getData());
		return reply;
	}
	
	@DeleteMapping("")
	public Reply deleteMessage(@PathVariable String user) {
		Reply reply = new Reply();
		chatlogService.deleteAllMessagesForUser(user);
		reply.setInfoMsg(Constants.Messages.DELETED_SUCCESSFULLY.toString());
		return reply;
	}
	
	@DeleteMapping("/{msgid}")
	public ResponseEntity<Reply> deleteMessage(@PathVariable int msgid) {
		ResponseEntity<Reply> responseEntity;
		Reply reply= new Reply();
		ServiceReplyModel serviceReplyModel = chatlogService.deleteMessage(msgid);
		if(serviceReplyModel.isError()) {
			reply.setErrorMsg(serviceReplyModel.getError().toString());
		}else {
			reply.setInfoMsg(Constants.Messages.DELETED_SUCCESSFULLY.toString());
		}
		responseEntity = new ResponseEntity<Reply>(reply,reply.isError()?HttpStatus.NO_CONTENT:HttpStatus.OK);
		return responseEntity;
	}
	

}
