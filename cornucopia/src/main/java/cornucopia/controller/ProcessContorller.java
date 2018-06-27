package cornucopia.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cornucopia.entity.JsonResult;
import cornucopia.entity.ProcessCategoryEntity;
import cornucopia.entity.ProcessDataEntity;
import cornucopia.entity.ProcessEntity;
import cornucopia.model.ProcessDataViewModel;
import cornucopia.service.OrderService;
import cornucopia.service.ProcessCategoryService;
import cornucopia.service.ProcessDataService;
import cornucopia.service.ProcessService;

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
	public JsonResult<Integer> applySave(@RequestBody ProcessDataViewModel pdvm) {
		ProcessDataEntity processDataEntity = new ProcessDataEntity();
		processDataEntity.setBizData(pdvm.getXmlStr());
		String formCode = OrderService.getInstance().getOrderNo("DPF");
		processDataEntity.setFormCode(formCode);
		int result = ProcessDataService.getInstance().insert(processDataEntity);
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(result);
		return jr;
	}
}
