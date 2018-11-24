package cornucopia.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cornucopia.entity.JsonResult;
import cornucopia.entity.ParainstEntity;
import cornucopia.model.FunctionParainstViewModel;
import cornucopia.model.FunctionViewModel;
import cornucopia.entity.FunctionEntity;
import cornucopia.entity.FunctionParameterEntity;
import cornucopia.service.FunctionService;

@RestController
@RequestMapping("/function")
public class FunctionController {
	@RequestMapping(value = { "/alllist" }, method = RequestMethod.GET)
	public JsonResult<List<FunctionEntity>> alllist(int id) {
		List<FunctionEntity> funcs = FunctionService.getInstance().getAll(id);

		JsonResult<List<FunctionEntity>> jr = new JsonResult<List<FunctionEntity>>();
		jr.setCode(200);
		jr.setData(funcs);
		return jr;
	}
	
	@RequestMapping(value = { "/get" }, method = RequestMethod.GET)
	public JsonResult<FunctionEntity> get(int id) {
		FunctionEntity functionEntity = FunctionService.getInstance().get(id);
		JsonResult<FunctionEntity> jr = new JsonResult<FunctionEntity>();
		jr.setCode(200);
		jr.setData(functionEntity);
		return jr;
	}
	
	@RequestMapping(value = { "/getPara" }, method = RequestMethod.GET)
	public JsonResult<List<FunctionParameterEntity>> getPara(int id) {
		List<FunctionParameterEntity> functionParaEntities = FunctionService.getInstance().getParas(id);
		JsonResult<List<FunctionParameterEntity>> jr = new JsonResult<List<FunctionParameterEntity>>();
		jr.setCode(200);
		jr.setData(functionParaEntities);
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
	
	@RequestMapping(value = { "/update" }, method = RequestMethod.POST)
	public JsonResult<Integer> update(@RequestBody FunctionViewModel funcVm) {
		FunctionService.getInstance().update(funcVm.getFunction());
		for (FunctionParameterEntity fp : funcVm.getFunctionParas()) {
			fp.setFuncId(funcVm.getFunction().getId());
		}
		FunctionService.getInstance().updateFunctionParas(funcVm.getFunction().getId(),funcVm.getFunctionParas());
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
	
	@RequestMapping(value = { "/addOrUpdateParainst" }, method = RequestMethod.POST)
	public JsonResult<Integer> addOrUpdateParainst( FunctionParainstViewModel fpv) {
		int id = FunctionService.getInstance().addOrUpdateParainst(fpv.getParainstId(),fpv.getFunctionId(),fpv.getParainstJson());
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(id);
		return jr;
	}
	
	@RequestMapping(value = { "/getParainst" }, method = RequestMethod.GET)
	public JsonResult<ParainstEntity> getParainst(String parainstId) {
		ParainstEntity parainstEntity = FunctionService.getInstance().getParainst(parainstId);
		JsonResult<ParainstEntity> jr = new JsonResult<ParainstEntity>();
		jr.setCode(200);
		jr.setData(parainstEntity);
		return jr;
	}
}
