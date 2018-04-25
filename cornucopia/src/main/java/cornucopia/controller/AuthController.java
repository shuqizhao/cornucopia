package cornucopia.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cornucopia.model.JsonResult;
import cornucopia.util.CookieUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@RequestMapping(value = { "/login", "" }, method = RequestMethod.POST)
	public JsonResult<String> login(HttpServletResponse response, @ModelAttribute("Un") String un, @ModelAttribute("Pwd") String pwd) {
		CookieUtil.set(response, "adAuthCookie", "true", 60);
		CookieUtil.set(response, "loginUser", un, 60);
		JsonResult<String> jr = new JsonResult<String>();
		jr.setCode(200);
		jr.setMessage("login sucess!");
		jr.setData("1");
		return jr;
	}
}
