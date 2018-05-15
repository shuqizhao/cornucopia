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

	public int exists(String roleName) {
		return roledao.exists(roleName);
	}

	public void insert(RoleEntity roleEntity) {
		 roledao.insert(roleEntity);
	}

	public int disable(int id) {
		return roledao.disable(id);
	}
	
	public int enable(int id) {
		return roledao.enable(id);
	}
	
	public int delete(int id) {
		return roledao.delete(id);
	}
}
