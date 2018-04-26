package cornucopia.dao;


import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import cornucopia.entity.UserEntity;

public interface UserDao {
	@Select("select * from sys_user where name=#{un} and pwd=#{pwd} and is_deleted=0 and is_enabled=1")
    public UserEntity getUserEntity(@Param("un") String un, @Param("pwd") String pwd);
}