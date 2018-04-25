package cornucopia.dao;


import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import cornucopia.model.UserEntity;

public interface UserDao {
	@Select("select * from sys_user where name=#{un} and pwd=#{pwd}")
    public UserEntity getUserEntity(@Param("un") String un, @Param("pwd") String pwd);
}