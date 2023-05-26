package com.pabitra.online_shopping.service;

import com.pabitra.online_shopping.entity.UsersEntity;

public interface UsersService {
	public int register(UsersEntity entity);
	public UsersEntity login(String phone, String password);
	public boolean isDuplicateUser(String phone);
}
