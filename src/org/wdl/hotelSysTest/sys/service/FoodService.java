package org.wdl.hotelSysTest.sys.service;

import java.util.List;

import org.wdl.hotelTest.bean.Food;


public interface FoodService {

	List<Food> find(String keyword, String foodTypeId);

	Food findById(int id);

	void update(Food food);

	Food findByFoodName(String foodName);

	void save(Food food);

}
