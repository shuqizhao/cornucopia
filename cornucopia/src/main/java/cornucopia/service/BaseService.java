package cornucopia.service;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import cornucopia.dao.BaseDao;
import cornucopia.util.MyBatisHelper;

public class BaseService<Dao extends BaseDao<Entity>, Entity> {
	protected String getName() {
		return "";
	}
	@SuppressWarnings("unchecked")
	public Class<Dao> getDaoClass()
    {
        Class<Dao> tClass = (Class<Dao>)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return tClass;
    }

	protected Dao tdao = MyBatisHelper.getMapper(getDaoClass());

	public List<Entity> getAll() {
		return tdao.getAll(getName());
	}

	public int exists(String name) {
		return tdao.exists(getName(), name);
	}

	// public int insert(Entity t) {
	// 	return tdao.insert(getName(), t);
	// }

	public int disable(int id) {
		return tdao.disable(getName(), id);
	}

	public int enable(int id) {
		return tdao.enable(getName(), id);
	}

	public int delete(int id) {
		return tdao.delete(getName(), id);
	}

	public Entity get(int id) {
		return tdao.get(getName(), id);
	}

	// public int update(Entity t) {
	// 	return tdao.update(getName(), t);
	// }
}
