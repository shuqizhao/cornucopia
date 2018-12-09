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
}