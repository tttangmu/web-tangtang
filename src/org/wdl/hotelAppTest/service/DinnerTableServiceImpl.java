package org.wdl.hotelAppTest.service;

import java.util.List;

import org.wdl.hotelAppTest.dao.DinnerTableDao;
import org.wdl.hotelAppTest.dao.DinnerTableDaoImpl;
import org.wdl.hotelTest.bean.DinnerTable;

public class DinnerTableServiceImpl implements DinnerTableService {

	private DinnerTableDao dinnerTableDao = new DinnerTableDaoImpl();
	@Override
	public List<DinnerTable> findDinnerTables(String tableStatus, String tableName) {
		return dinnerTableDao.findDinnerTables(tableStatus,tableName);
	}
	@Override
	public DinnerTable findById(int id) {
		return dinnerTableDao.findById(id);
	}
	@Override
	public void update(DinnerTable dinnerTable) {
		dinnerTableDao.update(dinnerTable);
	}	
}
