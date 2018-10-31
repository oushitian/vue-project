package com.interest.service;

import java.util.List;

import com.interest.model.UserEntity;

public interface UserService {
	public void insert(UserEntity userEntity);

	public void del(UserEntity userEntity);

	/**
	 * 通过登录名得到用户信息
	 * @param loginName
	 * @return
	 */
	public UserEntity getUserEntityByLoginName(String loginName);

	/**
	 * 获取user列表
	 * @param loginName
	 * @return
	 */
	public List<UserEntity> usersList(String loginName);

	/**
	 * 新建用户信息
	 * @param userEntity
	 */
	public void insertUser(UserEntity userEntity);

	/**
	 * 更新用户信息
	 * @param userEntity
	 */
	public void updateUser(UserEntity userEntity);

	/**
	 * 删除用户信息
	 * @param groupId
	 */
	public void deleteUsers(List<String> groupId);

	public void updateUsertype(UserEntity userEntity);
}
