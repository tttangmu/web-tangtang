package org.wdl.hotelAppTest.service;

import java.util.List;

import org.wdl.hotelTest.bean.Food;

public interface FoodService {

	List<Food> findByFoodTypeId(Integer foodTypeId);

	Food findById(Integer foodId);

}
