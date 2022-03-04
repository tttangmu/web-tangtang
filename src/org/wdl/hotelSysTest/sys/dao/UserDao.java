package org.wdl.hotelSysTest.sys.dao;

import java.util.List;

import org.wdl.hotelTest.bean.User;

public interface UserDao {

	User findByLoginNameAndPass(String loginname, String password);

	void save(User user);

	List<User> find(String sql);

	User findById(int id);

	void update(User user);

}
