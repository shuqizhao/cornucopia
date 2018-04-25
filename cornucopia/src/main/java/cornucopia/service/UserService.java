package cornucopia.service;

import cornucopia.dao.UserDao;
import cornucopia.model.UserEntity;
import cornucopia.util.MyBatisHelper;

public class UserService {

	private static UserService instance = new UserService();

	private UserService() {
	}

	public static UserService newInstance() {
		return instance;
	}

	UserDao userdao = MyBatisHelper.getMapper(UserDao.class);

	public UserEntity getOne(String un, String pwd) {
		return userdao.getUserEntity(un, pwd);
	}

	public boolean isLogin(String un, String pwd) {
		UserEntity userEntity = this.getOne(un, pwd);
		return userEntity != null;
	}
}
