package cornucopia.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;

import cornucopia.dao.ProcessDao;
import cornucopia.entity.ApproveConditionEntity;
import cornucopia.entity.ApproveMatrixEntity;
import cornucopia.entity.ProcessDataEntity;
import cornucopia.entity.ProcessDiagramEntity;
import cornucopia.entity.ProcessEntity;
import cornucopia.entity.ProcessNodeEntity;
import cornucopia.util.ActivitiHelper;
import cornucopia.util.MyBatisHelper;

public class ProcessService {
	private static ProcessService instance = new ProcessService();

	private ProcessService() {
	}

	public static ProcessService getInstance() {
		return instance;
	}

	ProcessDao processDao = MyBatisHelper.getMapper(ProcessDao.class);

	public List<ProcessEntity> getAllProcess() {
		return processDao.getAllProcess();
	}

	public int exists(String processName) {
		return processDao.exists(processName);
	}

	public int insert(ProcessEntity processEntity) {
		return processDao.insert(processEntity);
	}

	public int disable(int id) {
		return processDao.disable(id);
	}

	public int enable(int id) {
		return processDao.enable(id);
	}

	public int delete(int id) {
		return processDao.delete(id);
	}

	public ProcessEntity get(String processId) {
		return processDao.get(processId);
	}

	public String getPre(String processId) {
		return get(processId).getPre();
	}

	public String StartByProcessId(String processId, String userId, String bizData) {
		ProcessDiagramEntity pd = ProcessDiagramService.getInstance().getByProcessId(processId);
		String instId = Start(pd.getDefKey(), userId);
		Complete(processId, instId, userId, bizData);
		return instId;
	}

	public String Start(String key, String userId) {
		RuntimeService rs = ActivitiHelper.GetEngine().getRuntimeService();
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("dealUser", userId);
		variables.put("isWhile", "1");
		ProcessInstance pi = rs.startProcessInstanceByKey(key, variables);
		String instId = pi.getProcessInstanceId();
		return instId;
	}

	public void Complete(String instId, String userId, String bizData) {
		ProcessDataEntity pde = ProcessDataService.getInstance().getByInstId(instId);
		String processId = pde.getProcessId() + "";
		Complete(processId, instId, userId, bizData);
	}

	public void Complete(String processId, String instId, String userId, String bizData) {
		TaskQuery query = ActivitiHelper.GetEngine().getTaskService().createTaskQuery();
		query.taskAssignee(userId);
		query.processInstanceId(instId);
		Task task = query.singleResult();
		List<Integer> nextUserIds = getNextDealUser(processId, instId, bizData);
		Map<String, Object> variables = new HashMap<String, Object>();
		for (int nextUserId : nextUserIds) {
			variables.put("dealUser", nextUserId);
		}
		variables.put("isWhile", "1");
		ActivitiHelper.GetEngine().getTaskService().complete(task.getId(), variables);
	}

	public List<Integer> getNextDealUser(String processId, String instId, String bizData) {
		TaskQuery query = ActivitiHelper.GetEngine().getTaskService().createTaskQuery();
		query.processInstanceId(instId);
		Task task = query.singleResult();
		String taskName = task.getName();
		ProcessNodeEntity pne = ProcessNodeService.getInstance().getByName(processId, taskName);
		int processNodeId = pne.getId();

		List<ApproveMatrixEntity> ams = ApproveMatrixService.getInstance().getByNodeId(processNodeId);
		if (ams == null || ams.size() == 0) {
			// thow ex
		}
		for (ApproveMatrixEntity am : ams) {
			int c1Id = am.getApproveCondition1Id();
			List<ApproveConditionEntity> acs = ApproveService.getInstance().getConditions(c1Id);
			if (acs == null || acs.size() == 0) {
				// thow ex
			}
			boolean c1Bool = true;
			for (ApproveConditionEntity ac : acs) {
				if (calculateApproveCondition(ac, bizData)) {
					c1Bool = c1Bool && true;
				} else {
					c1Bool = c1Bool && false;
				}
			}
			if (c1Bool) {
				int c2Id = am.getApproveCondition2Id();
				List<ApproveConditionEntity> bacs = ApproveService.getInstance().getConditions(c2Id);
				if (bacs == null || bacs.size() == 0) {
					// thow ex
				}
				boolean c2Bool = true;
				for (ApproveConditionEntity ac : bacs) {
					if (calculateApproveCondition(ac, bizData)) {
						c2Bool = c2Bool && true;
					} else {
						c2Bool = c2Bool && false;
					}
				}
				if (c2Bool) {
					int positionId = am.getApprovePositionId();
					return ApprovePositionService.getInstance().getUserIdsByPositionId(positionId, bizData);
				}
			}
		}
		return new ArrayList<Integer>();
	}

	public boolean calculateApproveCondition(ApproveConditionEntity ac, String bizData) {
		Object var1 = "";
		if (ac.getVar1From() == 1) {// 文本
			var1 = ac.getVar1();
		} else if (ac.getVar1From() == 2) {// xpath

		} else if (ac.getVar1From() == 3) {// 函数

		}

		Object var2 = "";
		if (ac.getVar2From() == 1) {// 文本
			var2 = ac.getVar1();
		} else if (ac.getVar2From() == 2) {// xpath

		} else if (ac.getVar2From() == 3) {// 函数

		}

		// 字符串
		if (ac.getVar1Type() == 1) {
			String var11 = var1.toString();
			String var22 = var2.toString();
			if (ac.getOperation() == 1) {
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
		else if (ac.getVar1Type() == 2) {
			int var11 = Integer.parseInt(var1.toString());
			int var22 = Integer.parseInt(var2.toString());
			if (ac.getOperation() == 1) {
				if (var11 == var22) {
					return true;
				}
			} else if (ac.getOperation() == 2) {
				if (var11 > var22) {
					return true;
				}
			} else if (ac.getOperation() == 3) {
				if (var11 >= var22) {
					return true;
				}
			} else if (ac.getOperation() == 4) {
				if (var11 < var22) {
					return true;
				}
			} else if (ac.getOperation() == 5) {
				if (var11 <= var22) {
					return true;
				}
			} else if (ac.getOperation() == 6) {
				if (var11 != var22) {
					return true;
				}
			}
		}
		// 浮点
		else if (ac.getVar1Type() == 3) {
			float var11 = Float.parseFloat(var1.toString());
			float var22 = Float.parseFloat(var2.toString());
			if (ac.getOperation() == 1) {
				if (var11 == var22) {
					return true;
				}
			} else if (ac.getOperation() == 2) {
				if (var11 > var22) {
					return true;
				}
			} else if (ac.getOperation() == 3) {
				if (var11 >= var22) {
					return true;
				}
			} else if (ac.getOperation() == 4) {
				if (var11 < var22) {
					return true;
				}
			} else if (ac.getOperation() == 5) {
				if (var11 <= var22) {
					return true;
				}
			} else if (ac.getOperation() == 6) {
				if (var11 != var22) {
					return true;
				}
			}
		}
		// 日期
		else if (ac.getVar1Type() == 4) {
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
			if (ac.getOperation() == 1) {
				if (var11.getTime() == var22.getTime()) {
					return true;
				}
			} else if (ac.getOperation() == 2) {
				if (var11.getTime() > var22.getTime()) {
					return true;
				}
			} else if (ac.getOperation() == 3) {
				if (var11.getTime() >= var22.getTime()) {
					return true;
				}
			} else if (ac.getOperation() == 4) {
				if (var11.getTime() < var22.getTime()) {
					return true;
				}
			} else if (ac.getOperation() == 5) {
				if (var11.getTime() <= var22.getTime()) {
					return true;
				}
			} else if (ac.getOperation() == 6) {
				if (var11 != var22) {
					return true;
				}
			}
		}
		return false;
	}
}
