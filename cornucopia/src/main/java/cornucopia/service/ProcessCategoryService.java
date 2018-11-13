package cornucopia.service;

import java.util.List;

import cornucopia.dao.ProcessCategoryDao;
import cornucopia.entity.ProcessCategoryEntity;
import cornucopia.util.PagingParameters;

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

	public List<ProcessCategoryEntity> list(PagingParameters pp){
		return tdao.list(pp);
	}

	public int insert(ProcessCategoryEntity processCategoryEntity){
		return tdao.insert(processCategoryEntity);
	}
}
