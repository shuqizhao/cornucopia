package cornucopia.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cornucopia.entity.RoleEntity;
import cornucopia.service.RoleService;
import cornucopia.util.DataTableParameter;
import cornucopia.util.DataTableResult;

@RestController
@RequestMapping("/role")
public class RoleController {
	@RequestMapping(value = { "/list" }, method = RequestMethod.POST)
	public DataTableResult<RoleEntity> list(DataTableParameter dtp) {
		List<RoleEntity> roles = RoleService.getInstance().getRolesByPage(dtp.getiDisplayStart(),
				dtp.getiDisplayLength());
		int count = 0;
		if (roles != null && roles.size() > 0) {
			count = roles.get(0).getTotalCount();
		}
		DataTableResult<RoleEntity> dtr = new DataTableResult<RoleEntity>(dtp.getsEcho() + 1, count, count, roles);
		return dtr;
	}
}
