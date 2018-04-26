package cornucopia.service;

import java.util.List;

import cornucopia.dao.WhiteListDao;
import cornucopia.entity.WhiteListEntity;
import cornucopia.util.MyBatisHelper;

public class WhiteListService {

	private static WhiteListService instance = new WhiteListService();

	private WhiteListService() {
	}

	public static WhiteListService getInstance() {
		return instance;
	}

	WhiteListDao whiteListDao = MyBatisHelper.getMapper(WhiteListDao.class);

	/**
	 * 允许白名单通过，不需要登陆也可以访问
	 * @param url
	 * @return
	 */
	public boolean allowPass(String url) {
		if (url == null)
			return false;
		
		List<WhiteListEntity> whiteListEntities = whiteListDao.getAllWhiteListEntities();
		for (WhiteListEntity whiteListEntity : whiteListEntities) {
			if (whiteListEntity.getUrl().toLowerCase().equals(url.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
}
