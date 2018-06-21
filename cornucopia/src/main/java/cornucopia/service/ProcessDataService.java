package cornucopia.service;

import cornucopia.dao.ProcessDataDao;
import cornucopia.entity.ProcessDataEntity;
import cornucopia.util.MyBatisHelper;

public class ProcessDataService {
	private static ProcessDataService instance = new ProcessDataService();

	private ProcessDataService() {
	}

	public static ProcessDataService getInstance() {
		return instance;
	}

	ProcessDataDao processDataDao = MyBatisHelper.getMapper(ProcessDataDao.class);
	
	public int insert(ProcessDataEntity processDataEntity) {
		return processDataDao.insert(processDataEntity);
	}
}
