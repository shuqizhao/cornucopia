package cornucopia.service;

import java.util.List;

import cornucopia.dao.ProcessInstDiagramDao;
import cornucopia.entity.ProcessInstDiagramEntity;
import cornucopia.model.ProcessInstAuthViewModel;
import cornucopia.util.MyBatisHelper;

public class ProcessInstDiagramService {
	private static ProcessInstDiagramService instance = new ProcessInstDiagramService();

	private ProcessInstDiagramService() {
	}

	public static ProcessInstDiagramService getInstance() {
		return instance;
	}

	ProcessInstDiagramDao processInstDiagramDao = MyBatisHelper.getMapper(ProcessInstDiagramDao.class);

	public int insert(ProcessInstDiagramEntity processInstDiagramEntity) {
		return processInstDiagramDao.insert(processInstDiagramEntity);
	}

	public int delete(int processDataId) {
		return processInstDiagramDao.delete(processDataId);
	}

	public List<ProcessInstDiagramEntity> getAll(int processDataId) {
		return processInstDiagramDao.getAll(processDataId);
	}

	public List<ProcessInstAuthViewModel> getProcessInstAuth(int processDataId,int userId) {
		return processInstDiagramDao.getProcessInstAuth(processDataId, userId);
	}
	
	public int updateCurrent(int processDataId,int levelCount) {
		return processInstDiagramDao.updateCurrent(processDataId,levelCount);
	}
}
