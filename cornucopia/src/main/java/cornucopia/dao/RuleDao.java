package cornucopia.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import cornucopia.entity.RuleConditionEntity;
import cornucopia.entity.RuleEntity;

public interface RuleDao {
	
	@Select("call sp_rule_all()")
	public List<RuleEntity> getAll();

	@Insert("call sp_rule_insert(#{rule.name},#{rule.jobId},#{rule.parentId},#{rule.type},#{rule.user})")
	@SelectKey(statement="Select LAST_INSERT_ID()", keyProperty="rule.id", before=false, resultType=int.class)
	public int insert(@Param("rule") RuleEntity ruleEntity);

	@Update("call sp_rule_disable(#{id})")
	public int disable(@Param("id")int id);
	
	@Update("call sp_rule_enable(#{id})")
	public int enable(@Param("id")int id);
	
	@Update("call sp_rule_delete(#{id})")
	public int delete(@Param("id")int id);

	@Update("call sp_rule_update(#{rule.id},#{rule.name},#{rule.jobId},#{rule.type},#{rule.user})")
	public int update(@Param("rule")RuleEntity ruleEntity);

	@Select("call sp_rule_get(#{id})")
	public RuleEntity get(@Param("id")int id);

	@Select("call sp_rule_get_children(#{ruleId})")
	public List<RuleEntity> getRuleChildren(@Param("ruleId")int ruleId);

	@Insert("call sp_rule_condition_insert(#{rc.ruleId},#{rc.boolOperation},#{rc.var1},#{rc.var1From},#{rc.var1Type},#{rc.operation},#{rc.var2},#{rc.var2From},#{rc.var2Type})")
	@SelectKey(statement="Select LAST_INSERT_ID()", keyProperty="rc.id", before=false, resultType=int.class)
	public int insertApproveCondition(@Param("rc")RuleConditionEntity ruleConditionEntity);

	@Delete("call sp_rule_condition_delete_by_rule_id(#{ruleId})")
	public int deleteCondition(@Param("ruleId")int id);

	@Select("call sp_rule_condition_get_by_rule_id(#{ruleId})")
	public List<RuleConditionEntity> getRuleConditions(@Param("ruleId")int id);
}
