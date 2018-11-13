package cornucopia.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.mapping.StatementType;

import cornucopia.entity.ProcessCategoryEntity;
import cornucopia.util.PagingParameters;

public interface ProcessCategoryDao extends BaseDao<ProcessCategoryEntity> {
	@Select("call sp_process_category_all()")
	public List<ProcessCategoryEntity> getAllByOrder();

	@Select("call sp_process_category_list(#{pp.start},#{pp.length},#{pp.totalRows,mode=OUT,jdbcType=INTEGER})")
	@Options(statementType = StatementType.CALLABLE)
	public List<ProcessCategoryEntity> list(@Param("pp") PagingParameters pp);

	@Insert("call sp_process_category_insert(#{t.name},#{t.icon},#{t.orderNum})")
	@SelectKey(statement = "Select LAST_INSERT_ID()", keyProperty = "t.id", before = false, resultType = int.class)
	public int insert(@Param("t") ProcessCategoryEntity t);
}
