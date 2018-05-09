package cornucopia.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import cornucopia.entity.UserEntity;

public interface UserDao {
	@Select("call sp_user_login(#{un},#{pwd})")
	public UserEntity getUserEntity(@Param("un") String un, @Param("pwd") String pwd);

	@Select("call sp_user_get_by_page(#{start},#{length})")
	public List<UserEntity> getUsersByPage(@Param("start") int start, @Param("length") int length);

	@Select("call sp_user_get(#{userId})")
	public UserEntity getUser(@Param("userId") int id);

	@Select("call sp_user_roles(#{userId})")
	public List<Integer> getUserRoles(@Param("userId") int id);

	@Delete("call sp_user_roles_del(#{userId})")
	public boolean deleteUserRoles(@Param("userId") int id);

	@Insert("call sp_user_role_insert(#{userId},#{roleId})")
	public void insertUserRole(@Param("userId") int id,@Param("roleId") int roleId);
}