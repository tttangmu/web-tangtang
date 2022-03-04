package org.wdl.hotelAppTest.service;

import java.util.List;

import org.wdl.hotelAppTest.dao.FoodTypeDao;
import org.wdl.hotelAppTest.dao.FoodTypeDaoImpl;
import org.wdl.hotelTest.bean.FoodType;

public class FoodTypeServiceImpl implements FoodTypeService {

	private FoodTypeDao foodTypeDao = new FoodTypeDaoImpl();
	@Override
	public List<FoodType> findAll() {
		return foodTypeDao.findAll();
	}

}
