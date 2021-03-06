package cornucopia.service;

import java.util.List;

import cornucopia.dao.RoleDao;
import cornucopia.entity.RoleEntity;
import cornucopia.model.RoleAddUserViewModel;
import cornucopia.util.MyBatisHelper;
import cornucopia.util.PagingParameters;

public class RoleService {
	private static RoleService instance = new RoleService();

	private RoleService() {
	}

	public static RoleService getInstance() {
		return instance;
	}

	RoleDao roledao = MyBatisHelper.getMapper(RoleDao.class);

	public List<RoleEntity> getRolesByPage(PagingParameters pp) {
		return roledao.getRolesByPage(pp);
	}

	public List<RoleEntity> getAllRoles() {
		return roledao.getAllRoles();
	}

	public int exists(String roleName) {
		return roledao.exists(roleName);
	}

	public int insert(RoleEntity roleEntity) {
		return roledao.insert(roleEntity);
	}

	public int insertUser(RoleAddUserViewModel rauvm) {
		for (int userId : rauvm.getUserIds()) {
			roledao.insertUser(userId, rauvm.getRoleId());
		}
		return 1;
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

	public int deleteUsers(RoleAddUserViewModel rauvm) {
		for (int userId : rauvm.getUserIds()) {
			roledao.deleteUser(userId, rauvm.getRoleId());
		}
		return 1;
	}

	public RoleEntity get(int id) {
		return roledao.get(id);
	}

	public List<Integer> getUserIdsByRoleId(String id) {
		return roledao.getUserIdsByRoleId(id);
	}
}
