package org.wdl.hotelAppTest.dao;

import java.util.List;

import org.wdl.hotelTest.bean.Food;

public interface FoodDao {

	List<Food> findByFoodTypeId(Integer foodTypeId);

	Food findById(Integer foodId);

}
