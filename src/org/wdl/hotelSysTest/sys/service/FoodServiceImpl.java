package org.wdl.hotelSysTest.sys.service;

import java.util.List;

import org.wdl.hotelSysTest.sys.dao.FoodDao;
import org.wdl.hotelSysTest.sys.dao.FoodDaoImpl;
import org.wdl.hotelTest.bean.Food;

public class FoodServiceImpl implements FoodService {

	private FoodDao foodDao = new FoodDaoImpl();
	@Override
	public List<Food> find(String keyword, String foodTypeId) {
		return foodDao.find(keyword,foodTypeId);
	}
	@Override
	public Food findById(int id) {
		return foodDao.findById(id);
	}
	@Override
	public void update(Food food) {
		foodDao.update(food);
	}
	@Override
	public Food findByFoodName(String foodName) {
		return foodDao.findByFoodName(foodName);
	}
	@Override
	public void save(Food food) {
		foodDao.save(food);
	}

}
