package com.interest.dao;

import java.util.List;

import com.interest.model.EmailEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EmailDao {

	public void insertEntity(EmailEntity emailEntity);

	public List<EmailEntity> emailsList();

    public void deleteEmails(@Param("groupId") List<String> groupId);
}
