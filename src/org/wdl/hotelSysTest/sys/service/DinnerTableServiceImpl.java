package org.wdl.hotelSysTest.sys.service;

import java.util.List;

import org.wdl.hotelSysTest.sys.dao.DinnerTableDao;
import org.wdl.hotelSysTest.sys.dao.DinnerTableDaoImpl;
import org.wdl.hotelTest.bean.DinnerTable;

public class DinnerTableServiceImpl implements DinnerTableService {

	private DinnerTableDao dinnerTableDao = new DinnerTableDaoImpl();
	@Override
	public List<DinnerTable> find(String keyword, String tableStatus, String disabled) {
		return dinnerTableDao.find(keyword,tableStatus,disabled);
	}
	@Override
	public DinnerTable findById(int id) {
		return dinnerTableDao.findById(id);
	}
	@Override
	public void update(DinnerTable dinnerTable) {
		dinnerTableDao.update(dinnerTable);
	}
	@Override
	public DinnerTable findByTableName(String tableName) {
		return dinnerTableDao.findByTableName(tableName);
	}
	@Override
	public void save(DinnerTable table) {
		dinnerTableDao.save(table);
	}

}
