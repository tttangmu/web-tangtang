package org.wdl.hotelSysTest.sys.service;

import java.util.List;

import org.wdl.hotelTest.bean.User;

public interface UserService {

	User findByLoginNameAndPass(String loginname, String password);

	void save(User user);

	List<User> find(String searchType, String keyword, String disabled);

	User findById(int id);

	void update(User user);

}
