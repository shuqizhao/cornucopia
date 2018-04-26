package cornucopia.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import cornucopia.entity.WhiteListEntity;

public interface WhiteListDao {
	@Select("call sp_white_list_all()")
    public List<WhiteListEntity> getAllWhiteListEntities();
}
