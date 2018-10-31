package com.interest.service.impl;

import java.util.List;

import com.interest.dao.EmailDao;
import com.interest.model.EmailEntity;
import com.interest.page.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interest.service.EmailService;
import com.interest.utils.DateUtil;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private EmailDao emailDao;

	@Override
	public void insertEntity(EmailEntity emailEntity) {
		emailEntity.setCreatetime(DateUtil.currentTimestamp());
		emailDao.insertEntity(emailEntity);
	}

	@Override
	@PageQuery
	public List<EmailEntity> emailsList() {
		return emailDao.emailsList();
	}

	@Override
	public void deleteEmails(List<String> groupId) {
		emailDao.deleteEmails(groupId);
	}


}
