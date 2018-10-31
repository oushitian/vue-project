package com.interest.service.impl;

import java.util.List;

import com.interest.dao.PostCardDao;
import com.interest.dao.ReplyCardDao;
import com.interest.model.ReplyCardEntity;
import com.interest.model.view.ReplyCardModel;
import com.interest.page.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interest.service.ReplyCardService;
import com.interest.utils.DateUtil;
import com.interest.utils.SecurityAuthenUtil;

@Service
public class ReplyCardServiceImpl implements ReplyCardService {

	@Autowired
	private ReplyCardDao replyCardDao;
	
	@Autowired
	private PostCardDao postCardDao;

	@Override
	@PageQuery
	public List<ReplyCardModel> replycardList(int postcardid) {
		return replyCardDao.replycardList(postcardid);
	}

	@Override
	public void insertEntity(ReplyCardEntity replyCardEntity) {
		replyCardEntity.setUsername(SecurityAuthenUtil.getLoginName());
		replyCardEntity.setCreatetime(DateUtil.currentTimestamp());
		
		postCardDao.updateCreatetiem(replyCardEntity.getPostcardid(),replyCardEntity.getCreatetime());
		replyCardDao.insertEntity(replyCardEntity);
	}
	
//	@Override
//	public List<PostCardEntity> postcardList(int interestid, int pageSize, int start) {
//		return replyCardDao.postcardList(interestid,pageSize,start);
//	}
//
//	@Override
//	public Integer postcardSize(int interestid, int pageSize, int start) {
//		return replyCardDao.postcardSize(interestid,pageSize,start);
//	}

//
//	@Override
//	public PostCardEntity getPostcard(int id) {
//		return replyCardDao.getPostcard(id);
//	}

}
