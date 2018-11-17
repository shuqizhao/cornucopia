package cornucopia.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import cornucopia.entity.MenuEntity;

public interface MenuDao {
	@Select("call sp_menu_all_by_user_id(#{userId})")
	public List<MenuEntity> getAllMenus(@Param("userId") int userId);
	
	@Select("call sp_menu_all()")
    public List<MenuEntity> getAllMenuList();
	
	@Select("call sp_menu_btn_all()")
    public List<MenuEntity> getAllMenusAndBtns();

	@Select("call sp_menu_checkedlist_get(#{roleId})")
	public List<Integer> getCheckedList(@Param("roleId") int roleId);

	@Delete("call sp_role_menu_del(#{roleId})")
	public boolean deleteRoleMenus(@Param("roleId") int roleId);

	@Insert("call sp_role_menu_insert(#{roleId},#{menuId})")
	public void insertRoleMenu(@Param("roleId") int roleId,@Param("menuId") int menuId);

	@Select("call sp_menu_router_all(#{userId})")
	public List<MenuEntity> getAllRouters(@Param("userId") int userId);
}
