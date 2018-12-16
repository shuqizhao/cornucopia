package cornucopia.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;

import cornucopia.entity.ProcessDataEntity;
import cornucopia.model.ProcessSearchViewModel;
import cornucopia.util.PagingParameters;

public interface ProcessDataDao {
	@Insert("call sp_process_data_insert(#{pd.processId},#{pd.bizData},#{pd.jsonData},#{pd.formCode},#{pd.createBy},#{pd.procinstId},#{pd.levelCount},#{pd.status},#{pd.callbackStatus},#{processStatus})")
	@SelectKey(statement = "Select LAST_INSERT_ID()", keyProperty = "pd.id", before = false, resultType = int.class)
	public int insert(@Param("pd") ProcessDataEntity processDataEntity);

	@Select("call sp_process_data_get_by_launched_list(#{pp.start},#{pp.length},#{userId},#{psvm.processId},#{pp.totalRows,mode=OUT,jdbcType=INTEGER})")
	@Options(statementType = StatementType.CALLABLE)
	public List<ProcessDataEntity> launchedList(@Param("pp") PagingParameters pp, @Param("userId") int userId,
			@Param("psvm") ProcessSearchViewModel psvm);

	@Select("call sp_process_data_get_by_category_group(#{userId})")
	@Options(statementType = StatementType.CALLABLE)
	public List<ProcessDataEntity> catetoryGroup(@Param("userId") int userId);

	@Select("call sp_process_data_get_by_launched_group(#{userId},#{catetoryId})")
	@Options(statementType = StatementType.CALLABLE)
	public List<ProcessDataEntity> launchedGroup(@Param("userId") int userId, @Param("catetoryId") int catetoryId);

	@Select("call sp_process_data_get_by_task_list(#{pp.start},#{pp.length},#{userId},#{pp.totalRows,mode=OUT,jdbcType=INTEGER})")
	@Options(statementType = StatementType.CALLABLE)
	public List<ProcessDataEntity> taskList(@Param("pp") PagingParameters pp, @Param("userId") int userId);

	@Select("call sp_process_data_get_by_inst_id(#{instId})")
	public ProcessDataEntity getByInstId(@Param("instId") String instId);

	@Select("call sp_process_data_get_by_id(#{id})")
	public ProcessDataEntity get(@Param("id") int id);

	@Update("call sp_process_data_update(#{pd.id},#{pd.bizData},#{pd.jsonData},#{pd.updateBy},#{pd.levelCount},#{pd.status},#{pd.callbackStatus},#{processStatus})")
	public int update(@Param("pd") ProcessDataEntity processDataEntity);

	@Select("call sp_process_data_get_form_code(#{formCode})")
	public ProcessDataEntity getByFormCode(@Param("formCode") String formCode);

	@Select("call sp_process_data_get_by_dealed_list(#{pp.start},#{pp.length},#{userId},#{pp.totalRows,mode=OUT,jdbcType=INTEGER})")
	@Options(statementType = StatementType.CALLABLE)
	public List<ProcessDataEntity> dealedList(@Param("pp") PagingParameters pp, @Param("userId") int userId);
}
