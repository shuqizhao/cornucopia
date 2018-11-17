package cornucopia.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cornucopia.entity.JsonResult;
import cornucopia.entity.MenuEntity;
import cornucopia.entity.UserEntity;
import cornucopia.service.MenuService;

@RestController
@RequestMapping("/resource")
public class ResourceController {

    @RequestMapping(value = { "/get" }, method = RequestMethod.GET)
	public JsonResult<MenuEntity> get(int id) {
		MenuEntity menu = MenuService.getInstance().get(id);
		JsonResult<MenuEntity> jr = new JsonResult<MenuEntity>();
		jr.setCode(200);
		jr.setData(menu);
		return jr;
    }

	@RequestMapping(value = { "/all" }, method = RequestMethod.GET)
	public JsonResult<List<MenuEntity>> all(int menuId) {
		List<MenuEntity> menus = MenuService.getInstance().getAllMenuList(menuId);
		JsonResult<List<MenuEntity>> jr = new JsonResult<List<MenuEntity>>();
		jr.setCode(200);
		jr.setData(menus);
		return jr;
    }
    
    @RequestMapping(value = { "/add" }, method = RequestMethod.POST)
	public JsonResult<Integer> add(HttpServletRequest request, MenuEntity menuEntity) {
		UserEntity userEntity = (UserEntity) request.getSession().getAttribute("user");
		int userId = 0;
		if (userEntity != null) {
			userId = userEntity.getId();
		}
		menuEntity.setCreateBy(userId);
		MenuService.getInstance().insert(menuEntity);
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(menuEntity.getId());
		return jr;
    }
    
    @RequestMapping(value = { "/update" }, method = RequestMethod.POST)
	public JsonResult<Integer> update(HttpServletRequest request, MenuEntity menuEntity) {
		UserEntity userEntity = (UserEntity) request.getSession().getAttribute("user");
		int userId = 0;
		if (userEntity != null) {
			userId = userEntity.getId();
		}
		menuEntity.setUpdateBy(userId);
		MenuService.getInstance().update(menuEntity);
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(menuEntity.getId());
		return jr;
	}
}
