package cornucopia.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;

import cornucopia.dao.ProcessDao;
import cornucopia.entity.ProcessApproveEntity;
import cornucopia.entity.ProcessDataEntity;
import cornucopia.entity.ProcessDiagramEntity;
import cornucopia.entity.ProcessEntity;
import cornucopia.entity.ProcessInstDiagramEntity;
import cornucopia.util.ActivitiHelper;
import cornucopia.util.ConditionUtil;
import cornucopia.util.Log4jHelper;
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

	public List<ProcessEntity> getAllEnableProcess() {
		return processDao.getAllEnableProcess();
	}

	public int exists(int id, String processName, String pre) {
		return processDao.exists(id, processName, pre);
	}

	public int insert(ProcessEntity processEntity) {
		return processDao.insert(processEntity);
	}

	public int update(ProcessEntity processEntity) {
		return processDao.update(processEntity);
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
		Log4jHelper.LOGGER.info(String.format("[%s]->%s->defKey=%s", pde.getFormCode(), "准备启动流程", pd.getDefKey()));
		String instId = StartProcessByKey(pd.getDefKey(), pde.getCreateBy());
		Log4jHelper.LOGGER.info(String.format("[%s]->%s->instId=%s", pde.getFormCode(), "流程启动成功", instId));
		pde.setProcinstId(instId);
		pde.setLevelCount(0);
		Complete(pde);
		return instId;
	}

	private String StartProcessByKey(String key, int userId) {
		RuntimeService rs = ActivitiHelper.GetEngine().getRuntimeService();
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("dealUser", userId);
		// variables.put("isWhile", "1");
		ProcessInstance pi = rs.startProcessInstanceByKey(key, variables);
		String instId = pi.getProcessInstanceId();
		return instId;
	}

	public void Return(ProcessDataEntity pde) {
		Log4jHelper.LOGGER.info(String.format("[%s]->%s->updateBy=%d->instId=%s", pde.getFormCode(), "退回任务",
				pde.getUpdateBy(), pde.getProcinstId()));
		TaskQuery query = ActivitiHelper.GetEngine().getTaskService().createTaskQuery();
		// query.taskAssignee(pde.getUpdateBy() + "");
		query.processInstanceId(pde.getProcinstId());
		Task task = query.singleResult();
		if (!task.getName().contains("DOA")) {
			ActivitiHelper.GetEngine().getTaskService().complete(task.getId());
		}
		TaskQuery query2 = ActivitiHelper.GetEngine().getTaskService().createTaskQuery();
		// query.taskAssignee(pde.getUpdateBy() + "");
		query2.processInstanceId(pde.getProcinstId());
		task = query2.singleResult();
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("to", "2");
		variables.put("inputUser", pde.getCreateBy());
		variables.put("condition", 0);
		Log4jHelper.LOGGER.info(String.format("[%s]->%s->taskId=%s", pde.getFormCode(), "查找到引擎任务", task.getId()));
		ActivitiHelper.GetEngine().getTaskService().complete(task.getId(), variables);
		Log4jHelper.LOGGER.info(String.format("[%s]->%s", pde.getFormCode(), "引擎任务退回完成"));
	}

	public void Complete(ProcessDataEntity pde) {
		Log4jHelper.LOGGER.info(String.format("[%s]->%s->updateBy=%d->instId=%s", pde.getFormCode(), "准备完成任务",
				pde.getUpdateBy(), pde.getProcinstId()));
		List<Integer> nextUserIds = ConditionUtil.getNextDealUser(pde);
		if (nextUserIds == null || nextUserIds.size() == 0) {
			Log4jHelper.LOGGER.error(String.format("[%s]->%s->updateBy=%d->instId=%s", pde.getFormCode(), "任务找不到人",
					pde.getUpdateBy(), pde.getProcinstId()));
			return;
		}
		Log4jHelper.LOGGER.info(String.format("[%s]->%s->人员数=%d", pde.getFormCode(), "查找到审核人员", nextUserIds.size()));
		TaskQuery query = ActivitiHelper.GetEngine().getTaskService().createTaskQuery();
		// query.taskAssignee(pde.getUpdateBy() + "");
		query.processInstanceId(pde.getProcinstId());
		Task task = query.singleResult();
		if (!task.getName().contains("DOA") && !task.getName().equals("Init")) {
			ActivitiHelper.GetEngine().getTaskService().complete(task.getId());
		}
		TaskQuery query2 = ActivitiHelper.GetEngine().getTaskService().createTaskQuery();
		// query.taskAssignee(pde.getUpdateBy() + "");
		query2.processInstanceId(pde.getProcinstId());
		task = query2.singleResult();
		Log4jHelper.LOGGER.info(String.format("[%s]->%s->taskId=%s", pde.getFormCode(), "查找到引擎任务", task.getId()));
		Map<String, Object> variables = new HashMap<String, Object>();
		for (int nextUserId : nextUserIds) {
			if (nextUserId == 666666) {
				variables.put("to", "0");
				pde.setStepName("结束");
				Log4jHelper.LOGGER.info(
						String.format("[%s]->%s->taskId=%s", pde.getFormCode(), "查找到审核人员666666,准备完成DOA", task.getId()));
			} else {
				variables.put("to", "1");
			}
		}
		variables.put("assigneeList", nextUserIds);
		variables.put("condition", pde.getCondition());
		ActivitiHelper.GetEngine().getTaskService().complete(task.getId(), variables);
		Log4jHelper.LOGGER.info(String.format("[%s]->%s->taskId=%s", pde.getFormCode(), "引擎任务完成", task.getId()));
	}

	public void Stop(ProcessDataEntity pde) {
		Log4jHelper.LOGGER.info(String.format("[%s]->%s->updateBy=%d->instId=%s", pde.getFormCode(), "准备停止任务",
				pde.getUpdateBy(), pde.getProcinstId()));
		List<Integer> nextUserIds = new ArrayList<Integer>();
		nextUserIds.add(666666);
		TaskQuery query = ActivitiHelper.GetEngine().getTaskService().createTaskQuery();
		// query.taskAssignee(pde.getUpdateBy() + "");
		query.processInstanceId(pde.getProcinstId());
		Task task = query.singleResult();
		if (!task.getName().contains("DOA") && !task.getName().equals("Init")) {
			ActivitiHelper.GetEngine().getTaskService().complete(task.getId());
		}
		TaskQuery query2 = ActivitiHelper.GetEngine().getTaskService().createTaskQuery();
		// query.taskAssignee(pde.getUpdateBy() + "");
		query2.processInstanceId(pde.getProcinstId());
		task = query2.singleResult();
		Log4jHelper.LOGGER.info(String.format("[%s]->%s->taskId=%s", pde.getFormCode(), "查找到引擎任务", task.getId()));
		Map<String, Object> variables = new HashMap<String, Object>();
		for (int nextUserId : nextUserIds) {
			if (nextUserId == 666666) {
				variables.put("to", "0");
				pde.setStepName("结束");
				Log4jHelper.LOGGER.info(
						String.format("[%s]->%s->taskId=%s", pde.getFormCode(), "查找到审核人员666666,准备完成DOA", task.getId()));
			} else {
				variables.put("to", "1");
			}
		}
		variables.put("assigneeList", nextUserIds);
		variables.put("condition", pde.getCondition());
		ActivitiHelper.GetEngine().getTaskService().complete(task.getId(), variables);
		Log4jHelper.LOGGER.info(String.format("[%s]->%s->taskId=%s", pde.getFormCode(), "停止任务完成", task.getId()));
	}

	public void JumpToDoa(ProcessDataEntity pde) {
		Log4jHelper.LOGGER.info(String.format("[%s]->%s->updateBy=%d->instId=%s", pde.getFormCode(), "JumpToDoa准备完成任务",
				pde.getUpdateBy(), pde.getProcinstId()));
		TaskQuery query = ActivitiHelper.GetEngine().getTaskService().createTaskQuery();
		query.processInstanceId(pde.getProcinstId());
		Task task = query.singleResult();
		Log4jHelper.LOGGER.info(String.format("[%s]->%s->taskId=%s", pde.getFormCode(), "查找到引擎任务", task.getId()));

		ActivitiHelper.GetEngine().getTaskService().complete(task.getId());
		Log4jHelper.LOGGER.info(String.format("[%s]->%s->taskId=%s", pde.getFormCode(), "引擎任务完成", task.getId()));
	}

	public void DoAction(ProcessApproveEntity pae, ProcessDataEntity pde) {
		Log4jHelper.LOGGER.info(String.format("[%s]->%s->updateBy=%d->instId=%s", pde.getFormCode(), "准备开始动作",
				pae.getUpdateBy(), pae.getProcinstId()));
		TaskQuery query = ActivitiHelper.GetEngine().getTaskService().createTaskQuery();
		// query.taskAssignee(pde.getUpdateBy() + "");
		query.processInstanceId(pde.getProcinstId());
		Task task = query.singleResult();
		if (!task.getName().contains("DOA")) {
			ActivitiHelper.GetEngine().getTaskService().complete(task.getId());
		}
		TaskQuery query2 = ActivitiHelper.GetEngine().getTaskService().createTaskQuery();
		// query.taskAssignee(pde.getUpdateBy() + "");
		query2.processInstanceId(pde.getProcinstId());
		task = query2.singleResult();
		Log4jHelper.LOGGER.info(String.format("[%s]->%s->taskId=%s", pde.getFormCode(), "查找到引擎任务", task.getId()));
		Map<String, Object> variables = new HashMap<String, Object>();
		if (pae.getStepName().equals("preSign")) {
			variables.put("to", 3);
			// variables.put("dealUser", pae.getUserId());
			variables.put("inputUser", pae.getUserId());
		} else if (pae.getStepName().equals("afterSign")) {
			variables.put("to", 4);
			variables.put("inputUser", pae.getUserId());
		} else if (pae.getStepName().equals("modify")) {
			variables.put("to", 5);
			variables.put("inputUser", pae.getUserId());
		} else if (pae.getStepName().equals("transfer")) {
			variables.put("to", 6);
			variables.put("inputUser", pae.getUserId());
		} else {
			return;
		}
		ActivitiHelper.GetEngine().getTaskService().complete(task.getId(), variables);
		Log4jHelper.LOGGER.info(String.format("[%s]->%s->taskId=%s", pde.getFormCode(), "引擎任务完成", task.getId()));
	}

	public void clear(ProcessDataEntity pde) {
		ProcessInstDiagramService.getInstance().delete(pde.getId());
		ProcessInstDiagramEntity pide = new ProcessInstDiagramEntity();
		pide.setProcessDataId(pde.getId());
		pide.setProcessId(pde.getProcessId());
		pide.setName("发起申请");
		pide.setUserId(pde.getCreateBy());
		pide.setLevelCount(-1);
		ProcessInstDiagramService.getInstance().insert(pide);
	}

	public void build(ProcessDataEntity pde, List<Integer> userIds, String currentUserId, int levelCount,
			int positionId) {
		for (int userId : userIds) {
			ProcessInstDiagramEntity pide = new ProcessInstDiagramEntity();
			pide.setGuid(java.util.UUID.randomUUID().toString().replaceAll("-", ""));
			pide.setProcessDataId(pde.getId());
			pide.setProcessId(pde.getProcessId());
			pide.setName(pde.getStepName());
			pide.setUserId(userId);
			pide.setLevelCount(levelCount);
			pide.setApprovePositionId(positionId);
			if (currentUserId.equals(userId + "")) {
				pide.setIsCurrent(1);
			}
			ProcessInstDiagramService.getInstance().insert(pide);
		}
	}

	public void buildProcessInstDiagram(ProcessDataEntity pde) {
		ConditionUtil.getNextDealUser(pde, instance);
	}
}
