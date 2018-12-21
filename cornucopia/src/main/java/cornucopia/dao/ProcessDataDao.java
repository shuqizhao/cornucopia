package cornucopia.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;

import cornucopia.entity.ProcessDataEntity;
import cornucopia.model.ProcessMonitorViewModel;
import cornucopia.model.ProcessSearchViewModel;
import cornucopia.util.PagingParameters;

public interface ProcessDataDao {
	@Insert("call sp_process_data_insert(#{pd.processId},#{pd.bizData},#{pd.jsonData},#{pd.formCode},#{pd.createBy},#{pd.procinstId},#{pd.levelCount},#{pd.status},#{pd.callbackStatus},#{pd.processStatus})")
	@SelectKey(statement = "Select LAST_INSERT_ID()", keyProperty = "pd.id", before = false, resultType = int.class)
	public int insert(@Param("pd") ProcessDataEntity processDataEntity);

	@Select("call sp_process_data_get_by_launched_list(#{pp.start},#{pp.length},#{userId},#{psvm.columnName},#{psvm.columnValue},#{psvm.processId},#{pp.totalRows,mode=OUT,jdbcType=INTEGER})")
	@Options(statementType = StatementType.CALLABLE)
	public List<ProcessDataEntity> launchedList(@Param("pp") PagingParameters pp, @Param("userId") int userId,
			@Param("psvm") ProcessSearchViewModel psvm);

	@Select("call sp_process_data_get_by_monitor_list(#{pp.start},#{pp.length},#{pmvm.processId},#{pmvm.formCode},#{pmvm.createBy},#{pmvm.createTime},#{pmvm.processStatus},#{pp.totalRows,mode=OUT,jdbcType=INTEGER})")
	@Options(statementType = StatementType.CALLABLE)
	public List<ProcessMonitorViewModel> monitorList(@Param("pp") PagingParameters pp,
			@Param("pmvm") ProcessMonitorViewModel pmvm);

	@Select("call sp_process_data_get_by_mylaunched_category_group(#{userId})")
	@Options(statementType = StatementType.CALLABLE)
	public List<ProcessDataEntity> mylaunchedCatetoryGroup(@Param("userId") int userId);

	@Select("call sp_process_data_get_by_mytask_category_group(#{userId})")
	@Options(statementType = StatementType.CALLABLE)
	public List<ProcessDataEntity> myTaskCatetoryGroup(@Param("userId") int userId);

	@Select("call sp_process_data_get_by_mydeal_category_group(#{userId})")
	@Options(statementType = StatementType.CALLABLE)
	public List<ProcessDataEntity> myDealCatetoryGroup(@Param("userId") int userId);

	@Select("call sp_process_data_get_by_mylaunched_group(#{userId},#{catetoryId})")
	@Options(statementType = StatementType.CALLABLE)
	public List<ProcessDataEntity> launchedGroup(@Param("userId") int userId, @Param("catetoryId") int catetoryId);

	@Select("call sp_process_data_get_by_mytask_group(#{userId},#{catetoryId})")
	@Options(statementType = StatementType.CALLABLE)
	public List<ProcessDataEntity> taskGroup(@Param("userId") int userId, @Param("catetoryId") int catetoryId);

	@Select("call sp_process_data_get_by_mydealed_group(#{userId},#{catetoryId})")
	@Options(statementType = StatementType.CALLABLE)
	public List<ProcessDataEntity> dealedGroup(@Param("userId") int userId, @Param("catetoryId") int catetoryId);

	@Select("call sp_process_data_get_by_task_list(#{pp.start},#{pp.length},#{userId},#{psvm.columnName},#{psvm.columnValue},#{psvm.processId},#{pp.totalRows,mode=OUT,jdbcType=INTEGER})")
	@Options(statementType = StatementType.CALLABLE)
	public List<ProcessDataEntity> taskList(@Param("pp") PagingParameters pp, @Param("userId") int userId,
			@Param("psvm") ProcessSearchViewModel psvm);

	@Select("call sp_process_data_get_by_inst_id(#{instId})")
	public ProcessDataEntity getByInstId(@Param("instId") String instId);

	@Select("call sp_process_data_get_by_id(#{id})")
	public ProcessDataEntity get(@Param("id") int id);

	@Update("call sp_process_data_update(#{pd.id},#{pd.bizData},#{pd.jsonData},#{pd.updateBy},#{pd.levelCount},#{pd.status},#{pd.callbackStatus},#{pd.processStatus})")
	public int update(@Param("pd") ProcessDataEntity processDataEntity);

	@Select("call sp_process_data_get_form_code(#{formCode})")
	public ProcessDataEntity getByFormCode(@Param("formCode") String formCode);

	@Select("call sp_process_data_get_by_dealed_list(#{pp.start},#{pp.length},#{userId},#{psvm.columnName},#{psvm.columnValue},#{psvm.processId},#{pp.totalRows,mode=OUT,jdbcType=INTEGER})")
	@Options(statementType = StatementType.CALLABLE)
	public List<ProcessDataEntity> dealedList(@Param("pp") PagingParameters pp, @Param("userId") int userId,
			@Param("psvm") ProcessSearchViewModel psvm);

	@Update("call sp_process_data_delete(#{id})")
	@Options(statementType = StatementType.CALLABLE)
	public int delete(@Param("id") int id);

	@Update("call sp_process_data_undelete(#{id},#{procinstId})")
	@Options(statementType = StatementType.CALLABLE)
	public int unDelete(@Param("id") int id, @Param("procinstId") String procinstId);

	@Select("call sp_process_data_job_exists(#{procinstId})")
	@Options(statementType = StatementType.CALLABLE)
	public int jobExists(@Param("procinstId") String procinstId);
}
