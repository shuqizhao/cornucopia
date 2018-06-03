package cornucopia.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cornucopia.entity.JsonResult;
import cornucopia.model.FunctionViewModel;
import cornucopia.entity.FunctionEntity;
import cornucopia.entity.FunctionParameterEntity;
import cornucopia.service.FunctionService;

@RestController
@RequestMapping("/function")
public class FunctionController {
	@RequestMapping(value = { "/alllist" }, method = RequestMethod.GET)
	public JsonResult<List<FunctionEntity>> alllist() {
		List<FunctionEntity> funcs = FunctionService.getInstance().getAll();

		JsonResult<List<FunctionEntity>> jr = new JsonResult<List<FunctionEntity>>();
		jr.setCode(200);
		jr.setData(funcs);
		return jr;
	}

	@RequestMapping(value = { "/exists" }, method = RequestMethod.POST)
	public JsonResult<Integer> exists(String name) {
		int isExists = FunctionService.getInstance().exists(name);
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(isExists);
		return jr;
	}

	@RequestMapping(value = { "/add" }, method = RequestMethod.POST)

	public JsonResult<Integer> add(@RequestBody FunctionViewModel funcVm) {
		FunctionService.getInstance().insert(funcVm.getFunction());
		for (FunctionParameterEntity fp : funcVm.getFunctionParas()) {
			fp.setFuncId(funcVm.getFunction().getId());
		}
		FunctionService.getInstance().insertFunctionParas(funcVm.getFunctionParas());
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(funcVm.getFunction().getId());
		return jr;
	}

	@RequestMapping(value = { "/disable" }, method = RequestMethod.POST)
	public JsonResult<Integer> disable(@RequestParam(value = "Ids") int id) {
		int result = FunctionService.getInstance().disable(id);
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(result);
		return jr;
	}

	@RequestMapping(value = { "/enable" }, method = RequestMethod.POST)
	public JsonResult<Integer> enable(@RequestParam(value = "Ids") int id) {
		int result = FunctionService.getInstance().enable(id);
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(result);
		return jr;
	}

	@RequestMapping(value = { "/delete" }, method = RequestMethod.POST)
	public JsonResult<Integer> delete(@RequestParam(value = "Ids") int id) {
		int result = FunctionService.getInstance().delete(id);
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(result);
		return jr;
	}
}
