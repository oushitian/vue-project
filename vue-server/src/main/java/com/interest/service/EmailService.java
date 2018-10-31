package com.interest.service;

import java.util.List;

import com.interest.model.EmailEntity;

public interface EmailService {

	public void insertEntity(EmailEntity emailEntity);

	public List<EmailEntity> emailsList();

    public void deleteEmails(List<String> groupId);
}
