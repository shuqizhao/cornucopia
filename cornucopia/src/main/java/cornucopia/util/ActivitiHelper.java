package cornucopia.util;

import javax.sql.DataSource;

import org.activiti.engine.ProcessEngine;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.transaction.PlatformTransactionManager;

public class ActivitiHelper {
	private static ProcessEngine processEngine;

	static {
		try {
			SpringProcessEngineConfiguration sec = new SpringProcessEngineConfiguration();
			sec.setDataSource(SpringUtil.getBean(DataSource.class));
			sec.setTransactionManager(SpringUtil.getBean(PlatformTransactionManager.class));
			sec.setDatabaseSchemaUpdate("true");
			processEngine = sec.buildProcessEngine();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ProcessEngine GetEngine() {
		return processEngine;
	}
}
