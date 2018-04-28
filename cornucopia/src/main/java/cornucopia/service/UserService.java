package cornucopia.service;

import java.util.List;

import cornucopia.dao.UserDao;
import cornucopia.entity.UserEntity;
import cornucopia.util.MyBatisHelper;

public class UserService {

	private static UserService instance = new UserService();

	private UserService() {
	}

	public static UserService getInstance() {
		return instance;
	}

	UserDao userdao = MyBatisHelper.getMapper(UserDao.class);

	public UserEntity getUserEntity(String un, String pwd) {
		return userdao.getUserEntity(un, pwd);
	}

	public boolean isLogin(String un, String pwd) {
		UserEntity userEntity = this.getUserEntity(un, pwd);
		return userEntity != null;
	}

	public List<UserEntity> getUsersByPage(int start, int length) {
		return userdao.getUsersByPage(start, length);
	}
}
