package org.wdl.hotelAppTest.dao;

import org.wdl.hotelTest.bean.User;

public interface UserDao {

	User findByLoginNameAndPass(String loginname, String password);

	User findByLoginName(String loginName);

	void save(User user);

}
