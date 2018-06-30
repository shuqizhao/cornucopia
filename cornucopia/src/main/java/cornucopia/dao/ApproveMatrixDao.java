package cornucopia.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import cornucopia.entity.ApproveMatrixEntity;

public interface ApproveMatrixDao {
	@Select("call sp_approve_matrix_all(#{condition1Id})")
	public List<ApproveMatrixEntity> getAll(@Param("condition1Id") int conditionId);

	@Insert("call sp_approve_matrix_insert(#{am.processId},#{am.processNodeId},#{am.approveCondition1Id},#{am.approveCondition2Id},#{am.approvePositionId})")
	@SelectKey(statement="Select LAST_INSERT_ID()", keyProperty="am.id", before=false, resultType=int.class)
	public int insert(@Param("am") ApproveMatrixEntity approveMatrixEntity);
	
	@Update("call sp_approve_matrix_delete(#{id})")
	public int delete(@Param("id")int id);

	@Select("call sp_approve_matrix_get_by_process_node_id(#{id})")
	public List<ApproveMatrixEntity> getByNodeId(@Param("id")int id);
}
