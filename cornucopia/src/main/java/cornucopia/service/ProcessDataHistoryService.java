package cornucopia.service;

import cornucopia.dao.ProcessDataHistoryDao;
import cornucopia.entity.ProcessDataEntity;
import cornucopia.util.MyBatisHelper;

public class ProcessDataHistoryService {
	private static ProcessDataHistoryService instance = new ProcessDataHistoryService();

	private ProcessDataHistoryService() {
	}

	public static ProcessDataHistoryService getInstance() {
		return instance;
	}

	ProcessDataHistoryDao processDataHistoryDao = MyBatisHelper.getMapper(ProcessDataHistoryDao.class);

	public int insert(ProcessDataEntity processDataEntity) {
		return processDataHistoryDao.insert(processDataEntity);
	}

}
