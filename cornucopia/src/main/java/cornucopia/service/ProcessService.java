package cornucopia.service;

import java.io.UnsupportedEncodingException;
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
import org.dom4j.DocumentException;

import cornucopia.dao.ProcessDao;
import cornucopia.entity.ApproveConditionEntity;
import cornucopia.entity.ApproveMatrixEntity;
import cornucopia.entity.ApprovePositionEntity;
import cornucopia.entity.ProcessDataEntity;
import cornucopia.entity.ProcessDiagramEntity;
import cornucopia.entity.ProcessEntity;
import cornucopia.entity.ProcessInstDiagramEntity;
import cornucopia.entity.ProcessNodeEntity;
import cornucopia.util.ActivitiHelper;
import cornucopia.util.Log4jHelper;
import cornucopia.util.MyBatisHelper;
import cornucopia.util.XmlUtil;

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

	public String StartProcess(ProcessDataEntity pde) {
		Log4jHelper.LOGGER.info(String.format("[%s]->%s->createBy=%d", pde.getFormCode(), "开始流程", pde.getCreateBy()));
		ProcessDiagramEntity pd = ProcessDiagramService.getInstance().getByProcessId(pde.getProcessId() + "");
		Log4jHelper.LOGGER.info(String.format("[%s]->defKey=%s", pde.getFormCode(), pd.getDefKey()));
		String instId = StartProcessByKey(pd.getDefKey(), pde.getCreateBy());
		Log4jHelper.LOGGER.info(String.format("[%s]->instId=%s", pde.getFormCode(), instId));
		pde.setProcinstId(instId);
		pde.setLevelCount(0);
		Complete(pde);
		return instId;
	}

	private String StartProcessByKey(String key, int userId) {
		RuntimeService rs = ActivitiHelper.GetEngine().getRuntimeService();
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("dealUser", userId);
		variables.put("isWhile", "1");
		ProcessInstance pi = rs.startProcessInstanceByKey(key, variables);
		String instId = pi.getProcessInstanceId();
		return instId;
	}

	public void Return(ProcessDataEntity pde) {
		Log4jHelper.LOGGER.info(String.format("[%s]->%s->updateBy=%d->instId=%s", pde.getFormCode(), "退回任务",
				pde.getUpdateBy(), pde.getProcinstId()));
		TaskQuery query = ActivitiHelper.GetEngine().getTaskService().createTaskQuery();
		query.taskAssignee(pde.getUpdateBy() + "");
		query.processInstanceId(pde.getProcinstId());
		Task task = query.singleResult();
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("to", "2");
		variables.put("dealUser", pde.getCreateBy());
		variables.put("condition", 0);
		Log4jHelper.LOGGER.info(String.format("[%s]->taskId=%s", pde.getFormCode(), task.getId()));
		ActivitiHelper.GetEngine().getTaskService().complete(task.getId(), variables);
		Log4jHelper.LOGGER.info(String.format("[%s]->%s", pde.getFormCode(), "引擎退回完成"));
	}

	public void Complete(ProcessDataEntity pde) {
		Log4jHelper.LOGGER.info(String.format("[%s]->%s->updateBy=%d->instId=%s", pde.getFormCode(), "完成任务",
				pde.getUpdateBy(), pde.getProcinstId()));
		List<Integer> nextUserIds = getNextDealUser(pde);
		if (nextUserIds == null || nextUserIds.size() == 0) {
			Log4jHelper.LOGGER.error(String.format("[%s]->%s->updateBy=%d->instId=%s", pde.getFormCode(), "找不到人",
					pde.getUpdateBy(), pde.getProcinstId()));
			return;
		}
		TaskQuery query = ActivitiHelper.GetEngine().getTaskService().createTaskQuery();
		query.taskAssignee(pde.getUpdateBy() + "");
		query.processInstanceId(pde.getProcinstId());
		Task task = query.singleResult();
		Log4jHelper.LOGGER.info(String.format("[%s]->taskId=%s", pde.getFormCode(), task.getId()));
		Map<String, Object> variables = new HashMap<String, Object>();
		for (int nextUserId : nextUserIds) {
			if (nextUserId == 666666) {
				variables.put("to", "0");
			} else {
				variables.put("to", "1");
			}
		}
		variables.put("assigneeList", nextUserIds);
		variables.put("condition", pde.getCondition());
		ActivitiHelper.GetEngine().getTaskService().complete(task.getId(), variables);
		Log4jHelper.LOGGER.info(String.format("[%s]->%s->taskId=%s", pde.getFormCode(), "引擎任务完成", task.getId()));
	}

	public List<Integer> getNextDealUser(ProcessDataEntity pde) {
		Log4jHelper.LOGGER.info(String.format("[%s]->%s->updateBy=%d->instId=%s", pde.getFormCode(), "获取审批人员",
				pde.getUpdateBy(), pde.getProcinstId()));
		TaskQuery query = ActivitiHelper.GetEngine().getTaskService().createTaskQuery();
		query.processInstanceId(pde.getProcinstId());
		Task task = query.singleResult();
		String taskName = task.getName();
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
		int i = 0;
		for (ApproveMatrixEntity am : ams) {
			if (pde.getLevelCount() == i) {
				int c1Id = am.getApproveCondition1Id();
				List<ApproveConditionEntity> acs = ApproveService.getInstance().getConditions(c1Id);
				if (acs == null || acs.size() == 0) {
					Log4jHelper.LOGGER.error(String.format("[%s]->%s->updateBy=%d->instId=%s->c1Id=%d",
							pde.getFormCode(), "未能获取审批条件c1", pde.getUpdateBy(), pde.getProcinstId(), c1Id));
					return null;
				}
				boolean c1Bool = true;
				for (ApproveConditionEntity ac : acs) {
					if (calculateApproveCondition(ac, pde.getBizData())) {
						c1Bool = c1Bool && true;
					} else {
						c1Bool = c1Bool && false;
					}
				}
				if (c1Bool) {
					int c2Id = am.getApproveCondition2Id();
					List<ApproveConditionEntity> bacs = ApproveService.getInstance().getConditions(c2Id);
					if (bacs == null || bacs.size() == 0) {
						Log4jHelper.LOGGER.error(String.format("[%s]->%s->updateBy=%d->instId=%s->c2Id=%d",
								pde.getFormCode(), "未能获取审批条件c2", pde.getUpdateBy(), pde.getProcinstId(), c2Id));
						return null;
					}
					boolean c2Bool = true;
					for (ApproveConditionEntity ac : bacs) {
						if (calculateApproveCondition(ac, pde.getBizData())) {
							c2Bool = c2Bool && true;
						} else {
							c2Bool = c2Bool && false;
						}
					}
					if (c2Bool) {
						int positionId = am.getApprovePositionId();
						ApprovePositionEntity ape = ApprovePositionService.getInstance().getPosition(positionId);
						pde.setStepName(ape.getVitualTitle());
						pde.setCondition(ape.getApproveType());
						return ApprovePositionService.getInstance().getUserIdsByPosition(ape, pde.getBizData());
					}
				}
			}
			i++;
		}
		if (pde.getLevelCount() == ams.size() - 1) {
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

	public void buildProcessInstDiagram(ProcessDataEntity processDataEntity) {
		String instId = processDataEntity.getProcinstId();
		String processId = processDataEntity.getProcessId() + "";
		int processDataId = processDataEntity.getId();
		String bizData = processDataEntity.getBizData();
		TaskQuery query = ActivitiHelper.GetEngine().getTaskService().createTaskQuery();
		query.processInstanceId(instId);
		Task task = query.singleResult();
		String taskName = task.getName();
		String currentUserId = task.getAssignee();
		ProcessNodeEntity pne = ProcessNodeService.getInstance().getByName(processId, taskName);
		if (pne == null || !pne.getName().contains("DOA")) {
			pne = ProcessNodeService.getInstance().getDoaNode(processId);
			if (pne == null) {
				// ex
			}
		}
		int processNodeId = pne.getId();

		List<ApproveMatrixEntity> ams = ApproveMatrixService.getInstance().getByNodeId(processNodeId);
		if (ams == null || ams.size() == 0) {
			// thow ex
		}

		ProcessInstDiagramService.getInstance().delete(processDataId);
		ProcessInstDiagramEntity pide = new ProcessInstDiagramEntity();
		pide.setProcessDataId(processDataId);
		pide.setProcessId(processDataEntity.getProcessId());
		pide.setName("发起申请");
		pide.setUserId(processDataEntity.getCreateBy());
		pide.setLevelCount(-1);
		ProcessInstDiagramService.getInstance().insert(pide);

		int i = 0;
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
					ApprovePositionEntity ape = ApprovePositionService.getInstance().getPosition(positionId);
					processDataEntity.setStepName(ape.getVitualTitle());
					processDataEntity.setCondition(ape.getApproveType());
					List<Integer> userIds = ApprovePositionService.getInstance().getUserIdsByPosition(ape,
							processDataEntity.getBizData());
					String jobTitle = ape.getVitualTitle();
					for (int userId : userIds) {
						ProcessInstDiagramEntity processInstDiagramEntity = new ProcessInstDiagramEntity();
						processInstDiagramEntity.setProcessDataId(processDataId);
						processInstDiagramEntity.setProcessId(processDataEntity.getProcessId());
						processInstDiagramEntity.setName(jobTitle);
						processInstDiagramEntity.setUserId(userId);
						processInstDiagramEntity.setLevelCount(i);
						if (currentUserId.equals(userId + "")) {
							processInstDiagramEntity.setIsCurrent(1);
						}
						ProcessInstDiagramService.getInstance().insert(processInstDiagramEntity);
					}
				}
			}
			i++;
		}
	}

	public boolean calculateApproveCondition(ApproveConditionEntity ac, String bizData) {
		Object var1 = "";
		if (ac.getVar1From() == 1) {// 文本
			var1 = ac.getVar1();
		} else if (ac.getVar1From() == 2) {// xpath
			try {
				var1 = XmlUtil.selectSingleText(bizData, ac.getVar1());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		} else if (ac.getVar1From() == 3) {// 函数
			var1 = FunctionService.getInstance().executeGetUserIds(ac.getVar1(), bizData);
		}

		Object var2 = "";
		if (ac.getVar2From() == 1) {// 文本
			var2 = ac.getVar1();
		} else if (ac.getVar2From() == 2) {// xpath
			try {
				var2 = XmlUtil.selectSingleText(bizData, ac.getVar2());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		} else if (ac.getVar2From() == 3) {// 函数
			var2 = FunctionService.getInstance().executeGetUserIds(ac.getVar2(), bizData);
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
