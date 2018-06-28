package cornucopia.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;

import cornucopia.dao.ProcessDao;
import cornucopia.entity.ProcessDiagramEntity;
import cornucopia.entity.ProcessEntity;
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
		Complete(instId, userId);
		return instId;
	}

	public String Start(String key, String userId) {
		RuntimeService rs = ActivitiHelper.GetEngine().getRuntimeService();
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("dealUser", userId);
		ProcessInstance pi = rs.startProcessInstanceByKey(key, variables);
		// String acId = pi.getActivityId();
		String instId = pi.getProcessInstanceId();
		return instId;
	}

	public void Complete(String instId, String userId) {
		TaskQuery query = ActivitiHelper.GetEngine().getTaskService().createTaskQuery();
		query.taskAssignee(userId);
		query.processInstanceId(instId);
		Task task = query.singleResult();
		ActivitiHelper.GetEngine().getTaskService().complete(task.getId());
	}
}
