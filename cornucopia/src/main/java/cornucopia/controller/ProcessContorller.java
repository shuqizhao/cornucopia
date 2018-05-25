package cornucopia.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cornucopia.entity.JsonResult;
import cornucopia.entity.ProcessEntity;
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
}
