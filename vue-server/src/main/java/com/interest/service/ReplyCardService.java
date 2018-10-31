package com.interest.service;

import java.util.List;

import com.interest.model.ReplyCardEntity;
import com.interest.model.view.ReplyCardModel;

public interface ReplyCardService {

//	public void insertEntity(PostCardEntity postCardEntity);
//
//	public PostCardEntity getPostcard(int id);

	public List<ReplyCardModel> replycardList(int postcardid);

	public void insertEntity(ReplyCardEntity replyCardEntity);

}
