package cornucopia.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cornucopia.entity.JsonResult;
import cornucopia.entity.MenuEntity;
import cornucopia.entity.UserEntity;
import cornucopia.entity.WhiteListEntity;
import cornucopia.model.TreeViewModel;
import cornucopia.model.UserModPwdViewModel;
import cornucopia.service.MenuService;
import cornucopia.service.OrgService;
import cornucopia.service.UserService;
import cornucopia.service.WhiteListService;
import cornucopia.util.CookieUtil;
import cornucopia.util.DataTableParameter;
import cornucopia.util.DataTableResult;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@RequestMapping(value = { "/login" }, method = RequestMethod.POST)
	public JsonResult<String> login(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("Un") String un, @ModelAttribute("Pwd") String pwd) {
		int code = 500;
		UserEntity userEntity = UserService.getInstance().isLogin(un, pwd);
		if (userEntity != null) {
			code = 200;
			CookieUtil.set(response, "adAuthCookie", "true", 3600 * 24);
			CookieUtil.set(response, "loginUser", un, 3600 * 24);
			request.getSession().setAttribute("user", userEntity);
		}
		JsonResult<String> jr = new JsonResult<String>();
		jr.setCode(code);
		jr.setMessage("login sucess!");
		jr.setData("1");
		return jr;
	}

	@RequestMapping(value = { "/menus" }, method = RequestMethod.GET)
	public JsonResult<List<MenuEntity>> menus(HttpServletRequest request, HttpServletResponse response) {
		UserEntity userEntity = (UserEntity) request.getSession().getAttribute("user");
		int userId = 0;
		if (userEntity != null) {
			userId = userEntity.getId();
		}
		List<MenuEntity> menus = MenuService.getInstance().getAllMenus(userId);
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
	public JsonResult<Integer> saveCheckedList(int roleId,
			@RequestParam(value = "checkedList[]") List<Integer> checkedList) {
		int isOk = MenuService.getInstance().saveCheckedList(roleId, checkedList);
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

	@RequestMapping(value = { "/routerList" }, method = RequestMethod.GET)
	public JsonResult<List<MenuEntity>> routerList(HttpServletRequest request) {
		UserEntity userEntity = (UserEntity) request.getSession().getAttribute("user");
		int userId = 0;
		if (userEntity != null) {
			userId = userEntity.getId();
		}
		List<MenuEntity> menus = MenuService.getInstance().getAllRouters(userId);
		JsonResult<List<MenuEntity>> jr = new JsonResult<List<MenuEntity>>();
		jr.setCode(200);
		jr.setData(menus);
		return jr;
	}

	@RequestMapping(value = { "/whiteList" }, method = RequestMethod.POST)
	public DataTableResult<WhiteListEntity> list(DataTableParameter dtp) {
		List<WhiteListEntity> whiteList = WhiteListService.getInstance().getWhiteListByPage(dtp.getiDisplayStart(),
				dtp.getiDisplayLength());
		int count = 0;
		if (whiteList != null && whiteList.size() > 0) {
			count = whiteList.get(0).getTotalCount();
		}
		DataTableResult<WhiteListEntity> dtr = new DataTableResult<WhiteListEntity>(dtp.getsEcho() + 1, count, count,
				whiteList);
		return dtr;
	}

	@RequestMapping(value = { "/usermod" }, method = RequestMethod.POST)
	public JsonResult<Integer> userMod(HttpServletRequest request, UserModPwdViewModel umpvm) {
		UserEntity userEntity = (UserEntity) request.getSession().getAttribute("user");
		umpvm.setId(userEntity.getId()+"");
		int isOk = UserService.getInstance().modPwd(umpvm);
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(isOk);
		return jr;
	}

	@RequestMapping(value = { "/checkpwd" }, method = RequestMethod.POST)
	public JsonResult<Integer> checkPwd(HttpServletRequest request, UserModPwdViewModel umpvm) {
		UserEntity userEntity = (UserEntity) request.getSession().getAttribute("user");
		umpvm.setId(userEntity.getId()+"");
		int isOk = UserService.getInstance().checkPwd(umpvm);
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(isOk > 0 ? 1 : 0);
		return jr;
	}
}
