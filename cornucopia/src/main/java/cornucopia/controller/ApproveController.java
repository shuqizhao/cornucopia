package cornucopia.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cornucopia.entity.JsonResult;
import cornucopia.model.ApproveViewModel;
import cornucopia.entity.ApproveConditionEntity;
import cornucopia.entity.ApproveEntity;
import cornucopia.service.ApproveService;

@RestController
@RequestMapping("/approve")
public class ApproveController {
	@RequestMapping(value = { "/alllist" }, method = RequestMethod.GET)
	public JsonResult<List<ApproveEntity>> alllist() {
		List<ApproveEntity> approves = ApproveService.getInstance().getAll();
		
		JsonResult<List<ApproveEntity>> jr = new JsonResult<List<ApproveEntity>>();
		jr.setCode(200);
		jr.setData(approves);
		return jr;
	}
	
	@RequestMapping(value = { "/exists" }, method = RequestMethod.POST)
	public JsonResult<Integer> exists(String name) {
		int isExists = ApproveService.getInstance().exists(name);
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(isExists);
		return jr;
	}
	
	@RequestMapping(value = { "/add" }, method = RequestMethod.POST)
	public JsonResult<Integer> add(@RequestBody ApproveViewModel approveVm) {
		ApproveService.getInstance().insert(approveVm.getApprove());
		for (ApproveConditionEntity ac : approveVm.getApproveConditions()) {
			ac.setApproveId(approveVm.getApprove().getId());
		}
		ApproveService.getInstance().insertApproveConditions(approveVm.getApproveConditions());
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(approveVm.getApprove().getId());
		return jr;
	}
	
	@RequestMapping(value = { "/disable" }, method = RequestMethod.POST)
	public JsonResult<Integer> disable(@RequestParam(value = "Ids") int id) {
		int result = ApproveService.getInstance().disable(id);
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(result);
		return jr;
	}
	
	@RequestMapping(value = { "/enable" }, method = RequestMethod.POST)
	public JsonResult<Integer> enable(@RequestParam(value = "Ids") int id) {
		int result = ApproveService.getInstance().enable(id);
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(result);
		return jr;
	}
	
	@RequestMapping(value = { "/delete" }, method = RequestMethod.POST)
	public JsonResult<Integer> delete(@RequestParam(value = "Ids") int id) {
		int result = ApproveService.getInstance().delete(id);
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(result);
		return jr;
	}
}
