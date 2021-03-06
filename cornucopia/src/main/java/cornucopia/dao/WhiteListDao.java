package cornucopia.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import cornucopia.entity.WhiteListEntity;

public interface WhiteListDao {
	@Select("call sp_white_list_all()")
    public List<WhiteListEntity> getAllWhiteListEntities();
	
	@Select("call sp_whitelist_get_by_page(#{start},#{length})")
	public List<WhiteListEntity> getWhiteListByPage(@Param("start") int start, @Param("length") int length);
	
	@Insert("call sp_whitelist_insert(#{wle.name},#{wle.url},#{wle.type})")
	@SelectKey(statement="Select LAST_INSERT_ID()", keyProperty="wle.id", before=false, resultType=int.class)
	public int insert(@Param("wle") WhiteListEntity wle);
	
	@Update("call sp_whitelist_update(#{wle.id},#{wle.name},#{wle.url},#{wle.type})")
	public int update(@Param("wle") WhiteListEntity wle);
	
	@Update("call sp_whitelist_disable(#{id})")
	public int disable(@Param("id")int id);
	
	@Update("call sp_whitelist_enable(#{id})")
	public int enable(@Param("id")int id);
	
	@Update("call sp_whitelist_delete(#{id})")
	public int delete(@Param("id")int id);

	@Select("call sp_whitelist_get(#{id})")
	public WhiteListEntity get(@Param("id") int id);
}
