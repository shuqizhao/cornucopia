package cornucopia.service;

import org.apache.ibatis.session.SqlSession;

import cornucopia.dao.UserDao;
import cornucopia.model.UserEntity;
import cornucopia.util.MyBatisHelper;

public class UserService {
	SqlSession session = MyBatisHelper.getSession().openSession();

	public UserEntity getOne(String un, String pwd) {
		 UserDao userdao = session.getMapper(UserDao.class);
		return userdao.getUserEntity(un, pwd);
	}

	public boolean isLogin(String un, String pwd) {
		UserEntity userEntity = this.getOne(un, pwd);
		return userEntity != null;
	}
}
