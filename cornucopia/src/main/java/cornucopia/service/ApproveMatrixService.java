package cornucopia.service;

import java.util.List;

import cornucopia.dao.ApproveMatrixDao;
import cornucopia.entity.ApproveMatrixEntity;
import cornucopia.util.MyBatisHelper;

public class ApproveMatrixService {
	private static ApproveMatrixService instance = new ApproveMatrixService();

	private ApproveMatrixService() {
	}

	public static ApproveMatrixService getInstance() {
		return instance;
	}

	ApproveMatrixDao approveMatrixDao = MyBatisHelper.getMapper(ApproveMatrixDao.class);

	public List<ApproveMatrixEntity> getAll(int conditionId) {
		return approveMatrixDao.getAll(conditionId);
	}

	public int insert(ApproveMatrixEntity approveMatrixEntity) {
		return approveMatrixDao.insert(approveMatrixEntity);
	}

	public int delete(int id) {
		return approveMatrixDao.delete(id);
	}
	
	public List<ApproveMatrixEntity> getByNodeId(int id) {
		return approveMatrixDao.getByNodeId(id);
	}
}
