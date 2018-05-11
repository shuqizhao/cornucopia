package cornucopia.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import cornucopia.entity.RoleEntity;

public interface RoleDao {
	@Select("call sp_role_get_by_page(#{start},#{length})")
	public List<RoleEntity> getRolesByPage(@Param("start") int start, @Param("length") int length);

	@Select("call sp_role_all()")
	public List<RoleEntity> getAllRoles();

	@Select("call sp_role_exists(#{roleName})")
	public int exists(@Param("roleName") String roleName);

	@Insert("call sp_role_insert(#{role.name})")
	@Options(useGeneratedKeys = true, keyProperty = "role.id")
	public void insert(@Param("role") RoleEntity roleEntity);
}
