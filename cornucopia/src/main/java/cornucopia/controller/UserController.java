package cornucopia.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cornucopia.entity.JsonResult;

@RestController
@RequestMapping("/user")
public class UserController {

	@RequestMapping(value = { "/login", "" })
	public JsonResult<String> login() {

		JsonResult<String> jr = new JsonResult<String>();
		jr.setCode(200);
		jr.setMessage("login sucess!");
		jr.setData("I love you.");
		return jr;
	}

}