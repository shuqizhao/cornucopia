package cornucopia.dao;


import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import cornucopia.entity.UserEntity;

public interface UserDao {
	@Select("call sp_user_login(#{un},#{pwd})")
    public UserEntity getUserEntity(@Param("un") String un, @Param("pwd") String pwd);
	
	@Select("call sp_user_get_by_page(#{un},#{pwd})")
    public UserEntity getUsersByPage(@Param("un") String un, @Param("pwd") String pwd);
}