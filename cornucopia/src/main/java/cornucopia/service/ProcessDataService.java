package cornucopia.service;

import java.util.List;

import cornucopia.dao.ProcessDataDao;
import cornucopia.entity.ProcessDataEntity;
import cornucopia.util.MyBatisHelper;
import cornucopia.util.PagingParameters;
import cornucopia.util.XmlExercise;

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

	public List<ProcessDataEntity> launchedList(PagingParameters pp, int userId) {
		return processDataDao.launchedList(pp, userId);
	}

	public List<ProcessDataEntity> catetoryGroup(int userId) {
		return processDataDao.catetoryGroup(userId);
	}

	public List<ProcessDataEntity> launchedGroup(int userId,int catetoryId) {
		return processDataDao.launchedGroup(userId,catetoryId);
	}

	public List<ProcessDataEntity> taskList(PagingParameters pp, int userId) {
		return processDataDao.taskList(pp, userId);
	}

	public ProcessDataEntity getByInstId(String instId) {
		return processDataDao.getByInstId(instId);
	}

	public ProcessDataEntity get(int id) {
		return processDataDao.get(id);
	}

	public String getBizData(int id) {
		ProcessDataEntity processDataEntity = get(id);
		String bizData = processDataEntity.getBizData();
		String bizDataJsonStr = XmlExercise.xml2json(bizData);
		return bizDataJsonStr;
	}

	public int update(ProcessDataEntity processDataEntity) {
		return processDataDao.update(processDataEntity);
	}

	public ProcessDataEntity getByFormCode(String formCode) {
		return processDataDao.getByFormCode(formCode);
	}

	public List<ProcessDataEntity> dealedList(PagingParameters pp, int userId) {
		return processDataDao.dealedList(pp, userId);
	}
}
