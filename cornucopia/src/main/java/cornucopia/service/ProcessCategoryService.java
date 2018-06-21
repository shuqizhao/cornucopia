package cornucopia.service;

import java.util.List;

import cornucopia.dao.ProcessCategoryDao;
import cornucopia.entity.ProcessCategoryEntity;

public class ProcessCategoryService extends BaseService<ProcessCategoryDao, ProcessCategoryEntity> {
	private static ProcessCategoryService instance = new ProcessCategoryService();

	private ProcessCategoryService() {
	}

	public static ProcessCategoryService getInstance() {
		return instance;
	}
	
	protected String getName() {
		return "sys_process_category";
	}
	
	public List<ProcessCategoryEntity> getAllByOrder(){
		return tdao.getAllByOrder();
	}
}
