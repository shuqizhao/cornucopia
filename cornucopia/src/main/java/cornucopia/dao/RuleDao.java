package cornucopia.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import cornucopia.entity.RuleEntity;

public interface RuleDao {
	
	@Select("call sp_rule_all()")
	public List<RuleEntity> getAll();

	@Insert("call sp_rule_insert(#{rule.name})")
	@SelectKey(statement="Select LAST_INSERT_ID()", keyProperty="rule.id", before=false, resultType=int.class)
	public int insert(@Param("rule") RuleEntity ruleEntity);

	@Update("call sp_rule_disable(#{id})")
	public int disable(@Param("id")int id);
	
	@Update("call sp_rule_enable(#{id})")
	public int enable(@Param("id")int id);
	
	@Update("call sp_rule_delete(#{id})")
	public int delete(@Param("id")int id);

	@Update("call sp_rule_update(#{rule.id},#{rule.name})")
	public int update(@Param("rule")RuleEntity ruleEntity);

	@Select("call sp_rule_get(#{id})")
	public RuleEntity get(@Param("id")int id);
}
