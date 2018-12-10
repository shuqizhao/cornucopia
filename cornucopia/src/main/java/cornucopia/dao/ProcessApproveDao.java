package cornucopia.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;

import cornucopia.entity.ProcessApproveEntity;

public interface ProcessApproveDao {
    @Insert("call sp_process_approve_insert(#{pd.processId},#{pd.processDataId},#{pd.createBy},#{pd.procinstId},#{pd.levelCount},#{pd.stepName},#{pd.userId},#{pd.processInstDiagramId})")
	@SelectKey(statement = "Select LAST_INSERT_ID()", keyProperty = "pd.id", before = false, resultType = int.class)
	public int insert(@Param("pd") ProcessApproveEntity processApproveEntity);
}