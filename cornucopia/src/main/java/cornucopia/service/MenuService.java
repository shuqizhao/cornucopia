package cornucopia.service;

import java.util.List;

import cornucopia.dao.MenuDao;
import cornucopia.entity.MenuEntity;
import cornucopia.util.MyBatisHelper;

public class MenuService {
	private static MenuService instance = new MenuService();

	private MenuService() {
	}

	public static MenuService getInstance() {
		return instance;
	}
	
	MenuDao menuDao = MyBatisHelper.getMapper(MenuDao.class);
	
	public List<MenuEntity> getAllMenus(){
		List<MenuEntity> menus = menuDao.getAllMenus();
		return menus;
	}
}
