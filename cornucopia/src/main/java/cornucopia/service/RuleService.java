package cornucopia.service;

import java.util.List;

import cornucopia.dao.RuleDao;
import cornucopia.entity.RuleConditionEntity;
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

	public RuleEntity get(int id) {
		return ruledao.get(id);
	}

	public List<RuleEntity> getRuleChildren(int ruleId) {
		return ruledao.getRuleChildren(ruleId);
	}

	public int deleteCondition(int id) {
		return ruledao.deleteCondition(id);
	}
	
	public void updateRuleConditions(int id, RuleConditionEntity[] ruleConditions) {
		deleteCondition(id);
		insertRuleConditions(ruleConditions);
	}

	public int insertRuleCondition(RuleConditionEntity approveEntity) {
		 return ruledao.insertApproveCondition(approveEntity);
	}
	
	public void insertRuleConditions(RuleConditionEntity[] ruleConditions) {
		for (RuleConditionEntity re : ruleConditions) {
			insertRuleCondition(re);
		}
	}

	public List<RuleConditionEntity> getRuleConditions(int id) {
		return ruledao.getRuleConditions(id);
	}
}
