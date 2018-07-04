package cornucopia.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import cornucopia.entity.ProcessDataEntity;

public interface ProcessDataHistoryDao {
	@Insert("call sp_process_data_history_insert(#{pd.id},#{pd.processId},#{pd.bizData},#{pd.formCode},#{pd.createBy},#{pd.procinstId},#{pd.stepName},#{pd.updateBy})")
	public int insert(@Param("pd") ProcessDataEntity processDataEntity);
}
