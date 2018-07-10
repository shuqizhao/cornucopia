package cornucopia;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cornucopia.util.AppSettings;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@Configuration
@EnableTransactionManagement
@RestController
public class CornucopiaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CornucopiaApplication.class, args);
	}

	@RequestMapping(value = { "/", "" })
	public String hello() {

		return "hello bpm !!";
	}

	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		try {
			String driver = AppSettings.getInstance(AppSettings.class).get("db_driver");
			String url  = AppSettings.getInstance(AppSettings.class).get("db_url");
			String user  = AppSettings.getInstance(AppSettings.class).get("db_user");
			String pwd  = AppSettings.getInstance(AppSettings.class).get("db_pwd");
			dataSource.setDriverClassName(driver);
			dataSource.setUrl(url);
			dataSource.setUsername(user);
			dataSource.setPassword(pwd);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return dataSource;
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) {
		SqlSessionFactory factory = null;
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		bean.setConfigLocation(resolver.getResource("classpath:mybatis.xml"));
		try {
			factory = bean.getObject();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return factory;
	}

	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}
