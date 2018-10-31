package com.interest.service;

import java.util.List;

import com.interest.model.PostCardEntity;
import com.interest.model.view.PostCardModel;

public interface PostCardService {

	public List<PostCardModel> postcardList(String interestid);

	public void insertEntity(PostCardEntity postCardEntity);

	public PostCardModel getPostcard(int id);

	public void deletePostcards(List<String> groupId);

}
