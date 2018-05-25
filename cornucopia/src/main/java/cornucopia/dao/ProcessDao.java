package cornucopia.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import cornucopia.entity.ProcessEntity;

public interface ProcessDao {
	@Select("call sp_process_all()")
	public List<ProcessEntity> getAllProcess();

	@Select("call sp_process_exists(#{processName})")
	public int exists(@Param("processName") String processName);

	@Insert("call sp_process_insert(#{process.name})")
	@SelectKey(statement="Select LAST_INSERT_ID()", keyProperty="process.id", before=false, resultType=int.class)
	public int insert(@Param("process") ProcessEntity processEntity);

	@Update("call sp_process_disable(#{id})")
	public int disable(@Param("id")int id);
	
	@Update("call sp_process_enable(#{id})")
	public int enable(@Param("id")int id);
	
	@Update("call sp_process_delete(#{id})")
	public int delete(@Param("id")int id);
}
