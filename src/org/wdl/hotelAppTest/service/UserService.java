package org.wdl.hotelAppTest.service;

import org.wdl.hotelTest.bean.User;

public interface UserService {

	User findByLoginNameAndPass(String loginname, String password);

	User findByLoginName(String loginName);

	void save(User user);

}
