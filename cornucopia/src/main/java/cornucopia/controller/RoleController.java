package cornucopia.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cornucopia.entity.JsonResult;
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
	
	@RequestMapping(value = { "/alllist" }, method = RequestMethod.GET)
	public JsonResult<List<RoleEntity>> alllist() {
		List<RoleEntity> roles = RoleService.getInstance().getAllRoles();
		
		JsonResult<List<RoleEntity>> jr = new JsonResult<List<RoleEntity>>();
		jr.setCode(200);
		jr.setData(roles);
		return jr;
	}
	
	@RequestMapping(value = { "/exists" }, method = RequestMethod.POST)
	public JsonResult<Integer> exists(String name) {
		int isExists = RoleService.getInstance().exists(name);
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(isExists);
		return jr;
	}
	
	@RequestMapping(value = { "/add" }, method = RequestMethod.POST)
	public JsonResult<Integer> add(RoleEntity roleEntity) {
		RoleService.getInstance().insert(roleEntity);
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(roleEntity.getId());
		return jr;
	}
	
	@RequestMapping(value = { "/disable" }, method = RequestMethod.POST)
	public JsonResult<Integer> disable(@RequestParam(value = "Ids") int id) {
		int result = RoleService.getInstance().disable(id);
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(result);
		return jr;
	}
	
	@RequestMapping(value = { "/enable" }, method = RequestMethod.POST)
	public JsonResult<Integer> enable(@RequestParam(value = "Ids") int id) {
		int result = RoleService.getInstance().enable(id);
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(result);
		return jr;
	}
	
	@RequestMapping(value = { "/delete" }, method = RequestMethod.POST)
	public JsonResult<Integer> delete(@RequestParam(value = "Ids") int id) {
		int result = RoleService.getInstance().delete(id);
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(result);
		return jr;
	}
}
