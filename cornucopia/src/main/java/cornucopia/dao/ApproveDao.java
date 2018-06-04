package cornucopia.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import cornucopia.entity.ApproveConditionEntity;
import cornucopia.entity.ApproveEntity;

public interface ApproveDao {
	@Select("call sp_approve_all(#{processNodeId})")
	public List<ApproveEntity> getAll(@Param("processNodeId")int processNodeId);

	@Select("call sp_approve_exists(#{approveName})")
	public int exists(@Param("approveName") String approveName);

	@Insert("call sp_approve_insert(#{approve.name},#{approve.processId},#{approve.processNodeId})")
	@SelectKey(statement="Select LAST_INSERT_ID()", keyProperty="approve.id", before=false, resultType=int.class)
	public int insert(@Param("approve") ApproveEntity approveEntity);

	@Update("call sp_approve_disable(#{id})")
	public int disable(@Param("id")int id);
	
	@Update("call sp_approve_enable(#{id})")
	public int enable(@Param("id")int id);
	
	@Update("call sp_approve_delete(#{id})")
	public int delete(@Param("id")int id);

	@Insert("call sp_approve_condition_insert(#{approveCondition.approveId},#{approveCondition.boolOperation},#{approveCondition.var1},#{approveCondition.var1From},#{approveCondition.var1Type},#{approveCondition.operation},#{approveCondition.var2},#{approveCondition.var2From},#{approveCondition.var2Type})")
	@SelectKey(statement="Select LAST_INSERT_ID()", keyProperty="approveCondition.id", before=false, resultType=int.class)
	public int insertApproveCondition(@Param("approveCondition") ApproveConditionEntity approveEntity);

	@Select("call sp_approve_get(#{id})")
	public ApproveEntity get(@Param("id") int id);

	@Select("call sp_approve_condition_get_by_aprove_id(#{approveId})")
	public List<ApproveConditionEntity> getConditions(@Param("approveId") int id);
}
