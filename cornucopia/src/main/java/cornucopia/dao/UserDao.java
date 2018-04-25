package cornucopia.dao;


import org.apache.ibatis.annotations.Select;

import cornucopia.model.UserEntity;

public interface UserDao {
	@Select("select * from user where name=#{un} and pwd={pwd}")
    public UserEntity getUserEntity(String un, String pwd);
}