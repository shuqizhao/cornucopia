package cornucopia.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cornucopia.dao.ApprovePositionDao;
import cornucopia.entity.ApprovePositionEntity;
import cornucopia.entity.RuleConditionEntity;
import cornucopia.entity.RuleEntity;
import cornucopia.model.ApprovePositionViewModel;
import cornucopia.util.MyBatisHelper;

public class ApprovePositionService {
	private static ApprovePositionService instance = new ApprovePositionService();

	private ApprovePositionService() {
	}

	public static ApprovePositionService getInstance() {
		return instance;
	}

	ApprovePositionDao posdao = MyBatisHelper.getMapper(ApprovePositionDao.class);

	public List<ApprovePositionEntity> getAll(int processNodeId) {
		return posdao.getAll(processNodeId);
	}

	public List<ApprovePositionViewModel> getAllWithViewModel(int processNodeId) {
		return posdao.getAllWithViewModel(processNodeId);
	}

	public int insert(ApprovePositionEntity approvePositionEntity) {
		return posdao.insert(approvePositionEntity);
	}

	public int disable(int id) {
		return posdao.disable(id);
	}

	public int enable(int id) {
		return posdao.enable(id);
	}

	public int delete(int id) {
		return posdao.delete(id);
	}

	public ApprovePositionEntity getPosition(int id) {
		return posdao.getPosition(id);
	}

	public int update(ApprovePositionEntity pos) {
		return posdao.update(pos);
	}

	public List<Integer> getUserIdsByPositionIdWithRole(int id) {
		return posdao.getUserIdsByPositionId(id);
	}

	/**
	 * 获取审批人
	 * 
	 * @param id
	 * @param bizData
	 * @return
	 */
	public List<Integer> getUserIdsByPositionIdWithRule(int id, String bizData) {
		List<RuleEntity> rules = RuleService.getInstance().getRuleChildren(id);
		if (rules == null || rules.size() == 0) {
			// ex
		}
		for (RuleEntity rule : rules) {
			List<RuleConditionEntity> rcs = RuleService.getInstance().getRuleConditions(rule.getId());
			if (rcs == null || rcs.size() == 0) {
				// ex
			}
			boolean cBool = true;
			for (RuleConditionEntity rc : rcs) {
				if (calculateCondition(rc, bizData)) {
					cBool = cBool && true;
				} else {
					cBool = cBool && false;
				}
			}
			if (cBool) {
				// 角色
				if (rule.getType() == 1) {
					return RoleService.getInstance().getUserIdsByRoleId(rule.getUser());
				} else if (rule.getType() == 2) {// 函数
					return FunctionService.getInstance().executeGetUserIds(rule.getUser(), bizData);
				} else if (rule.getType() == 3) {// 指定人
					List<Integer> list = new ArrayList<Integer>();
					list.add(rule.getRuleId());
					return list;
				} else if (rule.getType() == 4) {// 跳过
					List<Integer> list = new ArrayList<Integer>();
					list.add(rule.getRuleId());
					return list;
				}
			}
		}
		return new ArrayList<Integer>();
	}

	public List<Integer> getUserIdsByPositionId(int id, String bizData) {
		ApprovePositionEntity ape = getPosition(id);
		if (ape.getType() == 1) {
			return RoleService.getInstance().getUserIdsByRoleId(ape.getRule() + "");
		} else if (ape.getType() == 2) {
			return getUserIdsByPositionIdWithRule(ape.getRule(), bizData);
		}
		return new ArrayList<Integer>();
	}

	public boolean calculateCondition(RuleConditionEntity rc, String bizData) {
		Object var1 = "";
		if (rc.getVar1From() == 1) {// 文本
			var1 = rc.getVar1();
		} else if (rc.getVar1From() == 2) {// xpath

		} else if (rc.getVar1From() == 3) {// 函数

		}

		Object var2 = "";
		if (rc.getVar2From() == 1) {// 文本
			var2 = rc.getVar1();
		} else if (rc.getVar2From() == 2) {// xpath

		} else if (rc.getVar2From() == 3) {// 函数

		}

		// 字符串
		if (rc.getVar1Type() == 1) {
			String var11 = var1.toString();
			String var22 = var2.toString();
			if (rc.getOperation() == 1) {
				if (var11.equals(var22)) {
					return true;
				}
			} else {
				if (!var11.equals(var22)) {
					return true;
				}
			}
		}
		// 整数
		else if (rc.getVar1Type() == 2) {
			int var11 = Integer.parseInt(var1.toString());
			int var22 = Integer.parseInt(var2.toString());
			if (rc.getOperation() == 1) {
				if (var11 == var22) {
					return true;
				}
			} else if (rc.getOperation() == 2) {
				if (var11 > var22) {
					return true;
				}
			} else if (rc.getOperation() == 3) {
				if (var11 >= var22) {
					return true;
				}
			} else if (rc.getOperation() == 4) {
				if (var11 < var22) {
					return true;
				}
			} else if (rc.getOperation() == 5) {
				if (var11 <= var22) {
					return true;
				}
			} else if (rc.getOperation() == 6) {
				if (var11 != var22) {
					return true;
				}
			}
		}
		// 浮点
		else if (rc.getVar1Type() == 3) {
			float var11 = Float.parseFloat(var1.toString());
			float var22 = Float.parseFloat(var2.toString());
			if (rc.getOperation() == 1) {
				if (var11 == var22) {
					return true;
				}
			} else if (rc.getOperation() == 2) {
				if (var11 > var22) {
					return true;
				}
			} else if (rc.getOperation() == 3) {
				if (var11 >= var22) {
					return true;
				}
			} else if (rc.getOperation() == 4) {
				if (var11 < var22) {
					return true;
				}
			} else if (rc.getOperation() == 5) {
				if (var11 <= var22) {
					return true;
				}
			} else if (rc.getOperation() == 6) {
				if (var11 != var22) {
					return true;
				}
			}
		}
		// 日期
		else if (rc.getVar1Type() == 4) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date var11 = null;
			try {
				var11 = df.parse(var1.toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Date var22 = null;
			try {
				var22 = df.parse(var2.toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if (rc.getOperation() == 1) {
				if (var11.getTime() == var22.getTime()) {
					return true;
				}
			} else if (rc.getOperation() == 2) {
				if (var11.getTime() > var22.getTime()) {
					return true;
				}
			} else if (rc.getOperation() == 3) {
				if (var11.getTime() >= var22.getTime()) {
					return true;
				}
			} else if (rc.getOperation() == 4) {
				if (var11.getTime() < var22.getTime()) {
					return true;
				}
			} else if (rc.getOperation() == 5) {
				if (var11.getTime() <= var22.getTime()) {
					return true;
				}
			} else if (rc.getOperation() == 6) {
				if (var11 != var22) {
					return true;
				}
			}
		}
		return false;
	}
}
