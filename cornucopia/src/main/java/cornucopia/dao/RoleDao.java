package cornucopia.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import cornucopia.entity.RoleEntity;

public interface RoleDao {
	@Select("call sp_role_get_by_page(#{start},#{length})")
	public List<RoleEntity> getRolesByPage(@Param("start") int start, @Param("length") int length);
}
