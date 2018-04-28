package cornucopia.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cornucopia.entity.UserEntity;
import cornucopia.service.UserService;
import cornucopia.util.DataTableParameter;
import cornucopia.util.DataTableResult;

@RestController
@RequestMapping("/user")
public class UserController {

	@RequestMapping(value = { "/list"}, method = RequestMethod.POST)
	public DataTableResult<UserEntity> list(DataTableParameter dtp) {
		List<UserEntity> users = UserService.getInstance().getUsersByPage(dtp.getiDisplayStart(), dtp.getiDisplayLength());
//		List<UserEntity> users = new ArrayList<UserEntity>();
		DataTableResult<UserEntity> dtr = new DataTableResult<UserEntity>(dtp.getsEcho() + 1, 9, 9, users);
		return dtr;
	}
}