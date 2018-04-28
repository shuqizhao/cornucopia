package cornucopia.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cornucopia.entity.UserEntity;
import cornucopia.util.DataTableParameter;
import cornucopia.util.DataTableResult;

@RestController
@RequestMapping("/user")
public class UserController {

	@RequestMapping(value = { "/list"}, method = RequestMethod.POST)
	public DataTableResult<UserEntity> list(DataTableParameter dtp) {
		System.out.println(dtp.getsEcho());
		List<UserEntity> users = new ArrayList<>();
		DataTableResult<UserEntity> dtr = new DataTableResult<UserEntity>(dtp.getsEcho() + 1, 9, 9, users);
		return dtr;
	}
}