package cornucopia.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import cornucopia.dao.MenuDao;
import cornucopia.entity.MenuEntity;
import cornucopia.model.ResourceViewModel;
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

	public List<ResourceViewModel> getAllResourceTree() {
		List<MenuEntity> menus = getAllMenusAndBtns();
		List<ResourceViewModel> rvms = buildResourceTree(0, menus);
		return rvms;
	}

	private List<ResourceViewModel> buildResourceTree(int parentId, List<MenuEntity> menus) {
		List<ResourceViewModel> rvms = new ArrayList<ResourceViewModel>();
		List<MenuEntity> curMenus = menus.stream().filter((MenuEntity r) -> r.getParentId() == parentId)
				.collect(Collectors.toList());
		for (MenuEntity menu : curMenus) {
			ResourceViewModel rvm = new ResourceViewModel();
			rvm.setId(menu.getId());
			rvm.setName(menu.getName());
			List<ResourceViewModel> temp = buildResourceTree(menu.getId(), menus);
			rvm.setChildren(temp);
			rvms.add(rvm);
		}
		return rvms;
	}
}
