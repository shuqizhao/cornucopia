package cornucopia.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cornucopia.entity.JsonResult;
import cornucopia.entity.RoleEntity;
import cornucopia.entity.RuleConditionEntity;
import cornucopia.entity.RuleEntity;
import cornucopia.model.ApprovePositionViewModel;
import cornucopia.model.ApproveViewModel;
import cornucopia.model.RuleViewModel;
import cornucopia.model.SelectViewModel;
import cornucopia.entity.ApproveConditionEntity;
import cornucopia.entity.ApproveEntity;
import cornucopia.entity.ApproveMatrixEntity;
import cornucopia.entity.ApprovePositionEntity;
import cornucopia.service.ApproveMatrixService;
import cornucopia.service.ApprovePositionService;
import cornucopia.service.ApproveService;
import cornucopia.service.RoleService;
import cornucopia.service.RuleService;
import cornucopia.util.DynamicBean;

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
		ApproveService.getInstance().updateApproveConditions(approveVm.getApprove().getId(),
				approveVm.getApproveConditions());
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(approveVm.getApprove().getId());
		return jr;
	}

	@RequestMapping(value = { "/positionlist" }, method = RequestMethod.GET)
	public JsonResult<List<ApprovePositionViewModel>> positionlist(int processNodeId) {
		List<ApprovePositionEntity> poses = ApprovePositionService.getInstance().getAll(processNodeId);
		List<ApprovePositionViewModel> posvms = new ArrayList<ApprovePositionViewModel>();
		for (ApprovePositionEntity pos : poses) {
			ApprovePositionViewModel posvm = new ApprovePositionViewModel();
			posvm.setId(pos.getId());
			posvm.setName(pos.getName());
			posvm.setType(pos.getType());
			posvm.setRule(pos.getRule());
			posvm.setIsEnabled(pos.getIsEnabled());
			posvm.setCreateTime(pos.getCreateTime());
			if (pos.getType() == 2) {
				RuleEntity rule = RuleService.getInstance().get(pos.getRule());
				if (rule != null) {
					posvm.setRuleStr(rule.getName());
				}
			} else {
				RoleEntity role = RoleService.getInstance().get(pos.getRule());
				if (role != null) {
					posvm.setRuleStr(role.getName());
				}
			}
			posvms.add(posvm);
		}
		JsonResult<List<ApprovePositionViewModel>> jr = new JsonResult<List<ApprovePositionViewModel>>();
		jr.setCode(200);
		jr.setData(posvms);
		return jr;
	}

	@RequestMapping(value = { "/positionAdd" }, method = RequestMethod.POST)
	public JsonResult<Integer> positionAdd(ApprovePositionEntity pos) {
		int id = ApprovePositionService.getInstance().insert(pos);
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(id);
		return jr;
	}

	@RequestMapping(value = { "/positionUpdate" }, method = RequestMethod.POST)
	public JsonResult<Integer> positionUpdate(ApprovePositionEntity pos) {
		int id = ApprovePositionService.getInstance().update(pos);
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(id);
		return jr;
	}

	@RequestMapping(value = { "/getPosition" }, method = RequestMethod.GET)
	public JsonResult<ApprovePositionEntity> getPosition(int id) {
		ApprovePositionEntity approvePositionEntity = ApprovePositionService.getInstance().getPosition(id);
		JsonResult<ApprovePositionEntity> jr = new JsonResult<ApprovePositionEntity>();
		jr.setCode(200);
		jr.setData(approvePositionEntity);
		return jr;
	}

	@RequestMapping(value = { "/getAllRoles" }, method = RequestMethod.GET)
	public JsonResult<List<SelectViewModel>> getAllRoles() {
		List<SelectViewModel> svms = new ArrayList<SelectViewModel>();
		List<RoleEntity> roleEntities = RoleService.getInstance().getAllRoles();
		for (RoleEntity re : roleEntities) {
			if (re.getIsDeleted() == 1 || re.getIsEnabled() == 0) {
				continue;
			}
			SelectViewModel svm = new SelectViewModel();
			svm.setId(re.getId());
			svm.setValue(re.getName());
			svm.setDesc(re.getIsDeleted() + "");
			svms.add(svm);
		}
		JsonResult<List<SelectViewModel>> jr = new JsonResult<List<SelectViewModel>>();
		jr.setCode(200);
		jr.setData(svms);
		return jr;
	}

	@RequestMapping(value = { "/getAllRules" }, method = RequestMethod.GET)
	public JsonResult<List<SelectViewModel>> getAllRules() {
		List<SelectViewModel> svms = new ArrayList<SelectViewModel>();
		List<RuleEntity> ruleEntities = RuleService.getInstance().getAll();
		for (RuleEntity re : ruleEntities) {
			if (re.getIsDeleted() == 1 || re.getIsEnabled() == 0) {
				continue;
			}
			SelectViewModel svm = new SelectViewModel();
			svm.setId(re.getId());
			svm.setValue(re.getName());
			svm.setDesc(re.getIsDeleted() + "");
			svms.add(svm);
		}
		JsonResult<List<SelectViewModel>> jr = new JsonResult<List<SelectViewModel>>();
		jr.setCode(200);
		jr.setData(svms);
		return jr;
	}

	@RequestMapping(value = { "/rulelist" }, method = RequestMethod.GET)
	public JsonResult<List<RuleEntity>> ruleList() {
		List<RuleEntity> ruleEntities = RuleService.getInstance().getAll();
		JsonResult<List<RuleEntity>> jr = new JsonResult<List<RuleEntity>>();
		jr.setCode(200);
		jr.setData(ruleEntities);
		return jr;
	}

	@RequestMapping(value = { "/ruleadd" }, method = RequestMethod.POST)
	public JsonResult<Integer> ruleAdd(RuleEntity ruleEntity) {
		int result = RuleService.getInstance().insert(ruleEntity);
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(result);
		return jr;
	}

	@RequestMapping(value = { "/ruleUpdate" }, method = RequestMethod.POST)
	public JsonResult<Integer> ruleUpdate(RuleEntity ruleEntity) {
		int result = RuleService.getInstance().update(ruleEntity);
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(result);
		return jr;
	}

	@RequestMapping(value = { "/getRule" }, method = RequestMethod.GET)
	public JsonResult<RuleEntity> getRule(int id) {
		RuleEntity ruleEnity = RuleService.getInstance().get(id);
		JsonResult<RuleEntity> jr = new JsonResult<RuleEntity>();
		jr.setCode(200);
		jr.setData(ruleEnity);
		return jr;
	}

	@RequestMapping(value = { "/getRuleChildren" }, method = RequestMethod.GET)
	public JsonResult<List<RuleEntity>> getRuleChildren(int ruleId) {
		List<RuleEntity> rules = RuleService.getInstance().getRuleChildren(ruleId);
		JsonResult<List<RuleEntity>> jr = new JsonResult<List<RuleEntity>>();
		jr.setCode(200);
		jr.setData(rules);
		return jr;
	}

	@RequestMapping(value = { "/ruleChildUpdate" }, method = RequestMethod.POST)
	public JsonResult<Integer> ruleChildUpdate(@RequestBody RuleViewModel ruleVm) {
		RuleService.getInstance().update(ruleVm.getRule());
		for (RuleConditionEntity rc : ruleVm.getRuleConditions()) {
			rc.setRuleId(ruleVm.getRule().getId());
		}
		RuleService.getInstance().updateRuleConditions(ruleVm.getRule().getId(), ruleVm.getRuleConditions());
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(ruleVm.getRule().getId());
		return jr;
	}

	@RequestMapping(value = { "/ruleChildAdd" }, method = RequestMethod.POST)
	public JsonResult<Integer> ruleChildAdd(@RequestBody RuleViewModel ruleVm) {
		RuleService.getInstance().insert(ruleVm.getRule());
		for (RuleConditionEntity rc : ruleVm.getRuleConditions()) {
			rc.setRuleId(ruleVm.getRule().getId());
		}
		RuleService.getInstance().insertRuleConditions(ruleVm.getRuleConditions());
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(ruleVm.getRule().getId());
		return jr;
	}

	@RequestMapping(value = { "/getRuleCondititons" }, method = RequestMethod.GET)
	public JsonResult<List<RuleConditionEntity>> getRuleCondititons(int id) {
		List<RuleConditionEntity> conditions = RuleService.getInstance().getRuleConditions(id);
		JsonResult<List<RuleConditionEntity>> jr = new JsonResult<List<RuleConditionEntity>>();
		jr.setCode(200);
		jr.setData(conditions);
		return jr;
	}

	@RequestMapping(value = { "/matrix" }, method = RequestMethod.GET)
	public JsonResult<List<Object>> matrix(int processNodeId, int conditionId) throws ClassNotFoundException {
		// 审批岗位
		List<ApprovePositionEntity> poses = ApprovePositionService.getInstance().getAll(processNodeId);
		// 审批路径
		List<ApproveEntity> approves = ApproveService.getInstance().getAllChildren(conditionId);
		// 审批矩阵
		List<ApproveMatrixEntity> approveMatrixEntities = ApproveMatrixService.getInstance().getAll(conditionId);
		if (poses == null) {
			poses = new ArrayList<ApprovePositionEntity>();
		}
		if (approves == null) {
			approves = new ArrayList<ApproveEntity>();
		}
		if (approveMatrixEntities == null) {
			approveMatrixEntities = new ArrayList<ApproveMatrixEntity>();
		}
		List<Object> result = new ArrayList<Object>();
		for (ApprovePositionEntity ap : poses) {
			HashMap<String, Object> typeMap = new HashMap<String, Object>();
			typeMap.put("postionId", Class.forName("java.lang.Integer"));
			typeMap.put("postionName", Class.forName("java.lang.String"));
			for (ApproveEntity approve : approves) {
				typeMap.put("id_" + approve.getId(), Class.forName("java.lang.Integer"));
			}
			DynamicBean bean = new DynamicBean(typeMap);
			for (ApproveEntity approve : approves) {
				List<ApproveMatrixEntity> approveMatrixEntityTemps = approveMatrixEntities.stream()
						.filter((ApproveMatrixEntity r) -> r.getApproveCondition2Id() == approve.getId())
						.collect(Collectors.toList());
				bean.setValue("id_" + approve.getId(), new Integer(approveMatrixEntityTemps.size() == 0 ? 0 : 1));
			}
			bean.setValue("postionId", new Integer(ap.getId()));
			bean.setValue("postionName", ap.getName());
			result.add(bean.getObject());
		}
		JsonResult<List<Object>> jr = new JsonResult<List<Object>>();
		jr.setCode(200);
		jr.setData(result);
		return jr;
	}
}
