package cornucopia.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;

import cornucopia.entity.RoleEntity;
import cornucopia.util.PagingParameters;

public interface RoleDao {
	@Select("call sp_role_get_by_page(#{pp.start},#{pp.length},#{pp.totalRows,mode=OUT,jdbcType=INTEGER})")
	@Options(statementType = StatementType.CALLABLE)
	public List<RoleEntity> getRolesByPage(@Param("pp") PagingParameters pp);

	@Select("call sp_role_all()")
	public List<RoleEntity> getAllRoles();

	@Select("call sp_role_exists(#{roleName})")
	public int exists(@Param("roleName") String roleName);

	@Insert("call sp_role_insert(#{role.name})")
//	@Options(useGeneratedKeys = true, keyProperty = "role.id",statementType = StatementType.CALLABLE)
	@SelectKey(statement="Select LAST_INSERT_ID()", keyProperty="role.id", before=false, resultType=int.class)
	public int insert(@Param("role") RoleEntity roleEntity);

	@Insert("call sp_role_insert_user(#{userId},#{roleId})")
	public int insertUser(@Param("userId") int userId,@Param("roleId") int roleId);

	@Update("call sp_role_disable(#{id})")
	public int disable(@Param("id")int id);
	
	@Update("call sp_role_enable(#{id})")
	public int enable(@Param("id")int id);
	
	@Update("call sp_role_delete(#{id})")
	public int delete(@Param("id")int id);

	@Delete("call sp_role_delete_user(#{userId},#{roleId})")
	public int deleteUser(@Param("userId") int userId,@Param("roleId") int roleId);

	@Select("call sp_role_get(#{id})")
	public RoleEntity get(@Param("id")int id);

	@Select("call sp_role_get_user_ids_by_role_id(#{id})")
	public List<Integer> getUserIdsByRoleId(@Param("id")String id);
}
