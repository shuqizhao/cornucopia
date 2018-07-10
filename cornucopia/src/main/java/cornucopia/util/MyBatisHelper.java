package cornucopia.util;

import org.mybatis.spring.SqlSessionTemplate;

public class MyBatisHelper {
	public static <T> T getMapper(Class<T> mapper) {
		T t = SpringUtil.getBean(SqlSessionTemplate.class).getMapper(mapper);
		return t;
	}
}