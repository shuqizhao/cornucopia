package cornucopia.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cornucopia.entity.JsonResult;
import cornucopia.entity.WhiteListEntity;
import cornucopia.service.WhiteListService;
import cornucopia.util.DataTableParameter;
import cornucopia.util.DataTableResult;

@RestController
@RequestMapping("/whitelist")
public class WhiteListController {
	@RequestMapping(value = { "/whiteList" }, method = RequestMethod.POST)
	public DataTableResult<WhiteListEntity> list(DataTableParameter dtp) {
		List<WhiteListEntity> whiteList = WhiteListService.getInstance().getWhiteListByPage(dtp.getiDisplayStart(),
				dtp.getiDisplayLength());
		int count = 0;
		if (whiteList != null && whiteList.size() > 0) {
			count = whiteList.get(0).getTotalCount();
		}
		DataTableResult<WhiteListEntity> dtr = new DataTableResult<WhiteListEntity>(dtp.getsEcho() + 1, count, count,
				whiteList);
		return dtr;
	}
	
	@RequestMapping(value = { "/add" }, method = RequestMethod.POST)
	public JsonResult<Integer> add(WhiteListEntity whiteListEntity) {
		WhiteListService.getInstance().addWhiteListByPage(whiteListEntity);
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(whiteListEntity.getId());
		return jr;
	}
	
	@RequestMapping(value = { "/disable" }, method = RequestMethod.POST)
	public JsonResult<Integer> disable(@RequestParam(value = "Ids") int id) {
		int result = WhiteListService.getInstance().disable(id);
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(result);
		return jr;
	}
	
	@RequestMapping(value = { "/enable" }, method = RequestMethod.POST)
	public JsonResult<Integer> enable(@RequestParam(value = "Ids") int id) {
		int result = WhiteListService.getInstance().enable(id);
		JsonResult<Integer> jr = new JsonResult<Integer>();
		jr.setCode(200);
		jr.setData(result);
		return jr;
	}
	
	@RequestMapping(value = { "/get" }, method = RequestMethod.GET)
	public JsonResult<WhiteListEntity> get(int id) {
		WhiteListEntity whiteListEntity = WhiteListService.getInstance().getWhiteListEntity(id);
		JsonResult<WhiteListEntity> jr = new JsonResult<WhiteListEntity>();
		jr.setCode(200);
		jr.setData(whiteListEntity);
		return jr;
	}
}
