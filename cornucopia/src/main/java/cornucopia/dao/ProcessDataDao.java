package cornucopia.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;

import cornucopia.entity.ProcessDataEntity;

public interface ProcessDataDao {
	@Insert("call sp_process_data_insert(#{pd.processId},#{pd.bizData})")
	@SelectKey(statement="Select LAST_INSERT_ID()", keyProperty="pd.id", before=false, resultType=int.class)
	public int insert(@Param("pd") ProcessDataEntity processDataEntity);
}
