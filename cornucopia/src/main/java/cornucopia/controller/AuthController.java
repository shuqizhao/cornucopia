package cornucopia.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cornucopia.entity.JsonResult;
import cornucopia.entity.MenuEntity;
import cornucopia.service.MenuService;
import cornucopia.service.UserService;
import cornucopia.util.CookieUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@RequestMapping(value = { "/login", "" }, method = RequestMethod.POST)
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

	@RequestMapping(value = { "/menus", "" }, method = RequestMethod.GET)
	public JsonResult<List<MenuEntity>> menus(HttpServletResponse response) {
		List<MenuEntity> menus = MenuService.getInstance().getAllMenus();
		JsonResult<List<MenuEntity>> jr = new JsonResult<List<MenuEntity>>();
		jr.setCode(200);
		jr.setData(menus);
		return jr;
	}
}
