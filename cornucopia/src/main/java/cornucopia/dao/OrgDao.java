package cornucopia.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import cornucopia.entity.OrgEntity;

public interface OrgDao {
	@Select("call sp_org_all()")
    public List<OrgEntity> getAllOrgs();
}
