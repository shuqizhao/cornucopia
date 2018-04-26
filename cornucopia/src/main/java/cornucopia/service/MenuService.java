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
	
	public List<MenuEntity> getAllMenus(){
		MenuDao menuDao = MyBatisHelper.getMapper(MenuDao.class);
		return menuDao.getAllMenus();
	}
}
