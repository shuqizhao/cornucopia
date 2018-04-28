package cornucopia.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import cornucopia.entity.UserEntity;

public interface UserDao {
	@Select("call sp_user_login(#{un},#{pwd})")
    public UserEntity getUserEntity(@Param("un") String un, @Param("pwd") String pwd);
	
	@Select("call sp_user_get_by_page(#{start},#{length})")
    public List<UserEntity> getUsersByPage(@Param("start") int start, @Param("length") int length);
}