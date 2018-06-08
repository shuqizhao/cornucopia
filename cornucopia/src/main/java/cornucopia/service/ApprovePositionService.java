package cornucopia.service;

import java.util.List;

import cornucopia.dao.ApprovePositionDao;
import cornucopia.entity.ApprovePositionEntity;
import cornucopia.util.MyBatisHelper;

public class ApprovePositionService {
	private static ApprovePositionService instance = new ApprovePositionService();

	private ApprovePositionService() {
	}

	public static ApprovePositionService getInstance() {
		return instance;
	}

	ApprovePositionDao posdao = MyBatisHelper.getMapper(ApprovePositionDao.class);

	public List<ApprovePositionEntity> getAll(int processNodeId) {
		return posdao.getAll(processNodeId);
	}

	public int insert(ApprovePositionEntity approvePositionEntity) {
		return posdao.insert(approvePositionEntity);
	}

	public int disable(int id) {
		return posdao.disable(id);
	}

	public int enable(int id) {
		return posdao.enable(id);
	}

	public int delete(int id) {
		return posdao.delete(id);
	}

	public ApprovePositionEntity getPosition(int id) {
		return posdao.getPosition(id);
	}

	public int update(ApprovePositionEntity pos) {
		return posdao.update(pos);
	}
}