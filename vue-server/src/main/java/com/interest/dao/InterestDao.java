package com.interest.dao;

import java.util.List;

import com.interest.model.InterestEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface InterestDao {

	public List<InterestEntity> getInsterest(@Param("title") String title);

	public InterestEntity getInsterestById(@Param("id") int id);

    public void insertEntity(InterestEntity interestEntity);

	public void updateEntity(InterestEntity interestEntity);

	public List<InterestEntity> interestList();

	public void deleteInterests(@Param("groupId") List<String> groupId);

    public List<InterestEntity> getBanners();

	public void updateBanners(@Param("groupId") List<String> groupId,@Param("banner") int i);
}
