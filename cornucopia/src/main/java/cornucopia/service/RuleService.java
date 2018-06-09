package cornucopia.service;

import java.util.List;

import cornucopia.dao.RuleDao;
import cornucopia.entity.RuleEntity;
import cornucopia.util.MyBatisHelper;

public class RuleService {
	private static RuleService instance = new RuleService();

	private RuleService() {
	}

	public static RuleService getInstance() {
		return instance;
	}

	RuleDao ruledao = MyBatisHelper.getMapper(RuleDao.class);

	public List<RuleEntity> getAll() {
		return ruledao.getAll();
	}

	public int insert(RuleEntity ruleEntity) {
		 return ruledao.insert(ruleEntity);
	}
	
	public int update(RuleEntity ruleEntity) {
		 return ruledao.update(ruleEntity);
	}

	public int disable(int id) {
		return ruledao.disable(id);
	}
	
	public int enable(int id) {
		return ruledao.enable(id);
	}
	
	public int delete(int id) {
		return ruledao.delete(id);
	}
}
