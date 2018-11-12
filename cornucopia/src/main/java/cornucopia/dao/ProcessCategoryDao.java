package cornucopia.dao;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import cornucopia.entity.ProcessCategoryEntity;
import cornucopia.util.PagingParameters;

public interface ProcessCategoryDao extends BaseDao<ProcessCategoryEntity> {
	@Select("call sp_process_category_all()")
	public List<ProcessCategoryEntity> getAllByOrder();

	@Select("call sp_process_category_list(#{pp.start},#{pp.length},#{pp.totalRows,mode=OUT,jdbcType=INTEGER})")
	@Options(statementType = StatementType.CALLABLE)
	public List<ProcessCategoryEntity> list(@Param("pp") PagingParameters pp);
}
