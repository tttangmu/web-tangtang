package org.wdl.hotelSysTest.sys.service;

import java.util.List;

import org.wdl.hotelSysTest.sys.dao.FoodTypeDao;
import org.wdl.hotelSysTest.sys.dao.FoodTypeDaoImpl;
import org.wdl.hotelTest.bean.Food;
import org.wdl.hotelTest.bean.FoodType;

public class FoodTypeServiceImpl implements FoodTypeService {
	private FoodTypeDao foodTypeDao = new FoodTypeDaoImpl();

	@Override
	public List<FoodType> find(String keyword, String disabled) {
		// TODO Auto-generated method stub
		return foodTypeDao.find(keyword,disabled);
	}

	@Override
	public FoodType findByFoodName(String foodTypeName) {
		return foodTypeDao.findByFoodName(foodTypeName);
	}

	@Override
	public void save(FoodType foodType2) {
		foodTypeDao.save(foodType2);
	}

	@Override
	public FoodType findById(int id) {
		return foodTypeDao.findById(id);
	}

	@Override
	public void update(FoodType foodType2) {
		foodTypeDao.update(foodType2);
	}

}
