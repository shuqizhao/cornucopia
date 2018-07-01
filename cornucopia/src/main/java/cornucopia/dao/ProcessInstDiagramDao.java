package cornucopia.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import cornucopia.entity.ProcessInstDiagramEntity;

public interface ProcessInstDiagramDao {
	@Insert("call sp_process_inst_diagram_insert(#{pi.name},#{pi.processId},#{pi.processDataId},#{pi.userId},#{pi.userName},#{pi.isCurrent},#{pi.levelCount})")
	@SelectKey(statement="Select LAST_INSERT_ID()", keyProperty="pi.id", before=false, resultType=int.class)
	public int insert(@Param("pi") ProcessInstDiagramEntity processInstDiagramEntity);
	
	@Delete("call sp_process_inst_diagram_delete(#{processDataId})")
	public int delete(@Param("processDataId") int processDataId);
	
	@Select("call sp_process_inst_diagram_all(#{processDataId})")
	public List<ProcessInstDiagramEntity> getAll(@Param("processDataId") int processDataId);
}
