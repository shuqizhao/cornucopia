package cornucopia.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cornucopia.entity.JsonResult;
import cornucopia.entity.ProcessCategoryEntity;
import cornucopia.entity.ProcessEntity;
import cornucopia.entity.UserEntity;
import cornucopia.service.ProcessCategoryService;
import cornucopia.service.ProcessService;
import cornucopia.util.DataTableParameter;
import cornucopia.util.DataTableResult;
import cornucopia.util.PagingParameters;

@RestController
@RequestMapping("/processCategory")
public class ProcessCategoryContorller {

	@RequestMapping(value = { "/list" }, method = RequestMethod.POST)
	public DataTableResult<ProcessCategoryEntity> list(DataTableParameter dtp) {
		PagingParameters pp = new PagingParameters();
		pp.setStart(dtp.getiDisplayStart());
		pp.setLength(dtp.getiDisplayLength());
		pp.setTotalRows(0);
		List<ProcessCategoryEntity> users = ProcessCategoryService.getInstance().list(pp);
		int count = pp.getTotalRows();
		DataTableResult<ProcessCategoryEntity> dtr = new DataTableResult<ProcessCategoryEntity>(dtp.getsEcho() + 1,
				count, count, users);
		return dtr;
	}

	@RequestMapping(value = { "/all" }, method = RequestMethod.GET)
	public JsonResult<List<ProcessCategoryEntity>> all() {
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

	@RequestMapping(value = { "/add" }, method = RequestMethod.POST)
	public JsonResult<Integer> add(HttpServletRequest request,@RequestBody ProcessCategoryEntity processCategoryEntity) {
		UserEntity userEntity = (UserEntity) request.getSession().getAttribute("user");
		int userId = 0;
		if (userEntity != null) {
			userId = userEntity.getId();
		}
		processCategoryEntity.setCreateBy(userId);
		ProcessCategoryService.getInstance().insert(processCategoryEntity);
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(processCategoryEntity.getId());
		return jr;
	}

	@RequestMapping(value = { "/disable" }, method = RequestMethod.POST)
	public JsonResult<Integer> disable(@RequestParam(value = "Ids") int id) {
		int result = ProcessCategoryService.getInstance().disable(id);
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(result);
		return jr;
	}

	@RequestMapping(value = { "/enable" }, method = RequestMethod.POST)
	public JsonResult<Integer> enable(@RequestParam(value = "Ids") int id) {
		int result = ProcessCategoryService.getInstance().enable(id);
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(result);
		return jr;
	}

	@RequestMapping(value = { "/delete" }, method = RequestMethod.POST)
	public JsonResult<Integer> delete(@RequestParam(value = "Ids") int id) {
		int result = ProcessCategoryService.getInstance().delete(id);
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(result);
		return jr;
	}

	@RequestMapping(value = { "/get" }, method = RequestMethod.GET)
	public JsonResult<ProcessCategoryEntity> get(int id) {
		ProcessCategoryEntity processCategoryEntity = ProcessCategoryService.getInstance().get(id);
		JsonResult<ProcessCategoryEntity> jr = new JsonResult<ProcessCategoryEntity>();
		jr.setCode(200);
		jr.setData(processCategoryEntity);
		return jr;
	}

	@RequestMapping(value = { "/update" }, method = RequestMethod.POST)
	public JsonResult<Integer> update(HttpServletRequest request,@RequestBody ProcessCategoryEntity processCategoryEntity) {
		UserEntity userEntity = (UserEntity) request.getSession().getAttribute("user");
		int userId = 0;
		if (userEntity != null) {
			userId = userEntity.getId();
		}
		processCategoryEntity.setUpdateBy(userId);
		int result = ProcessCategoryService.getInstance().update(processCategoryEntity);
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(result);
		return jr;
	}

}