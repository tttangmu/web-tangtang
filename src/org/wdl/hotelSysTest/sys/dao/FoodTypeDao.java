package org.wdl.hotelSysTest.sys.dao;

import java.util.List;

import org.wdl.hotelTest.bean.FoodType;


public interface FoodTypeDao {

	List<FoodType> find(String keyword, String disabled);

	FoodType findByFoodName(String foodTypeName);

	void save(FoodType foodType2);

	FoodType findById(int id);

	void update(FoodType foodType2);

}
