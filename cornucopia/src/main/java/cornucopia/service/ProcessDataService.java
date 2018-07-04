package cornucopia.service;

import java.util.List;

import cornucopia.dao.ProcessDataDao;
import cornucopia.entity.ProcessDataEntity;
import cornucopia.util.MyBatisHelper;
import cornucopia.util.PagingParameters;
import cornucopia.util.XmlUtil;

public class ProcessDataService {
	private static ProcessDataService instance = new ProcessDataService();

	private ProcessDataService() {
	}

	public static ProcessDataService getInstance() {
		return instance;
	}

	ProcessDataDao processDataDao = MyBatisHelper.getMapper(ProcessDataDao.class);

	public int insert(ProcessDataEntity processDataEntity) {
		 processDataDao.insert(processDataEntity);
		return ProcessDataHistoryService.getInstance().insert(processDataEntity);
	}

	public List<ProcessDataEntity> launchedList(PagingParameters pp, int userId) {
		return processDataDao.launchedList(pp, userId);
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
		String bizDataXmlStr = processDataEntity.getBizData();
		String bizDataJsonStr = XmlUtil.toJSONString(bizDataXmlStr);
		return bizDataJsonStr;
	}

	public int update(ProcessDataEntity processDataEntity) {
		ProcessDataHistoryService.getInstance().insert(processDataEntity);
		return processDataDao.update(processDataEntity);
	}

	public ProcessDataEntity getByFormCode(String formCode) {
		return processDataDao.getByFormCode(formCode);
	}
}
