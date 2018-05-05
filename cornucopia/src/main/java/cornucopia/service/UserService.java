package cornucopia.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import cornucopia.dao.UserDao;
import cornucopia.entity.RoleEntity;
import cornucopia.entity.UserEntity;
import cornucopia.model.IdNameModel;
import cornucopia.model.TransferViewModel;
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
}
