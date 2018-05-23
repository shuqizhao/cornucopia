package cornucopia.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;

import cornucopia.entity.UserEntity;
import cornucopia.model.UserModPwdViewModel;
import cornucopia.util.PagingParameters;

public interface UserDao {
	@Select("call sp_user_login(#{un},#{pwd})")
	public UserEntity getUserEntity(@Param("un") String un, @Param("pwd") String pwd);

	@Select("call sp_user_get_by_page(#{pp.start},#{pp.length},#{pp.totalRows,mode=OUT,jdbcType=INTEGER})")
	@Options(statementType = StatementType.CALLABLE)
	public List<UserEntity> getUsersByPage(@Param("pp") PagingParameters pp);

	@Select("call sp_user_get(#{userId})")
	public UserEntity getUser(@Param("userId") int id);

	@Select("call sp_user_roles(#{userId})")
	public List<Integer> getUserRoles(@Param("userId") int id);

	@Delete("call sp_user_roles_del(#{userId})")
	public boolean deleteUserRoles(@Param("userId") int id);

	@Insert("call sp_user_role_insert(#{userId},#{roleId})")
	public void insertUserRole(@Param("userId") int id, @Param("roleId") int roleId);

	@Update("call sp_user_mod_pwd(#{umpvm.id},#{umpvm.pwd})")
	public int modPwd(@Param("umpvm") UserModPwdViewModel umpvm);

	@Select("call sp_user_check_pwd(#{umpvm.id},#{umpvm.pwd})")
	public int checkPwd(UserModPwdViewModel umpvm);
}