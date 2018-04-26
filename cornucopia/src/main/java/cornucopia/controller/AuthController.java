package cornucopia.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cornucopia.entity.JsonResult;
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
		if(isLogin)
		{
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
}
