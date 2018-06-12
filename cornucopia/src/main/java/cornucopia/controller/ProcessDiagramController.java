package cornucopia.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cornucopia.entity.JsonResult;
import cornucopia.entity.ProcessDiagramEntity;
import cornucopia.service.ProcessDiagramService;

@RestController
@RequestMapping("/processdiagram")
public class ProcessDiagramController {
	@RequestMapping(value = { "/upload" }, method = RequestMethod.POST)
	public JsonResult<Integer> upload(ProcessDiagramEntity processDiagramEntity) {
		int result = ProcessDiagramService.getInstance().insert(processDiagramEntity);
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(result);
		return jr;
	}
	
	@RequestMapping(value = { "/getAll" }, method = RequestMethod.GET)
	public JsonResult<List<ProcessDiagramEntity>> getAll(int processId) {
		List<ProcessDiagramEntity> result = ProcessDiagramService.getInstance().getAll(processId);
		JsonResult<List<ProcessDiagramEntity>> jr = new JsonResult<List<ProcessDiagramEntity>>();
		jr.setCode(200);
		jr.setData(result);
		return jr;
	}
}
