package cornucopia.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import cornucopia.entity.ApproveEntity;

public interface ApproveDao {
	@Select("call sp_approve_all()")
	public List<ApproveEntity> getAll();

	@Select("call sp_approve_exists(#{approveName})")
	public int exists(@Param("approveName") String approveName);

	@Insert("call sp_approve_insert(#{approve.name})")
	@SelectKey(statement="Select LAST_INSERT_ID()", keyProperty="approve.id", before=false, resultType=int.class)
	public int insert(@Param("approve") ApproveEntity approveEntity);

	@Update("call sp_approve_disable(#{id})")
	public int disable(@Param("id")int id);
	
	@Update("call sp_approve_enable(#{id})")
	public int enable(@Param("id")int id);
	
	@Update("call sp_approve_delete(#{id})")
	public int delete(@Param("id")int id);
}
