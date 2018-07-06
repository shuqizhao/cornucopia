package cornucopia;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;

import cornucopia.service.ProcessDiagramService;
import cornucopia.util.ActivitiHelper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CornucopiaApplicationTests {

	@Test
	public void contextLoads() throws IOException, JAXBException {
		// ProcessEngine processEngine = ActivitiHelper.GetEngine();
		// System.out.println(processEngine);
		// List<ProcessCategoryEntity> list =
		// ProcessCategoryService.getInstance().getAll();
		// System.out.println(list.size());
//		String id = ProcessDiagramService.getInstance().deploy(63);
		//init database
//		ActivitiHelper.GetEngine();
//		System.out.println(1);
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		String jsonString = beanToXml(list,ArrayList.class);  
		System.out.println(jsonString);
	}
	
	 public static String beanToXml(Object obj,Class<?> load) throws JAXBException{
		          JAXBContext context = JAXBContext.newInstance(load);
		          Marshaller marshaller = context.createMarshaller();
		          marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		          marshaller.setProperty(Marshaller.JAXB_ENCODING, "GBK");
		          StringWriter writer = new StringWriter();
		          marshaller.marshal(obj,writer);
		          return writer.toString();
		      }

}
