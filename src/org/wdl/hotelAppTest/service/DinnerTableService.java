package org.wdl.hotelAppTest.service;

import java.util.List;

import org.wdl.hotelTest.bean.DinnerTable;

public interface DinnerTableService {


	List<DinnerTable> findDinnerTables(String parseInt, String tableName);

	DinnerTable findById(int id);

	void update(DinnerTable dinnerTable);

}
