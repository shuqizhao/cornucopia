package cornucopia.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import cornucopia.entity.ProcessInstDiagramEntity;
import cornucopia.model.ProcessInstAuthViewModel;

public interface ProcessInstDiagramDao {
	@Insert("call sp_process_inst_diagram_insert(#{pi.name},#{pi.processId},#{pi.processDataId},#{pi.userId},#{pi.userName},#{pi.isCurrent},#{pi.levelCount})")
	@SelectKey(statement="Select LAST_INSERT_ID()", keyProperty="pi.id", before=false, resultType=int.class)
	public int insert(@Param("pi") ProcessInstDiagramEntity processInstDiagramEntity);
	
	@Delete("call sp_process_inst_diagram_delete(#{processDataId})")
	public int delete(@Param("processDataId") int processDataId);
	
	@Select("call sp_process_inst_diagram_all(#{processDataId})")
	public List<ProcessInstDiagramEntity> getAll(@Param("processDataId") int processDataId);

	@Select("call sp_process_inst_auth(#{processDataId},#{userId})")
	public List<ProcessInstAuthViewModel> getProcessInstAuth(@Param("processDataId")int processDataId,@Param("userId")int userId);

	@Update("call sp_process_inst_update_current(#{pdId},#{levelCount},#{status})")
	public int updateCurrent(@Param("pdId")int processDataId,@Param("levelCount") int levelCount,@Param("status") int status);

	@Select("call sp_process_inst_get_id(#{processDataId},#{userId})")
	public int getProcessInstDiagramId(@Param("processDataId")int processDataId,@Param("userId")int userId);
}
