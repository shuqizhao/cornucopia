package cornucopia.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cornucopia.entity.JsonResult;
import cornucopia.entity.UserEntity;
import cornucopia.model.ApplicantViewModel;
import cornucopia.model.TransferViewModel;
import cornucopia.model.UserSearchViewModel;
import cornucopia.service.UserService;
import cornucopia.util.DataTableParameter;
import cornucopia.util.DataTableResult;
import cornucopia.util.PagingParameters;

@RestController
@RequestMapping("/user")
public class UserController {

	@RequestMapping(value = { "/list" }, method = RequestMethod.POST)
	public DataTableResult<UserEntity> list(DataTableParameter dtp) {
		PagingParameters pp = new PagingParameters();
		pp.setStart(dtp.getiDisplayStart());
		pp.setLength(dtp.getiDisplayLength());
		pp.setTotalRows(0);
		UserSearchViewModel usvm = JSON.parseObject(dtp.getsSearch(), UserSearchViewModel.class);
		List<UserEntity> users = UserService.getInstance().getUsersByPage(pp,usvm);
		int count = pp.getTotalRows();
		DataTableResult<UserEntity> dtr = new DataTableResult<UserEntity>(dtp.getsEcho() + 1, count, count, users);
		return dtr;
	}
	
	@RequestMapping(value = { "/get"}, method = RequestMethod.GET)
	public JsonResult<UserEntity> get(int id) {
		UserEntity user = UserService.getInstance().getUser(id);
		JsonResult<UserEntity> jr = new JsonResult<UserEntity>();
		jr.setCode(200);
		jr.setData(user);
		return jr;
	}
	
	@RequestMapping(value = "/roles", method = RequestMethod.GET)
	public TransferViewModel roles(int id) {
		TransferViewModel transferViewModel = UserService.getInstance().getUserRolesTransfer(id);
		return transferViewModel;
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public JsonResult<String> update(int id,@RequestParam(value = "roles[]") int[] roles) {
		UserService.getInstance().updateUserRoles(id,roles);
		JsonResult<String> jr = new JsonResult<String>();
		jr.setCode(200);
		jr.setMessage("update sucess!");
		jr.setData("1");
		return jr;
	}
	
	@RequestMapping(value = { "/getApplicant"}, method = RequestMethod.GET)
	public JsonResult<ApplicantViewModel> getApplicant(HttpServletRequest request) {
		UserEntity user = (UserEntity)request.getSession().getAttribute("user");
		ApplicantViewModel av = UserService.getInstance().getApplicant(user.getId());
		JsonResult<ApplicantViewModel> jr = new JsonResult<ApplicantViewModel>();
		jr.setCode(200);
		jr.setData(av);
		return jr;
	}

	@RequestMapping(value = { "/getByRoleId" }, method = RequestMethod.POST)
	public DataTableResult<UserEntity> getByRoleId(DataTableParameter dtp) {
		PagingParameters pp = new PagingParameters();
		pp.setStart(dtp.getiDisplayStart());
		pp.setLength(dtp.getiDisplayLength());
		pp.setTotalRows(0);
		UserSearchViewModel usvm = JSON.parseObject(dtp.getsSearch(), UserSearchViewModel.class);
		List<UserEntity> users = UserService.getInstance().getUsersByRoleId(pp,usvm.getRoleId(),usvm.getName());
		int count = pp.getTotalRows();
		DataTableResult<UserEntity> dtr = new DataTableResult<UserEntity>(dtp.getsEcho() + 1, count, count, users);
		return dtr;
	}
}