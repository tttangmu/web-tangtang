package org.wdl.hotelAppTest.dao;

import java.util.List;

import org.wdl.hotelTest.bean.DinnerTable;

public interface DinnerTableDao {

	List<DinnerTable> findDinnerTables(String tableStatus, String tableName);

	DinnerTable findById(int id);

	void update(DinnerTable dinnerTable);

}
