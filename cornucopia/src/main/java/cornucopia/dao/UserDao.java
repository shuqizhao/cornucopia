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
import cornucopia.model.ApplicantViewModel;
import cornucopia.model.UserModPwdViewModel;
import cornucopia.model.UserSearchViewModel;
import cornucopia.util.PagingParameters;

public interface UserDao {
	@Select("call sp_user_login(#{un},#{pwd})")
	public UserEntity getUserEntity(@Param("un") String un, @Param("pwd") String pwd);

	@Select("call sp_user_get_by_page(#{pp.start},#{pp.length},#{usvm.orgId},#{usvm.name},#{pp.totalRows,mode=OUT,jdbcType=INTEGER})")
	@Options(statementType = StatementType.CALLABLE)
	public List<UserEntity> getUsersByPage(@Param("pp") PagingParameters pp,@Param("usvm")UserSearchViewModel usvm);

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

	@Select("call sp_user_check_pwd(#{umpvm.id},#{umpvm.oldPwd})")
	public int checkPwd(@Param("umpvm") UserModPwdViewModel umpvm);

	@Select("call usp_user_get_applicant(#{id})")
	public ApplicantViewModel getApplicant(@Param("id")int id);
}