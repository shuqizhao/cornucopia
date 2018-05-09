package cornucopia.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cornucopia.entity.JsonResult;
import cornucopia.entity.UserEntity;
import cornucopia.model.TransferViewModel;
import cornucopia.service.UserService;
import cornucopia.util.DataTableParameter;
import cornucopia.util.DataTableResult;

@RestController
@RequestMapping("/user")
public class UserController {

	@RequestMapping(value = { "/list" }, method = RequestMethod.POST)
	public DataTableResult<UserEntity> list(DataTableParameter dtp) {
		List<UserEntity> users = UserService.getInstance().getUsersByPage(dtp.getiDisplayStart(),
				dtp.getiDisplayLength());
		int count = 0;
		if (users != null && users.size() > 0) {
			count = users.get(0).getTotalCount();
		}
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
	public JsonResult<String> update(UserEntity uen) {
		System.out.println(uen.getId());
		JsonResult<String> jr = new JsonResult<String>();
		jr.setCode(200);
		jr.setMessage("login sucess!");
		jr.setData("1");
		return jr;
	}
}