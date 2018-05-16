package cornucopia.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import cornucopia.dao.OrgDao;
import cornucopia.entity.OrgEntity;
import cornucopia.model.TreeViewModel;
import cornucopia.util.MyBatisHelper;

public class OrgService {
	
	private static OrgService instance = new OrgService();

	private OrgService() {
	}

	public static OrgService getInstance() {
		return instance;
	}

	OrgDao orgdao = MyBatisHelper.getMapper(OrgDao.class);
	
	public List<OrgEntity> getAllOrgs(){
		return orgdao.getAllOrgs();
	}
	
	public List<TreeViewModel> getAllOrgTree() {
		List<OrgEntity> orgs = getAllOrgs();
		List<TreeViewModel> tvms = buildOrgTree(0, orgs);
		return tvms;
	}

	private List<TreeViewModel> buildOrgTree(int parentId, List<OrgEntity> orgs) {
		List<TreeViewModel> tvms = new ArrayList<TreeViewModel>();
		List<OrgEntity> curOrgs = orgs.stream().filter((OrgEntity r) -> r.getParentId() == parentId)
				.collect(Collectors.toList());
		for (OrgEntity org : curOrgs) {
			TreeViewModel tvm = new TreeViewModel();
			tvm.setId(org.getId());
			tvm.setName(org.getName());
			if(org.getIcon()==null || org.getIcon().equals("")) {
				if(org.getParentId()==0) {
					tvm.setIcon("fa-th");
				}else {
					tvm.setIcon("fa-circle-o");
				}
			}else {
				tvm.setIcon(org.getIcon());
			}
			List<TreeViewModel> temp = buildOrgTree(org.getId(), orgs);
			tvm.setChildren(temp);
			tvms.add(tvm);
		}
		return tvms;
	}
}
