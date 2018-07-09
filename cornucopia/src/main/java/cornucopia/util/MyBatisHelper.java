package cornucopia.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

class MyBatisHelper1 {
	private static SqlSessionFactory sqlSessionFactory;

	static {
		try {
			String path = xcfg4j.Helper.getAppCfgFolder();
			MybaitsEntity.getInstance(MybaitsEntity.class);
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(new File(path + "/mybatis.config"));
			Element rootEl = document.getRootElement();
			rootEl.remove(rootEl.attribute("majorVersion"));
			rootEl.remove(rootEl.attribute("minorVersion"));
			String xmlStr = document.asXML();
			
			FileWriter fileWritter = new FileWriter(path + "/mybatis.config1", false);
			fileWritter.write(xmlStr);
			fileWritter.flush();
			fileWritter.close();
			
			InputStream inputStream = new ByteArrayInputStream(xmlStr.getBytes());
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static SqlSession getSession() {
		return sqlSessionFactory.openSession();
	}

	public static SqlSessionTemplate getSqlSessionTemplate() {
		SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
		return sqlSessionTemplate;
	}

	public static <T> T getMapper(Class<T> mapper) {
		T t = getSqlSessionTemplate().getMapper(mapper);
		return t;
	}
}

public class MyBatisHelper {
	private static SqlSessionTemplate sqlSessionTemplate;

	static {
		try {
			new MyBatisHelper1();
			String path = xcfg4j.Helper.getAppCfgFolder();
			ApplicationContextEntity.getInstance(ApplicationContextEntity.class);
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(new File(path + "/applicationContext.config"));
			Element rootEl = document.getRootElement();
			rootEl.remove(rootEl.attribute("majorVersion"));
			rootEl.remove(rootEl.attribute("minorVersion"));
			String xmlStr = document.asXML();
			File file = new File(path + "/applicationContext.config1");
			if (!file.exists()) {
				file.createNewFile();
			}
			// true = append file
			FileWriter fileWritter = new FileWriter(file, false);
			fileWritter.write(xmlStr);
			fileWritter.flush();
			fileWritter.close();
			@SuppressWarnings("resource")
			ApplicationContext ac = new FileSystemXmlApplicationContext("/"+file.getAbsolutePath());
			sqlSessionTemplate = (SqlSessionTemplate) ac.getBean("sqlSession");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static SqlSessionTemplate getSqlSessionTemplate() {
		return sqlSessionTemplate;
	}

	public static <T> T getMapper(Class<T> mapper) {
		T t = getSqlSessionTemplate().getMapper(mapper);
		return t;
	}
}
