package cornucopia.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cornucopia.entity.JsonResult;
import cornucopia.entity.ProcessDiagramEntity;
import cornucopia.model.ProcessDiagramViewModel;
import cornucopia.service.ProcessDiagramService;

@RestController
@RequestMapping("/processdiagram")
public class ProcessDiagramController {
	@RequestMapping(value = { "/upload" }, method = RequestMethod.POST)
	public JsonResult<Integer> upload(@RequestBody ProcessDiagramViewModel pdvm) {
		ProcessDiagramEntity processDiagramEntity = new ProcessDiagramEntity();
		processDiagramEntity.setProcessId(pdvm.getProcessId());
		processDiagramEntity.setPicFileId(pdvm.getPicFileId()[0].getId());
		processDiagramEntity.setFileId(pdvm.getFileId()[0].getId());
		processDiagramEntity.setName(pdvm.getName());
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
	
	@RequestMapping(value = { "/deploy" }, method = RequestMethod.POST)
	public JsonResult<String> deploy(@RequestParam(value = "Ids") int id) throws IOException {
		String deployId = ProcessDiagramService.getInstance().deploy(id);
		JsonResult<String> jr = new JsonResult<String>();
		jr.setCode(200);
		jr.setData(deployId);
		return jr;
	}
}
