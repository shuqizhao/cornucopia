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

	@Select("call sp_process_all_enable()")
	public List<ProcessEntity> getAllEnableProcess();

	@Select("call sp_process_exists(#{id},#{processName},#{pre})")
	public int exists(@Param("id") int id,@Param("processName") String processName,@Param("pre") String pre);

	@Insert("call sp_process_insert(#{process.name},#{process.icon},#{process.orderNum},#{process.url},#{process.pre},#{process.createBy},#{process.categoryId})")
	@SelectKey(statement="Select LAST_INSERT_ID()", keyProperty="process.id", before=false, resultType=int.class)
	public int insert(@Param("process") ProcessEntity processEntity);

	@Update("call sp_process_disable(#{id})")
	public int disable(@Param("id")int id);
	
	@Update("call sp_process_enable(#{id})")
	public int enable(@Param("id")int id);
	
	@Update("call sp_process_delete(#{id})")
	public int delete(@Param("id")int id);

	@Select("call sp_process_get(#{processId})")
	public ProcessEntity get(@Param("processId")String processId);
}
