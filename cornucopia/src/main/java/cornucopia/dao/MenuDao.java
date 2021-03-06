package cornucopia.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import cornucopia.entity.MenuEntity;

public interface MenuDao {
	@Select("call sp_menu_all_by_user_id(#{userId})")
	public List<MenuEntity> getAllMenus(@Param("userId") int userId);
	
	@Select("call sp_menu_all(#{menuId})")
    public List<MenuEntity> getAllMenuList(@Param("menuId")int menuId);
	
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

	@Insert("call sp_menu_insert(#{menu.name},#{menu.parentId},#{menu.routerName},#{menu.url},#{menu.functionName},#{menu.api},#{menu.icon},#{menu.type},#{menu.createBy})")
	public int insert(@Param("menu")MenuEntity menuEntity);

	@Select("call sp_menu_get(#{id})")
	public MenuEntity get(@Param("id")int id);

	@Update("call sp_menu_update(#{menu.id},#{menu.name},#{menu.parentId},#{menu.routerName},#{menu.url},#{menu.functionName},#{menu.api},#{menu.icon},#{menu.type},#{menu.updateBy})")
	public int update(@Param("menu")MenuEntity menuEntity);
}
