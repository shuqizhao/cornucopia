package cornucopia.service;

import java.util.List;

import cornucopia.dao.ProcessDiagramDao;
import cornucopia.entity.ProcessDiagramEntity;
import cornucopia.util.MyBatisHelper;

public class ProcessDiagramService {
	private static ProcessDiagramService instance = new ProcessDiagramService();

	private ProcessDiagramService() {
	}

	public static ProcessDiagramService getInstance() {
		return instance;
	}

	ProcessDiagramDao processDiagramDao = MyBatisHelper.getMapper(ProcessDiagramDao.class);
	
	public List<ProcessDiagramEntity> getAll(int processId) {
		return processDiagramDao.getAll(processId);
	}

	public int exists(String nodeName,int processId) {
		return processDiagramDao.exists(nodeName,processId);
	}

	public int insert(ProcessDiagramEntity processDiagramEntity) {
		 return processDiagramDao.insert(processDiagramEntity);
	}

	public int disable(int id) {
		return processDiagramDao.disable(id);
	}
	
	public int enable(int id) {
		return processDiagramDao.enable(id);
	}
	
	public int delete(int id) {
		return processDiagramDao.delete(id);
	}
}
