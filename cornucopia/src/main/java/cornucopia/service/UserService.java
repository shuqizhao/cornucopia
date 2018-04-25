package cornucopia.service;

import cornucopia.dao.UserDao;
import cornucopia.model.UserEntity;
import cornucopia.util.MyBatisHelper;

public class UserService {

	public UserEntity getOne(String un, String pwd) {
		UserDao userdao = MyBatisHelper.getMapper(UserDao.class);
		return userdao.getUserEntity(un, pwd);
	}

	public boolean isLogin(String un, String pwd) {
		UserEntity userEntity = this.getOne(un, pwd);
		return userEntity != null;
	}
}
