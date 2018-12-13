package cornucopia.controller;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.DocumentException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cornucopia.entity.JsonResult;
import cornucopia.entity.ProcessApproveEntity;
import cornucopia.entity.ProcessCategoryEntity;
import cornucopia.entity.ProcessDataEntity;
import cornucopia.entity.ProcessEntity;
import cornucopia.entity.ProcessInstDiagramEntity;
import cornucopia.entity.UserEntity;
import cornucopia.model.DoActionViewModel;
import cornucopia.model.ProcessDataViewModel;
import cornucopia.model.ProcessInstAuthViewModel;
import cornucopia.service.OrderService;
import cornucopia.service.ProcessApproveService;
import cornucopia.service.ProcessCategoryService;
import cornucopia.service.ProcessDataHistoryService;
import cornucopia.service.ProcessDataService;
import cornucopia.service.ProcessInstDiagramService;
import cornucopia.service.ProcessService;
import cornucopia.util.DataTableParameter;
import cornucopia.util.DataTableResult;
import cornucopia.util.PagingParameters;
import cornucopia.util.XmlUtil;

@RestController
@RequestMapping("/process")
public class ProcessContorller {
	@RequestMapping(value = { "/get" }, method = RequestMethod.GET)
	public JsonResult<ProcessEntity> get(String id) {
		ProcessEntity processes = ProcessService.getInstance().get(id);
		JsonResult<ProcessEntity> jr = new JsonResult<ProcessEntity>();
		jr.setCode(200);
		jr.setData(processes);
		return jr;
	}

	@RequestMapping(value = { "/alllist" }, method = RequestMethod.GET)
	public JsonResult<List<ProcessEntity>> alllist() {
		List<ProcessEntity> processes = ProcessService.getInstance().getAllProcess();
		JsonResult<List<ProcessEntity>> jr = new JsonResult<List<ProcessEntity>>();
		jr.setCode(200);
		jr.setData(processes);
		return jr;
	}

	@RequestMapping(value = { "/exists" }, method = RequestMethod.POST)
	public JsonResult<Integer> exists(int id, String name, String pre) {
		int isExists = ProcessService.getInstance().exists(id, name, pre);
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(isExists);
		return jr;
	}

	@RequestMapping(value = { "/add" }, method = RequestMethod.POST)
	public JsonResult<Integer> add(HttpServletRequest request, ProcessEntity processEntity) {
		UserEntity userEntity = (UserEntity) request.getSession().getAttribute("user");
		int userId = 0;
		if (userEntity != null) {
			userId = userEntity.getId();
		}
		processEntity.setCreateBy(userId);
		ProcessService.getInstance().insert(processEntity);
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(processEntity.getId());
		return jr;
	}

	@RequestMapping(value = { "/update" }, method = RequestMethod.POST)
	public JsonResult<Integer> update(HttpServletRequest request, ProcessEntity processEntity) {
		UserEntity userEntity = (UserEntity) request.getSession().getAttribute("user");
		int userId = 0;
		if (userEntity != null) {
			userId = userEntity.getId();
		}
		processEntity.setCreateBy(userId);
		ProcessService.getInstance().update(processEntity);
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(processEntity.getId());
		return jr;
	}

	@RequestMapping(value = { "/disable" }, method = RequestMethod.POST)
	public JsonResult<Integer> disable(@RequestParam(value = "Ids[]") int[] ids) {
		int result = ProcessService.getInstance().disable(ids[0]);
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(result);
		return jr;
	}

	@RequestMapping(value = { "/enable" }, method = RequestMethod.POST)
	public JsonResult<Integer> enable(@RequestParam(value = "Ids[]") int[] ids) {
		int result = ProcessService.getInstance().enable(ids[0]);
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(result);
		return jr;
	}

	@RequestMapping(value = { "/delete" }, method = RequestMethod.POST)
	public JsonResult<Integer> delete(@RequestParam(value = "Ids[]") int[] ids) {
		int result = ProcessService.getInstance().delete(ids[0]);
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(result);
		return jr;
	}

	@RequestMapping(value = { "/category" }, method = RequestMethod.GET)
	public JsonResult<List<ProcessCategoryEntity>> category() {
		List<ProcessCategoryEntity> processCategories = ProcessCategoryService.getInstance().getAllByOrder();
		List<ProcessEntity> processes = ProcessService.getInstance().getAllEnableProcess();
		for (ProcessCategoryEntity processCategoryEntity : processCategories) {
			List<ProcessEntity> temp = processes.stream()
					.filter((ProcessEntity r) -> r.getCategoryId() == processCategoryEntity.getId())
					.sorted((s1, s2) -> Double.compare(s1.getOrderNum(), s2.getOrderNum()))
					.collect(Collectors.toList());
			processCategoryEntity.setProesses(temp);
		}
		JsonResult<List<ProcessCategoryEntity>> jr = new JsonResult<List<ProcessCategoryEntity>>();
		jr.setCode(200);
		jr.setData(processCategories);
		return jr;
	}

	@Transactional(rollbackFor = Exception.class)
	@RequestMapping(value = { "/applySave" }, method = RequestMethod.POST)
	public JsonResult<Integer> applySave(HttpServletRequest request, @RequestBody ProcessDataViewModel pdvm)
			throws DocumentException, UnsupportedEncodingException {
		ProcessDataEntity processDataEntity = new ProcessDataEntity();
		processDataEntity.setJsonData(pdvm.getJsonStr());
		processDataEntity.setBizData(pdvm.getXmlStr());

		String processId = XmlUtil.selectSingleText(processDataEntity.getBizData(), "//processId");
		processDataEntity.setProcessId(Integer.parseInt(processId));

		String pre = ProcessService.getInstance().getPre(processId);

		String formCode = OrderService.getInstance().getOrderNo(pre);
		processDataEntity.setFormCode(formCode);
		XmlUtil.createOrUpdateElementForPd(processDataEntity, "//approve", "fromCode", formCode);

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		XmlUtil.createOrUpdateElementForPd(processDataEntity, "//approve", "createTime", df.format(new Date()));

		UserEntity user = (UserEntity) request.getSession().getAttribute("user");
		processDataEntity.setCreateBy(user.getId());
		processDataEntity.setUpdateBy(user.getId());
		XmlUtil.createOrUpdateElementForPd(processDataEntity, "//approve", "createBy", user.getId() + "");

		ProcessService.getInstance().StartProcess(processDataEntity);
		processDataEntity.setStepName("发起申请");
		int result = ProcessDataService.getInstance().insert(processDataEntity);
		ProcessApproveEntity processApproveEntity = new ProcessApproveEntity();
		processApproveEntity.setGuid(java.util.UUID.randomUUID().toString().replaceAll("-", ""));
		processApproveEntity.setCreateBy(user.getId());
		processApproveEntity.setProcessId(processDataEntity.getProcessId());
		processApproveEntity.setProcinstId(processDataEntity.getProcinstId());
		processApproveEntity.setProcessDataId(processDataEntity.getId());
		processApproveEntity.setLevelCount(-1);
		processApproveEntity.setStepName(processDataEntity.getStepName());
		processApproveEntity.setUserId(user.getId());
		ProcessApproveService.getInstance().insert(processApproveEntity);
		ProcessDataHistoryService.getInstance().insert(processDataEntity);
		ProcessService.getInstance().buildProcessInstDiagram(processDataEntity);
		// XmlUtil.createElementForPd(processDataEntity, "//approve", "pdId",
		// processDataEntity.getId()+"");

		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(result);
		return jr;
	}

	@Transactional(rollbackFor = Exception.class)
	@RequestMapping(value = { "/applyAgree" }, method = RequestMethod.POST)
	public JsonResult<Integer> applyAgree(HttpServletRequest request, @RequestBody ProcessDataViewModel pdvm)
			throws DocumentException, UnsupportedEncodingException {
		String formCode = XmlUtil.selectSingleText(pdvm.getXmlStr(), "//fromCode");
		ProcessDataEntity processDataEntity = ProcessDataService.getInstance().getByFormCode(formCode);
		processDataEntity.setJsonData(pdvm.getJsonStr());
		processDataEntity.setBizData(pdvm.getXmlStr());
		UserEntity user = (UserEntity) request.getSession().getAttribute("user");
		processDataEntity.setUpdateBy(user.getId());

		JsonResult<Integer> jr = new JsonResult<Integer>();
		List<ProcessInstAuthViewModel> auths = ProcessInstDiagramService.getInstance()
				.getProcessInstAuth(processDataEntity.getId(), user.getId());
		if (auths != null && auths.size() > 0) {
			ProcessApproveEntity existsAfterSign = null;
			ProcessInstAuthViewModel piavm = auths.get(0);
			if (piavm.getCurrentStep().equals("preSign") || piavm.getCurrentStep().equals("modify")) {
				processDataEntity.setLevelCount(processDataEntity.getLevelCount());
				ProcessApproveService.getInstance().updateCurrent(piavm.getId(), 0);
				//先调到DOA节点
				ProcessService.getInstance().JumpToDoa(processDataEntity);
			} else if (piavm.getCurrentStep().equals("transfer")) {
				// processDataEntity.setLevelCount(processDataEntity.getLevelCount() + 1);
				// ProcessApproveService.getInstance().updateCurrent(piavm.getId(), 0);
				ProcessApproveService.getInstance().updateCurrent(piavm.getId(), 0);
				existsAfterSign = ProcessApproveService.getInstance().getAfterSign(processDataEntity.getId(),
						user.getId());
				if (existsAfterSign == null || existsAfterSign.getId() == 0) {
					processDataEntity.setLevelCount(processDataEntity.getLevelCount() + 1);
				} else {
					processDataEntity.setLevelCount(processDataEntity.getLevelCount());
				}
				//先调到DOA节点
				ProcessService.getInstance().JumpToDoa(processDataEntity);
				// 转办时要给操作人记录
				ProcessInstDiagramEntity pide = ProcessInstDiagramService.getInstance().getProcessInst(piavm.getGuid());
				ProcessApproveEntity processApproveEntity = new ProcessApproveEntity();
				processApproveEntity.setCreateBy(pide.getUserId());
				processApproveEntity.setApprovePositionId(pide.getApprovePositionId());
				processApproveEntity.setProcessId(processDataEntity.getProcessId());
				processApproveEntity.setProcinstId(processDataEntity.getProcinstId());
				processApproveEntity.setProcessDataId(processDataEntity.getId());
				processApproveEntity.setLevelCount(processDataEntity.getLevelCount() - 1);
				processApproveEntity.setStepName(pide.getName());
				processApproveEntity.setUserId(pide.getUserId());
				processApproveEntity.setGuid(piavm.getGuid());
				ProcessApproveService.getInstance().insert(processApproveEntity);
			} else if (piavm.getCurrentStep().equals("afterSign")) {
				// processDataEntity.setLevelCount(processDataEntity.getLevelCount() + 1);
				ProcessApproveService.getInstance().updateCurrent(piavm.getId(), 0);
				existsAfterSign = ProcessApproveService.getInstance().getAfterSign(processDataEntity.getId(),
						user.getId());
				if (existsAfterSign == null || existsAfterSign.getId() == 0) {
					processDataEntity.setLevelCount(processDataEntity.getLevelCount() + 1);
				} else {
					processDataEntity.setLevelCount(processDataEntity.getLevelCount());
				}
				//先调到DOA节点
				ProcessService.getInstance().JumpToDoa(processDataEntity);
			} else {

				existsAfterSign = ProcessApproveService.getInstance().getAfterSign(processDataEntity.getId(),
						user.getId());

				if (existsAfterSign == null || existsAfterSign.getId() == 0) {
					processDataEntity.setLevelCount(processDataEntity.getLevelCount() + 1);
				} else {
					processDataEntity.setLevelCount(processDataEntity.getLevelCount());
				}

				ProcessApproveEntity processApproveEntity = new ProcessApproveEntity();
				processApproveEntity.setCreateBy(user.getId());
				processApproveEntity.setApprovePositionId(piavm.getApprovePositionId());
				processApproveEntity.setProcessId(processDataEntity.getProcessId());
				processApproveEntity.setProcinstId(processDataEntity.getProcinstId());
				processApproveEntity.setProcessDataId(processDataEntity.getId());
				processApproveEntity.setLevelCount(processDataEntity.getLevelCount());
				processApproveEntity.setStepName(piavm.getVitualTitle());
				processApproveEntity.setUserId(user.getId());
				String processInstDiagramGuId = ProcessInstDiagramService.getInstance()
						.getProcessInstDiagramGuId(processDataEntity.getId(), user.getId());
				processApproveEntity.setGuid(processInstDiagramGuId);
				ProcessApproveService.getInstance().insert(processApproveEntity);
			}
			if (existsAfterSign == null || existsAfterSign.getId() == 0) {
				ProcessService.getInstance().Complete(processDataEntity);
				ProcessApproveService.getInstance().updateCurrent(piavm.getId(), 0);
			} else {
				ProcessService.getInstance().DoAction(existsAfterSign, processDataEntity);
				ProcessApproveService.getInstance().updateCurrent(existsAfterSign.getId(), 1);
			}
			ProcessDataHistoryService.getInstance().insert(processDataEntity);
			ProcessInstDiagramService.getInstance().updateCurrent(processDataEntity.getId(),
					processDataEntity.getLevelCount(), 1);
			int result = ProcessDataService.getInstance().update(processDataEntity);
			jr.setCode(200);
			jr.setData(result);
		} else {
			jr.setCode(500);
			jr.setMessage("没有权限");
		}
		return jr;
	}

	@Transactional(rollbackFor = Exception.class)
	@RequestMapping(value = { "/applyRetry" }, method = RequestMethod.POST)
	public JsonResult<Integer> applyRetry(HttpServletRequest request, @RequestBody ProcessDataViewModel pdvm)
			throws DocumentException, UnsupportedEncodingException {
		String formCode = XmlUtil.selectSingleText(pdvm.getXmlStr(), "//fromCode");
		ProcessDataEntity processDataEntity = ProcessDataService.getInstance().getByFormCode(formCode);
		processDataEntity.setJsonData(pdvm.getJsonStr());
		processDataEntity.setBizData(pdvm.getXmlStr());

		UserEntity user = (UserEntity) request.getSession().getAttribute("user");
		processDataEntity.setUpdateBy(user.getId());
		processDataEntity.setLevelCount(-1);
		processDataEntity.setStepName("重发起");

		JsonResult<Integer> jr = new JsonResult<Integer>();
		List<ProcessInstAuthViewModel> auths = ProcessInstDiagramService.getInstance()
				.getProcessInstAuth(processDataEntity.getId(), user.getId());
		if (auths != null && auths.size() > 0) {
			ProcessInstAuthViewModel piavm = auths.get(0);
			ProcessApproveService.getInstance().updateCurrent(piavm.getId(), 0);
			ProcessService.getInstance().Complete(processDataEntity);
			ProcessDataHistoryService.getInstance().insert(processDataEntity);
			ProcessInstDiagramService.getInstance().updateCurrent(processDataEntity.getId(), 0, 1);
			ProcessDataHistoryService.getInstance().insert(processDataEntity);
			int result = ProcessDataService.getInstance().update(processDataEntity);
			jr.setCode(200);
			jr.setData(result);
		} else {
			jr.setCode(500);
			jr.setMessage("没有权限");
		}
		return jr;
	}

	@Transactional(rollbackFor = Exception.class)
	@RequestMapping(value = { "/applyReturn" }, method = RequestMethod.POST)
	public JsonResult<Integer> applyReturn(HttpServletRequest request, @RequestBody ProcessDataViewModel pdvm)
			throws DocumentException, UnsupportedEncodingException {
		String formCode = XmlUtil.selectSingleText(pdvm.getXmlStr(), "//fromCode");
		ProcessDataEntity processDataEntity = ProcessDataService.getInstance().getByFormCode(formCode);
		processDataEntity.setBizData(pdvm.getXmlStr());
		processDataEntity.setJsonData(pdvm.getJsonStr());

		UserEntity user = (UserEntity) request.getSession().getAttribute("user");
		processDataEntity.setUpdateBy(user.getId());
		processDataEntity.setLevelCount(0);

		JsonResult<Integer> jr = new JsonResult<Integer>();
		List<ProcessInstAuthViewModel> auths = ProcessInstDiagramService.getInstance()
				.getProcessInstAuth(processDataEntity.getId(), user.getId());
		if (auths != null && auths.size() > 0) {
			ProcessService.getInstance().Return(processDataEntity);
			processDataEntity.setStepName("重发起");
			ProcessDataHistoryService.getInstance().insert(processDataEntity);
			ProcessInstDiagramService.getInstance().updateCurrent(processDataEntity.getId(), -1, 1);
			ProcessApproveEntity paeFirstLevel = ProcessApproveService.getInstance().getFirstLevel(processDataEntity.getId());
			ProcessApproveEntity processApproveEntity = new ProcessApproveEntity();
			processApproveEntity.setGuid(paeFirstLevel.getGuid());
			processApproveEntity.setCreateBy(user.getId());
			processApproveEntity.setProcessId(processDataEntity.getProcessId());
			processApproveEntity.setProcinstId(processDataEntity.getProcinstId());
			processApproveEntity.setProcessDataId(processDataEntity.getId());
			processApproveEntity.setLevelCount(-1);
			processApproveEntity.setStepName("retry");
			processApproveEntity.setUserId(processDataEntity.getCreateBy());
			int paeId = ProcessApproveService.getInstance().insert(processApproveEntity);
			ProcessApproveService.getInstance().updateCurrent(paeId, 1);
			int result = ProcessDataService.getInstance().update(processDataEntity);
			jr.setCode(200);
			jr.setData(result);
		} else {
			jr.setCode(500);
			jr.setMessage("没有权限");
		}
		return jr;
	}

	@RequestMapping(value = { "/launchedList" }, method = RequestMethod.POST)
	public DataTableResult<ProcessDataEntity> launchedList(HttpServletRequest request, DataTableParameter dtp) {
		PagingParameters pp = new PagingParameters();
		pp.setStart(dtp.getiDisplayStart());
		pp.setLength(dtp.getiDisplayLength());
		UserEntity user = (UserEntity) request.getSession().getAttribute("user");
		List<ProcessDataEntity> processDatas = ProcessDataService.getInstance().launchedList(pp, user.getId());
		int count = pp.getTotalRows();
		DataTableResult<ProcessDataEntity> dtr = new DataTableResult<ProcessDataEntity>(dtp.getsEcho() + 1, count,
				count, processDatas);
		return dtr;
	}

	@RequestMapping(value = { "/taskList" }, method = RequestMethod.POST)
	public DataTableResult<ProcessDataEntity> taskList(HttpServletRequest request, DataTableParameter dtp) {
		PagingParameters pp = new PagingParameters();
		pp.setStart(dtp.getiDisplayStart());
		pp.setLength(dtp.getiDisplayLength());
		UserEntity user = (UserEntity) request.getSession().getAttribute("user");
		List<ProcessDataEntity> processDatas = ProcessDataService.getInstance().taskList(pp, user.getId());
		int count = pp.getTotalRows();
		DataTableResult<ProcessDataEntity> dtr = new DataTableResult<ProcessDataEntity>(dtp.getsEcho() + 1, count,
				count, processDatas);
		return dtr;
	}

	@RequestMapping(value = { "/dealedList" }, method = RequestMethod.POST)
	public DataTableResult<ProcessDataEntity> dealedList(HttpServletRequest request, DataTableParameter dtp) {
		PagingParameters pp = new PagingParameters();
		pp.setStart(dtp.getiDisplayStart());
		pp.setLength(dtp.getiDisplayLength());
		UserEntity user = (UserEntity) request.getSession().getAttribute("user");
		List<ProcessDataEntity> processDatas = ProcessDataService.getInstance().dealedList(pp, user.getId());
		int count = pp.getTotalRows();
		DataTableResult<ProcessDataEntity> dtr = new DataTableResult<ProcessDataEntity>(dtp.getsEcho() + 1, count,
				count, processDatas);
		return dtr;
	}

	@RequestMapping(value = { "/getBizData" }, method = RequestMethod.GET)
	public JsonResult<String> getBizData(HttpServletRequest request, int processId, int id) {
		// UserEntity user = (UserEntity) request.getSession().getAttribute("user");
		String bizData = ProcessDataService.getInstance().getBizData(id);
		JsonResult<String> jr = new JsonResult<String>();
		jr.setCode(200);
		jr.setData(bizData);
		return jr;
	}

	@RequestMapping(value = { "/getProcessInstDiagram" }, method = RequestMethod.GET)
	public JsonResult<List<ProcessInstDiagramEntity>> getProcessInstDiagram(int processDataId) {
		List<ProcessInstDiagramEntity> ProcessInstDiagramEntities = ProcessInstDiagramService.getInstance()
				.getAll(processDataId);
		JsonResult<List<ProcessInstDiagramEntity>> jr = new JsonResult<List<ProcessInstDiagramEntity>>();
		jr.setCode(200);
		jr.setData(ProcessInstDiagramEntities);
		return jr;
	}

	@RequestMapping(value = { "/getProcessInstAuth" }, method = RequestMethod.GET)
	public JsonResult<List<ProcessInstAuthViewModel>> getProcessInstAuth(HttpServletRequest request,
			int processDataId) {
		UserEntity user = (UserEntity) request.getSession().getAttribute("user");
		List<ProcessInstAuthViewModel> auths = ProcessInstDiagramService.getInstance().getProcessInstAuth(processDataId,
				user.getId());
		JsonResult<List<ProcessInstAuthViewModel>> jr = new JsonResult<List<ProcessInstAuthViewModel>>();
		jr.setCode(200);
		jr.setData(auths);
		return jr;
	}

	@Transactional(rollbackFor = Exception.class)
	@RequestMapping(value = { "/doAction" }, method = RequestMethod.POST)
	public JsonResult<Integer> doAction(HttpServletRequest request, DoActionViewModel davm)
			throws DocumentException, UnsupportedEncodingException {
		UserEntity user = (UserEntity) request.getSession().getAttribute("user");
		ProcessDataEntity processDataEntity = ProcessDataService.getInstance().get(davm.getProcessDataId());
		ProcessApproveEntity processApproveEntity = new ProcessApproveEntity();
		processApproveEntity.setGuid(java.util.UUID.randomUUID().toString().replaceAll("-", ""));
		processApproveEntity.setCreateBy(user.getId());
		processApproveEntity.setUserId(davm.getUserId());
		processApproveEntity.setApprovePositionId(davm.getApprovePositionId());
		processApproveEntity.setProcessId(processDataEntity.getProcessId());
		processApproveEntity.setProcinstId(processDataEntity.getProcinstId());
		processApproveEntity.setProcessDataId(processDataEntity.getId());
		processApproveEntity.setLevelCount(processDataEntity.getLevelCount());
		processApproveEntity.setStepName(davm.getAction());
		String processInstDiagramGuId = ProcessInstDiagramService.getInstance()
				.getProcessInstDiagramGuId(processDataEntity.getId(), user.getId());
		processApproveEntity.setParentGuid(processInstDiagramGuId);
		int result = ProcessApproveService.getInstance().insert(processApproveEntity);
		if (!davm.getAction().equals("afterSign")) {
			ProcessService.getInstance().DoAction(processApproveEntity, processDataEntity);
		}
		if(davm.getAction().equals("modify")){
			ProcessInstDiagramService.getInstance().updateCurrent(processDataEntity.getId(),
					processDataEntity.getLevelCount(), 0);
		}
		JsonResult<Integer> jr = new JsonResult<Integer>();
		if (result > 0) {
			jr.setCode(200);
			jr.setData(1);
		} else {
			jr.setCode(500);
			jr.setMessage("没有权限");
		}
		return jr;
	}
}
