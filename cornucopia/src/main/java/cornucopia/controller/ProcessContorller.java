package cornucopia.controller;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.DocumentException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cornucopia.entity.JsonResult;
import cornucopia.entity.ProcessCategoryEntity;
import cornucopia.entity.ProcessDataEntity;
import cornucopia.entity.ProcessEntity;
import cornucopia.entity.ProcessInstDiagramEntity;
import cornucopia.entity.UserEntity;
import cornucopia.model.ProcessDataViewModel;
import cornucopia.service.OrderService;
import cornucopia.service.ProcessCategoryService;
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
	@RequestMapping(value = { "/alllist" }, method = RequestMethod.GET)
	public JsonResult<List<ProcessEntity>> alllist() {
		List<ProcessEntity> processes = ProcessService.getInstance().getAllProcess();
		JsonResult<List<ProcessEntity>> jr = new JsonResult<List<ProcessEntity>>();
		jr.setCode(200);
		jr.setData(processes);
		return jr;
	}

	@RequestMapping(value = { "/exists" }, method = RequestMethod.POST)
	public JsonResult<Integer> exists(String name) {
		int isExists = ProcessService.getInstance().exists(name);
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(isExists);
		return jr;
	}

	@RequestMapping(value = { "/add" }, method = RequestMethod.POST)
	public JsonResult<Integer> add(ProcessEntity processEntity) {
		ProcessService.getInstance().insert(processEntity);
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(processEntity.getId());
		return jr;
	}

	@RequestMapping(value = { "/disable" }, method = RequestMethod.POST)
	public JsonResult<Integer> disable(@RequestParam(value = "Ids") int id) {
		int result = ProcessService.getInstance().disable(id);
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(result);
		return jr;
	}

	@RequestMapping(value = { "/enable" }, method = RequestMethod.POST)
	public JsonResult<Integer> enable(@RequestParam(value = "Ids") int id) {
		int result = ProcessService.getInstance().enable(id);
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(result);
		return jr;
	}

	@RequestMapping(value = { "/delete" }, method = RequestMethod.POST)
	public JsonResult<Integer> delete(@RequestParam(value = "Ids") int id) {
		int result = ProcessService.getInstance().delete(id);
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(result);
		return jr;
	}

	@RequestMapping(value = { "/category" }, method = RequestMethod.GET)
	public JsonResult<List<ProcessCategoryEntity>> category() {
		List<ProcessCategoryEntity> processCategories = ProcessCategoryService.getInstance().getAllByOrder();
		List<ProcessEntity> processes = ProcessService.getInstance().getAllProcess();
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

	@RequestMapping(value = { "/applySave" }, method = RequestMethod.POST)
	public JsonResult<Integer> applySave(HttpServletRequest request, @RequestBody ProcessDataViewModel pdvm)
			throws DocumentException, UnsupportedEncodingException {
		ProcessDataEntity processDataEntity = new ProcessDataEntity();
		processDataEntity.setBizData(pdvm.getXmlStr());

		String processId = XmlUtil.selectSingleText(processDataEntity.getBizData(), "//processId");
		processDataEntity.setProcessId(Integer.parseInt(processId));

		String pre = ProcessService.getInstance().getPre(processId);

		String formCode = OrderService.getInstance().getOrderNo(pre);
		processDataEntity.setFormCode(formCode);
		XmlUtil.createElementForPd(processDataEntity, "//approve", "fromCode", formCode);

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		XmlUtil.createElementForPd(processDataEntity, "//approve", "createTime", df.format(new Date()));

		UserEntity user = (UserEntity) request.getSession().getAttribute("user");
		processDataEntity.setCreateBy(user.getId());
		XmlUtil.createElementForPd(processDataEntity, "//approve", "createBy", user.getId() + "");

		String procinstId = ProcessService.getInstance().StartByProcessId(processId, user.getId(),
				processDataEntity.getBizData());
		processDataEntity.setProcinstId(procinstId);
		int result = ProcessDataService.getInstance().insert(processDataEntity);
		ProcessService.getInstance().buildProcessInstDiagram(processDataEntity);
		// XmlUtil.createElementForPd(processDataEntity, "//approve", "pdId",
		// processDataEntity.getId()+"");

		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(result);
		return jr;
	}

	@RequestMapping(value = { "/applyAgree" }, method = RequestMethod.POST)
	public JsonResult<Integer> applyAgree(HttpServletRequest request, @RequestBody ProcessDataViewModel pdvm)
			throws DocumentException, UnsupportedEncodingException {
		String formCode = XmlUtil.selectSingleText(pdvm.getXmlStr(), "//fromCode");
		ProcessDataEntity processDataEntity = ProcessDataService.getInstance().getByFormCode(formCode);
		processDataEntity.setBizData(pdvm.getXmlStr());
		UserEntity user = (UserEntity) request.getSession().getAttribute("user");
		processDataEntity.setUpdateBy(user.getId());
		processDataEntity.setLevelCount(processDataEntity.getLevelCount() + 1);
		int result = ProcessDataService.getInstance().update(processDataEntity);
		ProcessService.getInstance().Complete(processDataEntity);
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(result);
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
		List<ProcessInstDiagramEntity> ProcessInstDiagramEntities = ProcessInstDiagramService.getInstance().getAll(processDataId);
		JsonResult<List<ProcessInstDiagramEntity>> jr = new JsonResult<List<ProcessInstDiagramEntity>>();
		jr.setCode(200);
		jr.setData(ProcessInstDiagramEntities);
		return jr;
	}
	
	@RequestMapping(value = { "/getProcessInstAuth" }, method = RequestMethod.GET)
	public JsonResult<List<String>> getProcessInstAuth(HttpServletRequest request,int processDataId) {
		 UserEntity user = (UserEntity) request.getSession().getAttribute("user");
		List<String> auths = ProcessInstDiagramService.getInstance().getProcessInstAuth(processDataId,user.getId());
		JsonResult<List<String>> jr = new JsonResult<List<String>>();
		jr.setCode(200);
		jr.setData(auths);
		return jr;
	}
}
