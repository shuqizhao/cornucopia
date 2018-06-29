package cornucopia.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;

import cornucopia.dao.ProcessDao;
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

	public String StartByProcessId(String processId, String userId) {
		ProcessDiagramEntity pd = ProcessDiagramService.getInstance().getByProcessId(processId);
		String instId = Start(pd.getDefKey(), userId);
		Complete(processId, instId);
		return instId;
	}

	public String Start(String key, String userId) {
		RuntimeService rs = ActivitiHelper.GetEngine().getRuntimeService();
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("dealUser", userId);
		ProcessInstance pi = rs.startProcessInstanceByKey(key, variables);
		String instId = pi.getProcessInstanceId();
		return instId;
	}

	public void Complete(String instId) {
	    ProcessDataEntity pde =	ProcessDataService.getInstance().getByInstId(instId);
		String processId = pde.getProcessId()+"";
		Complete(processId, instId);
	}

	public void Complete(String processId, String instId) {
		TaskQuery query = ActivitiHelper.GetEngine().getTaskService().createTaskQuery();
		query.processInstanceId(instId);
		Task task = query.singleResult();
		String userId = getNextDealUser(processId, instId);
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("dealUser", userId);
		ActivitiHelper.GetEngine().getTaskService().complete(task.getId(), variables);
	}

	public String getNextDealUser(String processId, String instId) {
		TaskQuery query = ActivitiHelper.GetEngine().getTaskService().createTaskQuery();
		query.processInstanceId(instId);
		Task task = query.singleResult();
		String taskName = task.getName();
		ProcessNodeEntity pne = ProcessNodeService.getInstance().getByName(processId, taskName);
		int processNodeId = pne.getId();
		return "";
	}
}
