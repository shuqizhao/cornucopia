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
import cornucopia.entity.ApprovePositionEntity;
import cornucopia.service.ApprovePositionService;
import cornucopia.service.ApproveService;

@RestController
@RequestMapping("/approve")
public class ApproveController {
	@RequestMapping(value = { "/alllist" }, method = RequestMethod.GET)
	public JsonResult<List<ApproveEntity>> alllist(int processNodeId) {
		List<ApproveEntity> approves = ApproveService.getInstance().getAll(processNodeId);
		
		JsonResult<List<ApproveEntity>> jr = new JsonResult<List<ApproveEntity>>();
		jr.setCode(200);
		jr.setData(approves);
		return jr;
	}
	
	@RequestMapping(value = { "/allchildren" }, method = RequestMethod.GET)
	public JsonResult<List<ApproveEntity>> allchildren(int approveId) {
		List<ApproveEntity> approves = ApproveService.getInstance().getAllChildren(approveId);
		
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
	
	@RequestMapping(value = { "/get" }, method = RequestMethod.GET)
	public JsonResult<ApproveEntity> get(int id) {
		ApproveEntity approveEntity = ApproveService.getInstance().get(id);
		JsonResult<ApproveEntity> jr = new JsonResult<ApproveEntity>();
		jr.setCode(200);
		jr.setData(approveEntity);
		return jr;
	}
	
	@RequestMapping(value = { "/getConditions" }, method = RequestMethod.GET)
	public JsonResult<List<ApproveConditionEntity>> getConditions(int id) {
		List<ApproveConditionEntity> conditions = ApproveService.getInstance().getConditions(id);
		JsonResult<List<ApproveConditionEntity>> jr = new JsonResult<List<ApproveConditionEntity>>();
		jr.setCode(200);
		jr.setData(conditions);
		return jr;
	}
	
	@RequestMapping(value = { "/update" }, method = RequestMethod.POST)
	public JsonResult<Integer> update(@RequestBody ApproveViewModel approveVm) {
		ApproveService.getInstance().update(approveVm.getApprove());
		for (ApproveConditionEntity ac : approveVm.getApproveConditions()) {
			ac.setApproveId(approveVm.getApprove().getId());
		}
		ApproveService.getInstance().updateFunctionParas(approveVm.getApprove().getId(),approveVm.getApproveConditions());
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(approveVm.getApprove().getId());
		return jr;
	}
	
	@RequestMapping(value = { "/positionlist" }, method = RequestMethod.GET)
	public JsonResult<List<ApprovePositionEntity>> positionlist(int processNodeId) {
		List<ApprovePositionEntity> poses = ApprovePositionService.getInstance().getAll(processNodeId);
		
		JsonResult<List<ApprovePositionEntity>> jr = new JsonResult<List<ApprovePositionEntity>>();
		jr.setCode(200);
		jr.setData(poses);
		return jr;
	}
}
