package cornucopia.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import cornucopia.entity.ApprovePositionEntity;

public interface ApprovePositionDao {
	
	@Select("call sp_approve_position_all(#{processNodeId})")
	public List<ApprovePositionEntity> getAll(@Param("processNodeId") int processNodeId);

	@Insert("call sp_approve_position_insert(#{pos.name},#{pos.processNodeId},#{pos.type},#{pos.rule})")
	@SelectKey(statement="Select LAST_INSERT_ID()", keyProperty="pos.id", before=false, resultType=int.class)
	public int insert(@Param("pos") ApprovePositionEntity approvePositionEntity);

	@Update("call sp_approve_position_disable(#{id})")
	public int disable(@Param("id")int id);
	
	@Update("call sp_approve_position_enable(#{id})")
	public int enable(@Param("id")int id);
	
	@Update("call sp_approve_position_delete(#{id})")
	public int delete(@Param("id")int id);

	@Select("call sp_approve_position_get(#{id})")
	public ApprovePositionEntity getPosition(@Param("id")int id);

	@Update("call sp_approve_position_update(#{pos.id},#{pos.name},#{pos.type},#{pos.rule})")
	public int update(@Param("pos") ApprovePositionEntity pos);
}
