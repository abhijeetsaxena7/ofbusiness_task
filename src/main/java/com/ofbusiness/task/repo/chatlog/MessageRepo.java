package com.ofbusiness.task.repo.chatlog;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ofbusiness.task.entity.chatlog.MessageTbl;

public interface MessageRepo extends PagingAndSortingRepository<MessageTbl, Integer> {
	List<MessageTbl> findByUserIdAndMessageIdGreaterThan(String userId, int messageId, Pageable pageable);

	void deleteByUserId(String userId);
}
