package cornucopia.controller;

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
}
