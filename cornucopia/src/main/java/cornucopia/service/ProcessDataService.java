package cornucopia.service;

import java.util.List;

import cornucopia.dao.ProcessDataDao;
import cornucopia.entity.ProcessDataEntity;
import cornucopia.model.ProcessSearchViewModel;
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

	public List<ProcessDataEntity> launchedList(PagingParameters pp, int userId, ProcessSearchViewModel psvm) {
		return processDataDao.launchedList(pp, userId, psvm);
	}

	public List<ProcessDataEntity> mylaunchedCatetoryGroup(int userId) {
		return processDataDao.mylaunchedCatetoryGroup(userId);
	}

	public List<ProcessDataEntity> myTaskCatetoryGroup(int userId) {
		return processDataDao.myTaskCatetoryGroup(userId);
	}

	public List<ProcessDataEntity> myDealCatetoryGroup(int userId) {
		return processDataDao.myDealCatetoryGroup(userId);
	}

	public List<ProcessDataEntity> launchedGroup(int userId, int catetoryId) {
		return processDataDao.launchedGroup(userId, catetoryId);
	}

	public List<ProcessDataEntity> taskGroup(int userId, int catetoryId) {
		return processDataDao.taskGroup(userId, catetoryId);
	}

	public List<ProcessDataEntity> dealedGroup(int userId, int catetoryId) {
		return processDataDao.dealedGroup(userId, catetoryId);
	}

	public List<ProcessDataEntity> taskList(PagingParameters pp, int userId, ProcessSearchViewModel psvm) {
		return processDataDao.taskList(pp, userId, psvm);
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

	public List<ProcessDataEntity> dealedList(PagingParameters pp, int userId, ProcessSearchViewModel psvm) {
		return processDataDao.dealedList(pp, userId, psvm);
	}
}
