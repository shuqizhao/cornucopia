package cornucopia.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cornucopia.entity.JsonResult;
import cornucopia.entity.ProcessCategoryEntity;
import cornucopia.entity.ProcessEntity;
import cornucopia.service.ProcessCategoryService;
import cornucopia.service.ProcessService;
import cornucopia.util.DataTableParameter;
import cornucopia.util.DataTableResult;
import cornucopia.util.PagingParameters;

@RestController
@RequestMapping("/processCatetory")
public class ProcessCatetoryContorller {

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
}