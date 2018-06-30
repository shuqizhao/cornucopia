package cornucopia.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import cornucopia.entity.ProcessNodeEntity;

public interface ProcessNodeDao {
	@Select("call sp_process_node_all(#{processId})")
	public List<ProcessNodeEntity> getAll(@Param("processId") int processId);

	@Select("call sp_process_node_exists(#{processNodeName},#{processId})")
	public int exists(@Param("processNodeName") String processNodeName,@Param("processId") int processId);

	@Insert("call sp_process_node_insert(#{processNode.name},#{processNode.processId})")
	@SelectKey(statement="Select LAST_INSERT_ID()", keyProperty="processNode.id", before=false, resultType=int.class)
	public int insert(@Param("processNode") ProcessNodeEntity processNodeEntity);

	@Update("call sp_process_node_disable(#{id})")
	public int disable(@Param("id")int id);
	
	@Update("call sp_process_node_enable(#{id})")
	public int enable(@Param("id")int id);
	
	@Update("call sp_process_node_delete(#{id})")
	public int delete(@Param("id")int id);

	@Select("call sp_process_node_get_by_name(#{processId},#{taskName})")
	public ProcessNodeEntity getByName(@Param("processId")String processId, @Param("taskName")String taskName);

	@Select("call sp_process_node_get_doa(#{processId})")
	public ProcessNodeEntity getDoaNode(@Param("processId")String processId);
}
