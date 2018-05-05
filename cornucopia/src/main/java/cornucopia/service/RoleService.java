package cornucopia.service;

import java.util.List;

import cornucopia.dao.RoleDao;
import cornucopia.entity.RoleEntity;
import cornucopia.util.MyBatisHelper;

public class RoleService {
	private static RoleService instance = new RoleService();

	private RoleService() {
	}

	public static RoleService getInstance() {
		return instance;
	}

	RoleDao roledao = MyBatisHelper.getMapper(RoleDao.class);

	public List<RoleEntity> getRolesByPage(int start, int length) {
		return roledao.getRolesByPage(start, length);
	}
	
	public List<RoleEntity> getAllRoles() {
		return roledao.getAllRoles();
	}
}
