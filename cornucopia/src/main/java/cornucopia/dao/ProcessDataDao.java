package cornucopia.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.mapping.StatementType;

import cornucopia.entity.ProcessDataEntity;
import cornucopia.util.PagingParameters;

public interface ProcessDataDao {
	@Insert("call sp_process_data_insert(#{pd.processId},#{pd.bizData},#{pd.formCode},#{pd.createBy})")
	@SelectKey(statement="Select LAST_INSERT_ID()", keyProperty="pd.id", before=false, resultType=int.class)
	public int insert(@Param("pd") ProcessDataEntity processDataEntity);

	@Select("call sp_process_data_get_by_launched_list(#{pp.start},#{pp.length},#{userId},#{pp.totalRows,mode=OUT,jdbcType=INTEGER})")
	@Options(statementType = StatementType.CALLABLE)
	public List<ProcessDataEntity> launchedList(@Param("pp")PagingParameters pp,@Param("userId")int userId);
}
