package com.ofbusiness.task.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.ofbusiness.task.config.Constants;
import com.ofbusiness.task.entity.chatlog.MessageTbl;
import com.ofbusiness.task.model.MessageModel;
import com.ofbusiness.task.model.ServiceReplyModel;
import com.ofbusiness.task.repo.chatlog.MessageRepo;

@Service
public class ChatLogService {
	private MessageRepo messageRepo;

	@Autowired
	public ChatLogService(MessageRepo messageRepo) {
		this.messageRepo = messageRepo;
	}
	
	@Transactional
	public ServiceReplyModel addUpdateMessage(List<MessageModel> messageModels) {
		MessageTbl messageTbl;
		Optional<MessageTbl>messageOpt;
		ServiceReplyModel serviceReplyModel = new ServiceReplyModel();
		for(MessageModel messageModel:messageModels) {
			if(messageModel.getUserId()==null) {
				serviceReplyModel.setError(Constants.Messages.EMPTY_USERID);
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return serviceReplyModel;
			}
			
			if(messageModel.getMessage()==null|| messageModel.getMessage().isEmpty()) {
				serviceReplyModel.setError(Constants.Messages.EMPTY_USERID);
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return serviceReplyModel;
			}
			if(messageModel.getTimestamp()==null) {
				messageModel.setTimestamp(new Date());
			}
			
			if(messageModel.getMessageId()==0) {
				messageTbl = new MessageTbl();
			}else {
				messageOpt = messageRepo.findById(messageModel.getMessageId());
				if(messageOpt.isPresent()) {
					messageTbl = messageOpt.get();
				}else {
					serviceReplyModel.setError(Constants.Messages.RECORD_NOT_FOUND);
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return serviceReplyModel;
				}
			}
			
			BeanUtils.copyProperties(messageModel, messageTbl,"messageId");
			messageRepo.save(messageTbl);
			messageModel.setMessageId(messageTbl.getMessageId());
		}
		
		serviceReplyModel.setData(messageModels);
		return serviceReplyModel;
	}
	
	public ServiceReplyModel getMessages(String userId,int limit, int startMessageId) {
		ServiceReplyModel serviceReplyModel = new ServiceReplyModel();
		List<MessageTbl> messageTbls = messageRepo.findByUserIdAndMessageIdGreaterThan(userId, startMessageId, PageRequest.of(0, limit, Direction.DESC, "timestamp"));
		List<MessageModel> messageModels = new ArrayList<MessageModel>();
		MessageModel messageModel;
		for(MessageTbl messageTbl:messageTbls) {
			messageModel = new MessageModel();
			BeanUtils.copyProperties(messageTbl, messageModel);
			messageModels.add(messageModel);
		}
		
		serviceReplyModel.setData(messageModels);
		return serviceReplyModel;
	}
	
	@Transactional
	public ServiceReplyModel deleteMessage(int messageId) {
		Optional<MessageTbl> messageTblOpt = messageRepo.findById(messageId);
		ServiceReplyModel serviceReplyModel = new ServiceReplyModel();
		if(messageTblOpt.isPresent()) {
			messageRepo.deleteById(messageId);
		}else{
			serviceReplyModel.setError(Constants.Messages.RECORD_NOT_FOUND);
		}
		
		return serviceReplyModel;
	}
	
	@Transactional
	public void deleteAllMessagesForUser(String userId) {
		messageRepo.deleteByUserId(userId);
	}

}
