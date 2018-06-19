package cornucopia.service;

import java.io.IOException;
import java.util.List;

import org.activiti.engine.repository.Deployment;

import cornucopia.dao.ProcessDiagramDao;
import cornucopia.entity.ProcessDiagramEntity;
import cornucopia.util.ActivitiHelper;
import cornucopia.util.FileEntity;
import cornucopia.util.FileUtil;
import cornucopia.util.MyBatisHelper;

public class ProcessDiagramService {
	private static ProcessDiagramService instance = new ProcessDiagramService();

	private ProcessDiagramService() {
	}

	public static ProcessDiagramService getInstance() {
		return instance;
	}

	ProcessDiagramDao processDiagramDao = MyBatisHelper.getMapper(ProcessDiagramDao.class);

	public List<ProcessDiagramEntity> getAll(int processId) {
		return processDiagramDao.getAll(processId);
	}

	public int exists(String nodeName, int processId) {
		return processDiagramDao.exists(nodeName, processId);
	}

	public int insert(ProcessDiagramEntity processDiagramEntity) {
		return processDiagramDao.insert(processDiagramEntity);
	}

	public int disable(int id) {
		return processDiagramDao.disable(id);
	}

	public int enable(int id) {
		return processDiagramDao.enable(id);
	}

	public int delete(int id) {
		return processDiagramDao.delete(id);
	}

	public ProcessDiagramEntity get(int id) {
		return processDiagramDao.get(id);
	}

	public String deploy(int id) throws IOException {
		ProcessDiagramEntity processDiagramEntity = get(id);
		FileEntity fileId = FileUtil.GetStream(processDiagramEntity.getFileId());
		FileEntity picFileId = FileUtil.GetStream(processDiagramEntity.getPicFileId());
		Deployment deployment = ActivitiHelper.GetEngine().getRepositoryService().createDeployment()
				.name(processDiagramEntity.getName()).addInputStream(fileId.getName(), fileId.getIs())
				.addInputStream(picFileId.getName(), picFileId.getIs()).deploy();
		processDiagramEntity.setDeployId(deployment.getId());
		update(processDiagramEntity);
		return deployment.getId();
	}

	private void update(ProcessDiagramEntity processDiagramEntity) {
		// TODO Auto-generated method stub

	}
}
