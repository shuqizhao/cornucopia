package cornucopia.service;

import java.util.List;

import cornucopia.dao.ProcessDataDao;
import cornucopia.entity.ProcessDataEntity;
import cornucopia.util.MyBatisHelper;
import cornucopia.util.PagingParameters;

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

	public List<ProcessDataEntity> launchedList(PagingParameters pp,int userId) {
		return processDataDao.launchedList(pp,userId);
	}
}
