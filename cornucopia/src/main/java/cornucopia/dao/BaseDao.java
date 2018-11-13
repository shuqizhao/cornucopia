package cornucopia.dao;

import java.util.List;

// import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
// import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

public interface BaseDao<T> {

	@Select("call sp_common_all(#{tn})")
	public List<T> getAll(@Param("tn") String tn);

	@Select("call sp_common_exists(#{tn},#{name})")
	public int exists(@Param("tn") String tn, @Param("name") String name);

	// @Insert("call sp_common_insert(#{tn},#{t.name})")
	// @SelectKey(statement = "Select LAST_INSERT_ID()", keyProperty = "t.id", before = false, resultType = int.class)
	// public int insert(@Param("tn") String tn, @Param("t") T t);

	@Update("call sp_common_disable(#{tn},#{id})")
	public int disable(@Param("tn") String tn, @Param("id") int id);

	@Update("call sp_common_enable(#{tn},#{id})")
	public int enable(@Param("tn") String tn, @Param("id") int id);

	@Update("call sp_common_delete(#{tn},#{id})")
	public int delete(@Param("tn") String tn, @Param("id") int id);

	@Select("call sp_common_get(#{tn},#{id})")
	public T get(@Param("tn") String tn, @Param("id") int id);

	@Select("call sp_common_update(#{tn},#{t.id},#{t.name})")
	public int update(@Param("tn") String tn, @Param("t") T t);
}
