package cornucopia.service;

import java.util.List;

import cornucopia.dao.ApproveDao;
import cornucopia.entity.ApproveConditionEntity;
import cornucopia.entity.ApproveEntity;
import cornucopia.util.MyBatisHelper;

public class ApproveService {
	private static ApproveService instance = new ApproveService();

	private ApproveService() {
	}

	public static ApproveService getInstance() {
		return instance;
	}

	ApproveDao approvedao = MyBatisHelper.getMapper(ApproveDao.class);
	
	public List<ApproveEntity> getAll() {
		return approvedao.getAll();
	}

	public int exists(String roleName) {
		return approvedao.exists(roleName);
	}

	public int insert(ApproveEntity approveEntity) {
		 return approvedao.insert(approveEntity);
	}
	
	public int insertApproveCondition(ApproveConditionEntity approveEntity) {
		 return approvedao.insertApproveCondition(approveEntity);
	}

	public int disable(int id) {
		return approvedao.disable(id);
	}
	
	public int enable(int id) {
		return approvedao.enable(id);
	}
	
	public int delete(int id) {
		return approvedao.delete(id);
	}

	public void insertApproveConditions(ApproveConditionEntity[] approveConditions) {
		for (ApproveConditionEntity fp : approveConditions) {
			insertApproveCondition(fp);
		}
	}
}
