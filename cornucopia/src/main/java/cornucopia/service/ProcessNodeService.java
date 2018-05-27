package cornucopia.service;

import java.util.List;

import cornucopia.dao.ProcessNodeDao;
import cornucopia.entity.ProcessNodeEntity;
import cornucopia.util.MyBatisHelper;

public class ProcessNodeService {
	private static ProcessNodeService instance = new ProcessNodeService();

	private ProcessNodeService() {
	}

	public static ProcessNodeService getInstance() {
		return instance;
	}

	ProcessNodeDao processNodeDao = MyBatisHelper.getMapper(ProcessNodeDao.class);
	
	public List<ProcessNodeEntity> getAll(int processId) {
		return processNodeDao.getAll(processId);
	}

	public int exists(String nodeName,int processId) {
		return processNodeDao.exists(nodeName,processId);
	}

	public int insert(ProcessNodeEntity processNodeEntity) {
		 return processNodeDao.insert(processNodeEntity);
	}

	public int disable(int id) {
		return processNodeDao.disable(id);
	}
	
	public int enable(int id) {
		return processNodeDao.enable(id);
	}
	
	public int delete(int id) {
		return processNodeDao.delete(id);
	}
}
