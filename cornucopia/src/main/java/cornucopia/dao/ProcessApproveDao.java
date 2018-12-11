package cornucopia.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import cornucopia.entity.ProcessApproveEntity;

public interface ProcessApproveDao {
    @Insert("call sp_process_approve_insert(#{pd.processId},#{pd.processDataId},#{pd.createBy},#{pd.procinstId},#{pd.levelCount},#{pd.stepName},#{pd.userId},#{pd.guid},#{pd.parentGuid})")
	@SelectKey(statement = "Select LAST_INSERT_ID()", keyProperty = "pd.id", before = false, resultType = int.class)
	public int insert(@Param("pd") ProcessApproveEntity processApproveEntity);

	@Update("call sp_process_approve_update_current(#{id},#{status})")
	public int updateCurrent(@Param("id")int id,@Param("status") int status);

	@Select("call sp_process_approve_get_after_sgin(#{pdId},#{userId})")
	public ProcessApproveEntity getAfterSign(@Param("pdId")int pdId,@Param("userId") int userId);
}