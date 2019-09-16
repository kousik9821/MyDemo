package com.example.dao;
import com.example.model.UserInfo;
public interface IUserInfoDAO {
	UserInfo getActiveUser(String userName);
}