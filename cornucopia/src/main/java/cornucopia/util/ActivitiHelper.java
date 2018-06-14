package cornucopia.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ActivitiHelper {
	private static ProcessEngine processEngine;

	static {
		try {
			String path = xcfg4j.Helper.getAppCfgFolder();
			ActivitiEntity.getInstance(ActivitiEntity.class);
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(new File(path + "/activiti.config"));
			Element rootEl = document.getRootElement();
			rootEl.remove(rootEl.attribute("majorVersion"));
			rootEl.remove(rootEl.attribute("minorVersion"));
			String xmlStr = document.asXML().replace("xsi", "xmlns:xsi").replace("schemaLocation",
					"xsi:schemaLocation");
			InputStream inputStream = new ByteArrayInputStream(xmlStr.getBytes());
			ProcessEngineConfiguration conf = ProcessEngineConfiguration
					.createProcessEngineConfigurationFromInputStream(inputStream, "processEngineConfiguration");
			processEngine = conf.buildProcessEngine();
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ProcessEngine GetEngine() {
		return processEngine;
	}
}
