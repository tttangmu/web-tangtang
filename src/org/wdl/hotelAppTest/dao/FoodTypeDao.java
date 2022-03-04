package org.wdl.hotelAppTest.dao;

import java.util.List;

import org.wdl.hotelTest.bean.FoodType;

public interface FoodTypeDao {

	List<FoodType> findAll();

}
