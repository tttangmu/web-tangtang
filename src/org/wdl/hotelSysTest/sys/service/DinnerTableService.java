package org.wdl.hotelSysTest.sys.service;

import java.util.List;

import org.wdl.hotelTest.bean.DinnerTable;


public interface DinnerTableService {

	List<DinnerTable> find(String keyword, String tableStatus, String disabled);

	DinnerTable findById(int id);

	void update(DinnerTable dinnerTable);

	DinnerTable findByTableName(String tableName);

	void save(DinnerTable table);

}
