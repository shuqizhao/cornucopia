package cornucopia.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import cornucopia.dao.MenuDao;
import cornucopia.entity.MenuEntity;
import cornucopia.model.TreeViewModel;
import cornucopia.util.MyBatisHelper;

public class MenuService {
	private static MenuService instance = new MenuService();

	private MenuService() {
	}

	public static MenuService getInstance() {
		return instance;
	}

	MenuDao menuDao = MyBatisHelper.getMapper(MenuDao.class);

	public List<MenuEntity> getAllMenus() {
		List<MenuEntity> menus = menuDao.getAllMenus();
		return menus;
	}

	public List<MenuEntity> getAllMenusAndBtns() {
		List<MenuEntity> menus = menuDao.getAllMenusAndBtns();
		return menus;
	}

	public List<TreeViewModel> getAllResourceTree() {
		List<MenuEntity> menus = getAllMenusAndBtns();
		List<TreeViewModel> rvms = buildResourceTree(0, menus);
		return rvms;
	}

	private List<TreeViewModel> buildResourceTree(int parentId, List<MenuEntity> menus) {
		List<TreeViewModel> rvms = new ArrayList<TreeViewModel>();
		List<MenuEntity> curMenus = menus.stream().filter((MenuEntity r) -> r.getParentId() == parentId)
				.collect(Collectors.toList());
		for (MenuEntity menu : curMenus) {
			TreeViewModel rvm = new TreeViewModel();
			rvm.setId(menu.getId());
			rvm.setName(menu.getName());
			if(menu.getIcon()==null || menu.getIcon().equals("")) {
				if(menu.getParentId()==0) {
					rvm.setIcon("fa-th");
				}else {
					rvm.setIcon("fa-circle-o");
				}
			}else {
				rvm.setIcon(menu.getIcon());
			}
			List<TreeViewModel> temp = buildResourceTree(menu.getId(), menus);
			rvm.setChildren(temp);
			rvms.add(rvm);
		}
		return rvms;
	}

	public List<Integer> getCheckedList(int roleId) {
		List<Integer> checkedList = menuDao.getCheckedList(roleId);
		return checkedList;
	}

	public int saveCheckedList(int roleId, List<Integer> checkedList) {
		menuDao.deleteRoleMenus(roleId);
		for(int menuId : checkedList) {
			menuDao.insertRoleMenu(roleId,menuId);
		}
		return 1;
	}
}
