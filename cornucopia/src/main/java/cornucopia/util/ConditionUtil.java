package cornucopia.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;

import cornucopia.entity.ApproveMatrixEntity;
import cornucopia.entity.ApprovePositionEntity;
import cornucopia.entity.ConditionEntity;
import cornucopia.entity.ProcessDataEntity;
import cornucopia.entity.ProcessNodeEntity;
import cornucopia.entity.RuleEntity;
import cornucopia.service.ApproveMatrixService;
import cornucopia.service.ApprovePositionService;
import cornucopia.service.ApproveService;
import cornucopia.service.FunctionService;
import cornucopia.service.ProcessNodeService;
import cornucopia.service.ProcessService;
import cornucopia.service.RoleService;
import cornucopia.service.RuleService;

public class ConditionUtil {
	public static List<Integer> getNextDealUser(ProcessDataEntity pde) {
		return getNextDealUser(pde, null);
	}

	@SuppressWarnings("unchecked")
	public static List<Integer> getNextDealUser(ProcessDataEntity pde, ProcessService processService) {
		Log4jHelper.LOGGER.info(String.format("[%s]->%s->updateBy=%d->instId=%s", pde.getFormCode(), "开始获取审批人员",
				pde.getUpdateBy(), pde.getProcinstId()));
		TaskQuery query = ActivitiHelper.GetEngine().getTaskService().createTaskQuery();
		query.processInstanceId(pde.getProcinstId());
		Task task = query.singleResult();
		String taskName = task.getName();
		String currentUserId = task.getAssignee();
		ProcessNodeEntity pne = ProcessNodeService.getInstance().getByName(pde.getProcessId() + "", taskName);
		if (pne == null || !pne.getName().contains("DOA")) {
			pne = ProcessNodeService.getInstance().getDoaNode(pde.getProcessId() + "");
			if (pne == null) {
				Log4jHelper.LOGGER.error(String.format("[%s]->%s->updateBy=%d->instId=%s", pde.getFormCode(), "未能获取DOA",
						pde.getUpdateBy(), pde.getProcinstId()));
				return null;
			}
		}
		int processNodeId = pne.getId();

		List<ApproveMatrixEntity> ams = ApproveMatrixService.getInstance().getByNodeId(processNodeId);
		if (ams == null || ams.size() == 0) {
			Log4jHelper.LOGGER.error(String.format("[%s]->%s->updateBy=%d->instId=%s", pde.getFormCode(), "未能获取审批矩阵",
					pde.getUpdateBy(), pde.getProcinstId()));
			return null;
		}

		// build diagram use
		if (processService != null) {
			processService.clear(pde);
		}

		int i = 0;
		for (ApproveMatrixEntity am : ams) {
			if (pde.getLevelCount() == i || processService != null) {
				int c1Id = am.getApproveCondition1Id();
				List<?> acs = ApproveService.getInstance().getConditions(c1Id);
				if (acs == null || acs.size() == 0) {
					Log4jHelper.LOGGER.error(String.format("[%s]->%s->updateBy=%d->instId=%s->c1Id=%d",
							pde.getFormCode(), "未能获取审批条件c1", pde.getUpdateBy(), pde.getProcinstId(), c1Id));
					return null;
				}
				boolean c1Bool = calculateCondition((List<ConditionEntity>) acs, pde);
				if (c1Bool) {
					int c2Id = am.getApproveCondition2Id();
					List<?> bacs = ApproveService.getInstance().getConditions(c2Id);
					if (bacs == null || bacs.size() == 0) {
						Log4jHelper.LOGGER.error(String.format("[%s]->%s->updateBy=%d->instId=%s->c2Id=%d",
								pde.getFormCode(), "未能获取审批条件c2", pde.getUpdateBy(), pde.getProcinstId(), c2Id));
						return null;
					}
					boolean c2Bool = calculateCondition((List<ConditionEntity>) bacs, pde);
					if (c2Bool) {
						int positionId = am.getApprovePositionId();
						ApprovePositionEntity ape = ApprovePositionService.getInstance().getPosition(positionId);
						pde.setStepName(ape.getVitualTitle());
						pde.setCondition(ape.getApproveType());
						Log4jHelper.LOGGER.info(String.format(
								"[%s]->%s->updateBy=%d->instId=%s->c2Id=%d->vitualTitle=%s->approveType=%d",
								pde.getFormCode(), "找到虚拟岗位", pde.getUpdateBy(), pde.getProcinstId(), c2Id,
								ape.getVitualTitle(), ape.getApproveType()));
						List<Integer> userIds = getUserIdsByPosition(ape, pde);
						if (processService != null) {
							processService.build(pde, userIds, currentUserId, i);
						}
						return userIds;
					}
				}
			}
			i++;
		}
		if (pde.getLevelCount() == ams.size()) {
			List<Integer> result = new ArrayList<Integer>();
			result.add(666666);
			return result;
		} else {
			Log4jHelper.LOGGER.error(String.format("[%s]->%s->updateBy=%d->instId=%s->levelCount=%d->processNodeId=%d",
					pde.getFormCode(), "审批条件不匹配", pde.getUpdateBy(), pde.getProcinstId(), pde.getLevelCount(),
					processNodeId));
			return null;
		}
	}

	public static List<Integer> getUserIdsByPositionIdWithRule(int id, ProcessDataEntity pde) {
		List<RuleEntity> rules = RuleService.getInstance().getRuleChildren(id);
		if (rules == null || rules.size() == 0) {
			Log4jHelper.LOGGER.error(String.format("[%s]->%s->ruleId=%d", pde.getFormCode(), "找不到岗位规则", id));
			return new ArrayList<Integer>();
		}
		for (RuleEntity rule : rules) {
			Log4jHelper.LOGGER
					.info(String.format("[%s]->开始计算规则[%s]->ruleId=%d", pde.getFormCode(), rule.getName(), id));
			List<?> rcs = RuleService.getInstance().getRuleConditions(rule.getId());
			if (rcs == null || rcs.size() == 0) {
				Log4jHelper.LOGGER.error(
						String.format("[%s]->开始计算规则[%s],找不到岗位规则条件->ruleId=%d", pde.getFormCode(), rule.getName(), id));
				continue;
			}
			@SuppressWarnings("unchecked")
			boolean cBool = ConditionUtil.calculateCondition((List<ConditionEntity>) rcs, pde);
			if (cBool) {
				// 角色
				if (rule.getType() == 1) {
					Log4jHelper.LOGGER.info(String.format("[%s]->开始计算规则[%s]->ruleId=%d->通过角色[%s]获取人员",
							pde.getFormCode(), rule.getName(), rule.getUser(), id));
					return RoleService.getInstance().getUserIdsByRoleId(rule.getUser());
				} else if (rule.getType() == 2) {// 函数
					Log4jHelper.LOGGER.info(String.format("[%s]->开始计算规则[%s]->ruleId=%d->通过函数[%s]获取人员",
							pde.getFormCode(), rule.getName(), rule.getUser(), id));
					return FunctionService.getInstance().executeGetUserIds(rule.getUser(), pde);
				} else if (rule.getType() == 3) {// 指定人
					Log4jHelper.LOGGER.info(String.format("[%s]->开始计算规则[%s]->ruleId=%d->通过指定人[%s]获取人员",
							pde.getFormCode(), rule.getName(), rule.getUser(), id));
					List<Integer> list = new ArrayList<Integer>();
					list.add(rule.getRuleId());
					return list;
				} else if (rule.getType() == 4) {// 跳过
					Log4jHelper.LOGGER.info(String.format("[%s]->开始计算规则[%s]->ruleId=%d->跳过[%s]", pde.getFormCode(),
							rule.getName(), rule.getUser(), id));
					List<Integer> list = new ArrayList<Integer>();
					list.add(rule.getRuleId());
					return list;
				}
			}
		}
		return new ArrayList<Integer>();
	}

	public List<Integer> getUserIdsByPositionId(int id, ProcessDataEntity pde) {
		ApprovePositionEntity ape = ApprovePositionService.getInstance().getPosition(id);
		return getUserIdsByPosition(ape, pde);
	}

	public static List<Integer> getUserIdsByPosition(ApprovePositionEntity ape, ProcessDataEntity pde) {
		if (ape.getType() == 1) {
			Log4jHelper.LOGGER.info(String.format("[%s]->开始通过岗位[%s]的角色获取人员->positionId=%d", pde.getFormCode(),
					ape.getName(), ape.getId()));
			return RoleService.getInstance().getUserIdsByRoleId(ape.getRule() + "");
		} else if (ape.getType() == 2) {
			Log4jHelper.LOGGER.info(String.format("[%s]->开始通过岗位[%s]的规则获取人员->positionId=%d", pde.getFormCode(),
					ape.getName(), ape.getId()));
			return getUserIdsByPositionIdWithRule(ape.getRule(), pde);
		}
		return new ArrayList<Integer>();
	}

	public static boolean calculateCondition(List<ConditionEntity> acs, ProcessDataEntity pde) {
		String bools = "1==1 ";
		int i = 0;
		boolean isLevelO2Start = false;
		boolean isLevelO2End = false;
		boolean isPreLevel01 = true;
		boolean isCurLevle01 = false;
		for (ConditionEntity ac : acs) {
			Log4jHelper.LOGGER.info(String.format("[%s]->%s->updateBy=%d->instId=%s->acId=%d", pde.getFormCode(),
					"开始计算条件" + i, pde.getUpdateBy(), pde.getProcinstId(), ac.getId()));
			boolean isPass = ConditionUtil.calculateCondition(ac, pde);
			String op = "";
			String levelLeft = " ";
			String levelRight = " ";
			if (ac.getLevel() == 1) {
				isCurLevle01 = true;

				if (isPreLevel01 && !isCurLevle01) {
					isLevelO2Start = true;
				} else {
					isLevelO2Start = false;
				}
				if (isCurLevle01 && !isPreLevel01) {
					isLevelO2End = true;
				} else {
					isLevelO2End = false;
				}

				isPreLevel01 = true;
			} else {
				isCurLevle01 = false;

				if (isPreLevel01 && !isCurLevle01) {
					isLevelO2Start = true;
				} else {
					isLevelO2Start = false;
				}
				if (isCurLevle01 && !isPreLevel01) {
					isLevelO2End = true;
				} else {
					isLevelO2End = false;
				}

				isPreLevel01 = false;
			}

			if (isLevelO2Start) {
				levelLeft = " ( ";
				if (i == acs.size() - 1) {
					levelRight = " ) ";
				}
			}
			if (isLevelO2End) {
				levelRight = " ) ";
			}
			if (ac.getBoolOperation() == 1 || i == 0) {
				op = " && ";
			} else {
				op = " || ";
			}
			if (i == acs.size() - 1 && !isPreLevel01) {
				bools += op + levelLeft + isPass + levelRight;
			} else {
				bools += levelRight + op + levelLeft + isPass;
			}
			i++;
		}
		try {
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByName("js");
			boolean result = (boolean) engine.eval(bools);
			Log4jHelper.LOGGER.info(String.format("[%s]->%s:%s->%s:%s->updateBy=%d->instId=%s", pde.getFormCode(),
					"计算条件的表达式为", bools, "结果为", result + "", pde.getUpdateBy(), pde.getProcinstId()));
			return result;
		} catch (ScriptException e) {
			Log4jHelper.LOGGER.error(String.format("[%s]->%s:%s->%s:%s->updateBy=%d->instId=%s", pde.getFormCode(),
					"计算条件的表达式为", bools, "结果为", "error", pde.getUpdateBy(), pde.getProcinstId()), e);
			return false;
		}
	}

	private static boolean calculateCondition(ConditionEntity ac, ProcessDataEntity pde) {
		Object var1 = "";
		if (ac.getVar1From() == 1) {// 文本
			var1 = ac.getVar1();
			Log4jHelper.LOGGER
					.info(String.format("[%s]->变量1->acId=%d->变量值为文本{%s}", pde.getFormCode(), ac.getId(), var1));
		} else if (ac.getVar1From() == 2) {// xpath
			try {
				var1 = XmlUtil.selectSingleText(pde.getBizData(), ac.getVar1());
				Log4jHelper.LOGGER.info(String.format("[%s]->变量1->acId=%d->变量值为xpath:{%s=%s}", pde.getFormCode(),
						ac.getId(), ac.getVar1(), var1));
			} catch (Exception e) {
				Log4jHelper.LOGGER.error(String.format("[%s]->变量1->acId=%d->变量值为xpath{%s=error}", pde.getFormCode(),
						ac.getId(), ac.getVar1()), e);
			}
		} else if (ac.getVar1From() == 3) {// 函数
			var1 = FunctionService.getInstance().executeGetUserIds(ac.getVar1(), pde);
			Log4jHelper.LOGGER.info(String.format("[%s]->变量1->acId=%d->变量值为函数:{%s=%s}", pde.getFormCode(), ac.getId(),
					ac.getVar1(), var1));
		}

		Object var2 = "";
		if (ac.getVar2From() == 1) {// 文本
			var2 = ac.getVar2();
			Log4jHelper.LOGGER
					.info(String.format("[%s]->变量2->acId=%d->变量值为文本{%s}", pde.getFormCode(), ac.getId(), var2));
		} else if (ac.getVar2From() == 2) {// xpath
			try {
				var2 = XmlUtil.selectSingleText(pde.getBizData(), ac.getVar2());
				Log4jHelper.LOGGER.info(String.format("[%s]->变量2->acId=%d->变量值为xpath:{%s=%s}", pde.getFormCode(),
						ac.getId(), ac.getVar2(), var2));
			} catch (Exception e) {
				Log4jHelper.LOGGER.error(String.format("[%s]->变量2->acId=%d->变量值为xpath{%s=error}", pde.getFormCode(),
						ac.getId(), ac.getVar2()), e);
			}
		} else if (ac.getVar2From() == 3) {// 函数
			var2 = FunctionService.getInstance().executeGetUserIds(ac.getVar2(), pde);
			Log4jHelper.LOGGER.info(String.format("[%s]->变量2->acId=%d->变量值为函数:{%s=%s}", pde.getFormCode(), ac.getId(),
					ac.getVar2(), var2));
		}

		// 字符串
		if (ac.getVarType() == 1) {
			String var11 = var1.toString();
			String var22 = var2.toString();
			if (ac.getOperation() == 1) {
				if (var11.equals(var22)) {
					Log4jHelper.LOGGER
							.info(String.format("[%s]->变量类型[字符串],%s==%s,结果为true", pde.getFormCode(), var11, var22));
					return true;
				} else {
					Log4jHelper.LOGGER
							.info(String.format("[%s]->变量类型[字符串],%s==%s,结果为false", pde.getFormCode(), var11, var22));
				}
			} else {
				if (!var11.equals(var22)) {
					Log4jHelper.LOGGER
							.info(String.format("[%s]->变量类型[字符串],%s!=%s,结果为true", pde.getFormCode(), var11, var22));
					return true;
				} else {
					Log4jHelper.LOGGER
							.info(String.format("[%s]->变量类型[字符串],%s!=%s,结果为false", pde.getFormCode(), var11, var22));
				}
			}
		}
		// 整数
		else if (ac.getVarType() == 2) {
			int var11 = Integer.parseInt(var1.toString());
			int var22 = Integer.parseInt(var2.toString());
			if (ac.getOperation() == 1) {
				if (var11 == var22) {
					Log4jHelper.LOGGER.info(
							String.format("[%s]->变量类型[整数],%s==%s,结果为true", pde.getFormCode(), var11 + "", var22 + ""));
					return true;
				} else {
					Log4jHelper.LOGGER.info(
							String.format("[%s]->变量类型[整数],%s==%s,结果为false", pde.getFormCode(), var11 + "", var22 + ""));
				}
			} else if (ac.getOperation() == 2) {
				if (var11 > var22) {
					Log4jHelper.LOGGER.info(
							String.format("[%s]->变量类型[整数],%s>%s,结果为true", pde.getFormCode(), var11 + "", var22 + ""));
					return true;
				} else {
					Log4jHelper.LOGGER.info(
							String.format("[%s]->变量类型[整数],%s>%s,结果为false", pde.getFormCode(), var11 + "", var22 + ""));
				}
			} else if (ac.getOperation() == 3) {
				if (var11 >= var22) {
					Log4jHelper.LOGGER.info(
							String.format("[%s]->变量类型[整数],%s>=%s,结果为true", pde.getFormCode(), var11 + "", var22 + ""));
					return true;
				} else {
					Log4jHelper.LOGGER.info(
							String.format("[%s]->变量类型[整数],%s>=%s,结果为false", pde.getFormCode(), var11 + "", var22 + ""));
				}
			} else if (ac.getOperation() == 4) {
				if (var11 < var22) {
					Log4jHelper.LOGGER.info(
							String.format("[%s]->变量类型[整数],%s<%s,结果为true", pde.getFormCode(), var11 + "", var22 + ""));
					return true;
				} else {
					Log4jHelper.LOGGER.info(
							String.format("[%s]->变量类型[整数],%s<%s,结果为false", pde.getFormCode(), var11 + "", var22 + ""));
				}
			} else if (ac.getOperation() == 5) {
				if (var11 <= var22) {
					Log4jHelper.LOGGER.info(
							String.format("[%s]->变量类型[整数],%s<=%s,结果为true", pde.getFormCode(), var11 + "", var22 + ""));
					return true;
				} else {
					Log4jHelper.LOGGER.info(
							String.format("[%s]->变量类型[整数],%s<=%s,结果为false", pde.getFormCode(), var11 + "", var22 + ""));
				}
			} else if (ac.getOperation() == 6) {
				if (var11 != var22) {
					Log4jHelper.LOGGER.info(
							String.format("[%s]->变量类型[整数],%s!=%s,结果为true", pde.getFormCode(), var11 + "", var22 + ""));
					return true;
				} else {
					Log4jHelper.LOGGER.info(
							String.format("[%s]->变量类型[整数],%s!=%s,结果为false", pde.getFormCode(), var11 + "", var22 + ""));
				}
			}
		}
		// 浮点
		else if (ac.getVarType() == 3) {
			float var11 = Float.parseFloat(var1.toString());
			float var22 = Float.parseFloat(var2.toString());
			if (ac.getOperation() == 1) {
				if (var11 == var22) {
					Log4jHelper.LOGGER.info(
							String.format("[%s]->变量类型[浮点],%s==%s,结果为true", pde.getFormCode(), var11 + "", var22 + ""));
					return true;
				} else {
					Log4jHelper.LOGGER.info(
							String.format("[%s]->变量类型[浮点],%s==%s,结果为false", pde.getFormCode(), var11 + "", var22 + ""));
				}
			} else if (ac.getOperation() == 2) {
				if (var11 > var22) {
					Log4jHelper.LOGGER.info(
							String.format("[%s]->变量类型[浮点],%s>%s,结果为true", pde.getFormCode(), var11 + "", var22 + ""));
					return true;
				} else {
					Log4jHelper.LOGGER.info(
							String.format("[%s]->变量类型[浮点],%s>%s,结果为false", pde.getFormCode(), var11 + "", var22 + ""));
				}
			} else if (ac.getOperation() == 3) {
				if (var11 >= var22) {
					Log4jHelper.LOGGER.info(
							String.format("[%s]->变量类型[浮点],%s>=%s,结果为true", pde.getFormCode(), var11 + "", var22 + ""));
					return true;
				} else {
					Log4jHelper.LOGGER.info(
							String.format("[%s]->变量类型[浮点],%s>=%s,结果为false", pde.getFormCode(), var11 + "", var22 + ""));
				}
			} else if (ac.getOperation() == 4) {
				if (var11 < var22) {
					Log4jHelper.LOGGER.info(
							String.format("[%s]->变量类型[浮点],%s<%s,结果为true", pde.getFormCode(), var11 + "", var22 + ""));
					return true;
				} else {
					Log4jHelper.LOGGER.info(
							String.format("[%s]->变量类型[浮点],%s<%s,结果为false", pde.getFormCode(), var11 + "", var22 + ""));
				}
			} else if (ac.getOperation() == 5) {
				if (var11 <= var22) {
					Log4jHelper.LOGGER.info(
							String.format("[%s]->变量类型[浮点],%s<=%s,结果为true", pde.getFormCode(), var11 + "", var22 + ""));
					return true;
				} else {
					Log4jHelper.LOGGER.info(
							String.format("[%s]->变量类型[浮点],%s<=%s,结果为false", pde.getFormCode(), var11 + "", var22 + ""));
				}
			} else if (ac.getOperation() == 6) {
				if (var11 != var22) {
					Log4jHelper.LOGGER.info(
							String.format("[%s]->变量类型[浮点],%s!=%s,结果为true", pde.getFormCode(), var11 + "", var22 + ""));
					return true;
				} else {
					Log4jHelper.LOGGER.info(
							String.format("[%s]->变量类型[浮点],%s!=%s,结果为false", pde.getFormCode(), var11 + "", var22 + ""));
				}
			}
		}
		// 日期
		else if (ac.getVarType() == 4) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date var11 = null;
			try {
				var11 = df.parse(var1.toString());
			} catch (ParseException e) {
				Log4jHelper.LOGGER.error(String.format("[%s]->变量1,类型[日期],解析出错,%s", pde.getFormCode(), var11 + ""), e);
			}
			Date var22 = null;
			try {
				var22 = df.parse(var2.toString());
			} catch (ParseException e) {
				Log4jHelper.LOGGER.error(String.format("[%s]->变量2,类型[日期],解析出错,%s", pde.getFormCode(), var22 + ""), e);
			}
			if (ac.getOperation() == 1) {
				if (var11.getTime() == var22.getTime()) {
					Log4jHelper.LOGGER.info(
							String.format("[%s]->变量类型[日期],%s==%s,结果为true", pde.getFormCode(), var11 + "", var22 + ""));
					return true;
				} else {
					Log4jHelper.LOGGER.info(
							String.format("[%s]->变量类型[日期],%s==%s,结果为false", pde.getFormCode(), var11 + "", var22 + ""));
				}
			} else if (ac.getOperation() == 2) {
				if (var11.getTime() > var22.getTime()) {
					Log4jHelper.LOGGER.info(
							String.format("[%s]->变量类型[日期],%s>%s,结果为true", pde.getFormCode(), var11 + "", var22 + ""));
					return true;
				} else {
					Log4jHelper.LOGGER.info(
							String.format("[%s]->变量类型[日期],%s>%s,结果为false", pde.getFormCode(), var11 + "", var22 + ""));
				}
			} else if (ac.getOperation() == 3) {
				if (var11.getTime() >= var22.getTime()) {
					Log4jHelper.LOGGER.info(
							String.format("[%s]->变量类型[日期],%s>=%s,结果为true", pde.getFormCode(), var11 + "", var22 + ""));
					return true;
				} else {
					Log4jHelper.LOGGER.info(
							String.format("[%s]->变量类型[日期],%s>=%s,结果为false", pde.getFormCode(), var11 + "", var22 + ""));
				}
			} else if (ac.getOperation() == 4) {
				if (var11.getTime() < var22.getTime()) {
					Log4jHelper.LOGGER.info(
							String.format("[%s]->变量类型[日期],%s<%s,结果为true", pde.getFormCode(), var11 + "", var22 + ""));
					return true;
				} else {
					Log4jHelper.LOGGER.info(
							String.format("[%s]->变量类型[日期],%s<%s,结果为false", pde.getFormCode(), var11 + "", var22 + ""));
				}
			} else if (ac.getOperation() == 5) {
				if (var11.getTime() <= var22.getTime()) {
					Log4jHelper.LOGGER.info(
							String.format("[%s]->变量类型[日期],%s<=%s,结果为true", pde.getFormCode(), var11 + "", var22 + ""));
					return true;
				} else {
					Log4jHelper.LOGGER.info(
							String.format("[%s]->变量类型[日期],%s<=%s,结果为false", pde.getFormCode(), var11 + "", var22 + ""));
				}
			} else if (ac.getOperation() == 6) {
				if (var11 != var22) {
					Log4jHelper.LOGGER.info(
							String.format("[%s]->变量类型[日期],%s!=%s,结果为true", pde.getFormCode(), var11 + "", var22 + ""));
					return true;
				} else {
					Log4jHelper.LOGGER.info(
							String.format("[%s]->变量类型[日期],%s!=%s,结果为false", pde.getFormCode(), var11 + "", var22 + ""));
				}
			}
		}
		return false;
	}
}
