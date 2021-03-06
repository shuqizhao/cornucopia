package cornucopia.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import cornucopia.entity.ProcessDiagramEntity;

public interface ProcessDiagramDao {
	@Select("call sp_process_diagram_all(#{processId})")
	public List<ProcessDiagramEntity> getAll(@Param("processId") int processId);

	@Select("call sp_process_diagram_exists(#{name},#{processId})")
	public int exists(@Param("name") String name,@Param("processId") int processId);

	@Insert("call sp_process_diagram_insert(#{pd.name},#{pd.fileId},#{pd.picFileId},#{pd.processId})")
	@SelectKey(statement="Select LAST_INSERT_ID()", keyProperty="pd.id", before=false, resultType=int.class)
	public int insert(@Param("pd") ProcessDiagramEntity processEntity);

	@Update("call sp_process_diagram_disable(#{id})")
	public int disable(@Param("id")int id);
	
	@Update("call sp_process_diagram_enable(#{id})")
	public int enable(@Param("id")int id);
	
	@Update("call sp_process_diagram_delete(#{id})")
	public int delete(@Param("id")int id);
	
	@Select("call sp_process_diagram_get(#{id})")
	public ProcessDiagramEntity get(@Param("id")int id);

	@Update("call sp_process_diagram_update(#{pd.id},#{pd.deployId},#{pd.defId},#{pd.defKey})")
	public int update(@Param("pd")ProcessDiagramEntity processDiagramEntity);

	@Select("call sp_process_diagram_get_by_process_id(#{id})")
	public ProcessDiagramEntity getByProcessId(@Param("id")String id);
}
