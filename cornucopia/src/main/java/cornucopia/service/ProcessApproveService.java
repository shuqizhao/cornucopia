package cornucopia.service;

import cornucopia.dao.ProcessApproveDao;
import cornucopia.entity.ProcessApproveEntity;
import cornucopia.util.MyBatisHelper;

public class ProcessApproveService {
	private static ProcessApproveService instance = new ProcessApproveService();

	private ProcessApproveService() {
	}

	public static ProcessApproveService getInstance() {
		return instance;
	}

	ProcessApproveDao processApproveDao = MyBatisHelper.getMapper(ProcessApproveDao.class);

	public int insert(ProcessApproveEntity processApproveEntity) {
		return processApproveDao.insert(processApproveEntity);
	}

	public int updateCurrent(int id, int status) {
		return processApproveDao.updateCurrent(id, status);
	}

	public ProcessApproveEntity getAfterSign(int pdId, int userId) {
		return processApproveDao.getAfterSign(pdId, userId);
	}

	public ProcessApproveEntity getFirstLevel(int pdId) {
		return processApproveDao.getFirstLevel(pdId);
	}
}