package cornucopia.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import cornucopia.dao.UserDao;
import cornucopia.entity.RoleEntity;
import cornucopia.entity.UserEntity;
import cornucopia.model.ApplicantViewModel;
import cornucopia.model.IdNameModel;
import cornucopia.model.TransferViewModel;
import cornucopia.model.UserModPwdViewModel;
import cornucopia.model.UserSearchViewModel;
import cornucopia.util.MyBatisHelper;
import cornucopia.util.PagingParameters;

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

	public UserEntity isLogin(String un, String pwd) {
		UserEntity userEntity = this.getUserEntity(un, pwd);
		return userEntity;
	}

	public int updateLastLoginTime(int id) {
		return userdao.updateLastLoginTime(id);
	}

	public List<UserEntity> getUsersByPage(PagingParameters pp,UserSearchViewModel urvm) {
		return userdao.getUsersByPage(pp,urvm);
	}

	public List<UserEntity> getUsersByRoleId(PagingParameters pp,int roleId,String userName) {
		return userdao.getUsersByRoleId(pp,roleId,userName);
	}

	public UserEntity getUser(int id) {
		return userdao.getUser(id);
	}

	public TransferViewModel getUserRolesTransfer(int id) {
		List<Integer> roleIds = userdao.getUserRoles(id);
		List<RoleEntity> roleEntities = RoleService.getInstance().getAllRoles();

		List<RoleEntity> left = roleEntities.stream().filter((RoleEntity r) -> !roleIds.contains(r.getId()))
				.collect(Collectors.toList());
		List<IdNameModel> leftIdNameModel = new ArrayList<IdNameModel>();
		for (RoleEntity roleEntity : left) {
			IdNameModel idNameModel = new IdNameModel();
			idNameModel.setId(roleEntity.getId());
			idNameModel.setName(roleEntity.getName());
			leftIdNameModel.add(idNameModel);
		}
		List<RoleEntity> right = roleEntities.stream().filter((RoleEntity r) -> roleIds.contains(r.getId()))
				.collect(Collectors.toList());
		List<IdNameModel> rightIdNameModel = new ArrayList<IdNameModel>();
		for (RoleEntity roleEntity : right) {
			IdNameModel idNameModel = new IdNameModel();
			idNameModel.setId(roleEntity.getId());
			idNameModel.setName(roleEntity.getName());
			rightIdNameModel.add(idNameModel);
		}
		TransferViewModel transferViewModel = new TransferViewModel();
		transferViewModel.setLeft(leftIdNameModel);
		transferViewModel.setRight(rightIdNameModel);
		return transferViewModel;
	}

	public boolean updateUserRoles(int id, int[] roles) {
		userdao.deleteUserRoles(id);
		for (int roleId : roles) {
			userdao.insertUserRole(id, roleId);
		}
		return true;
	}

	public int modPwd(UserModPwdViewModel umpvm) {
		return userdao.modPwd(umpvm);
	}

	public int checkPwd(UserModPwdViewModel umpvm) {
		return userdao.checkPwd(umpvm);
	}

	public ApplicantViewModel getApplicant(int id) {
		return userdao.getApplicant(id);
	}

	public int enable(int id) {
		return userdao.enable(id);
	}

	public int disable(int id) {
		return userdao.disable(id);
	}
}
