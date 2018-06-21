package cornucopia.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import cornucopia.entity.ProcessCategoryEntity;

public interface ProcessCategoryDao extends BaseDao<ProcessCategoryEntity> {
	@Select("call sp_process_category_all()")
	public List<ProcessCategoryEntity> getAllByOrder();
}
