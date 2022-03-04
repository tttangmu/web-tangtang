package org.wdl.hotelAppTest.service;

import org.wdl.hotelAppTest.dao.UserDao;
import org.wdl.hotelAppTest.dao.UserDaoImpl;
import org.wdl.hotelTest.bean.User;

public class UserServiceImpl implements UserService {
	private UserDao userDao = new UserDaoImpl();
	@Override
	public User findByLoginNameAndPass(String loginname, String password) {
		return userDao.findByLoginNameAndPass(loginname,password);
	}
	@Override
	public User findByLoginName(String loginName) {
		return userDao.findByLoginName(loginName);
	}
	@Override
	public void save(User user) {
		userDao.save(user);
	}

}
