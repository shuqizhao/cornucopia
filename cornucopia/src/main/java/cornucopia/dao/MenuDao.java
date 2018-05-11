package cornucopia.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import cornucopia.entity.MenuEntity;

public interface MenuDao {
	@Select("call sp_menu_all()")
    public List<MenuEntity> getAllMenus();
	
	@Select("call sp_menu_btn_all()")
    public List<MenuEntity> getAllMenusAndBtns();
}
