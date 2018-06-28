package cornucopia.service;

import java.util.List;

import cornucopia.dao.ProcessDao;
import cornucopia.entity.ProcessEntity;
import cornucopia.util.MyBatisHelper;

public class ProcessService {
	private static ProcessService instance = new ProcessService();

	private ProcessService() {
	}

	public static ProcessService getInstance() {
		return instance;
	}

	ProcessDao processDao = MyBatisHelper.getMapper(ProcessDao.class);
	
	public List<ProcessEntity> getAllProcess() {
		return processDao.getAllProcess();
	}

	public int exists(String processName) {
		return processDao.exists(processName);
	}

	public int insert(ProcessEntity processEntity) {
		 return processDao.insert(processEntity);
	}

	public int disable(int id) {
		return processDao.disable(id);
	}
	
	public int enable(int id) {
		return processDao.enable(id);
	}
	
	public int delete(int id) {
		return processDao.delete(id);
	}

	public ProcessEntity get(String processId) {
		return processDao.get(processId);
	}
	
	public String getPre(String processId) {
		return get(processId).getPre();
	}
}
