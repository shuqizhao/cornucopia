package cornucopia.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cornucopia.entity.JsonResult;
import cornucopia.entity.MenuEntity;
import cornucopia.model.TreeViewModel;
import cornucopia.service.MenuService;
import cornucopia.service.OrgService;
import cornucopia.service.UserService;
import cornucopia.util.CookieUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@RequestMapping(value = { "/login"}, method = RequestMethod.POST)
	public JsonResult<String> login(HttpServletResponse response, @ModelAttribute("Un") String un,
			@ModelAttribute("Pwd") String pwd) {
		int code = 500;
		boolean isLogin = UserService.getInstance().isLogin(un, pwd);
		if (isLogin) {
			code = 200;
			CookieUtil.set(response, "adAuthCookie", "true", 3600 * 24);
			CookieUtil.set(response, "loginUser", un, 3600 * 24);
		}
		JsonResult<String> jr = new JsonResult<String>();
		jr.setCode(code);
		jr.setMessage("login sucess!");
		jr.setData("1");
		return jr;
	}

	@RequestMapping(value = { "/menus"}, method = RequestMethod.GET)
	public JsonResult<List<MenuEntity>> menus(HttpServletResponse response) {
		List<MenuEntity> menus = MenuService.getInstance().getAllMenus();
		JsonResult<List<MenuEntity>> jr = new JsonResult<List<MenuEntity>>();
		jr.setCode(200);
		jr.setData(menus);
		return jr;
	}
	
	@RequestMapping(value = { "/allResource" }, method = RequestMethod.GET)
	public JsonResult<List<TreeViewModel>> allResource() {
		List<TreeViewModel> menus = MenuService.getInstance().getAllResourceTree();
		JsonResult<List<TreeViewModel>> jr = new JsonResult<List<TreeViewModel>>();
		jr.setCode(200);
		jr.setData(menus);
		return jr;
	}
	
	@RequestMapping(value = { "/getCheckedList" }, method = RequestMethod.GET)
	public JsonResult<List<Integer>> getCheckedList(int roleId) {
		List<Integer> checkedList = MenuService.getInstance().getCheckedList(roleId);
		JsonResult<List<Integer>> jr = new JsonResult<List<Integer>>();
		jr.setCode(200);
		jr.setData(checkedList);
		return jr;
	}
	
	@RequestMapping(value = { "/saveCheckedList" }, method = RequestMethod.POST)
	public JsonResult<Integer> saveCheckedList(int roleId,@RequestParam(value = "checkedList[]") List<Integer> checkedList) {
		int isOk = MenuService.getInstance().saveCheckedList(roleId,checkedList);
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(isOk);
		return jr;
	}
	
	@RequestMapping(value = { "/allOrg" }, method = RequestMethod.GET)
	public JsonResult<List<TreeViewModel>> allOrg() {
		List<TreeViewModel> menus = OrgService.getInstance().getAllOrgTree();
		JsonResult<List<TreeViewModel>> jr = new JsonResult<List<TreeViewModel>>();
		jr.setCode(200);
		jr.setData(menus);
		return jr;
	}
}
