package cornucopia;

import java.io.IOException;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cornucopia.service.ProcessDiagramService;
import cornucopia.util.ActivitiHelper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CornucopiaApplicationTests {

	@Test
	public void contextLoads() throws IOException {
		// ProcessEngine processEngine = ActivitiHelper.GetEngine();
		// System.out.println(processEngine);
		// List<ProcessCategoryEntity> list =
		// ProcessCategoryService.getInstance().getAll();
		// System.out.println(list.size());
		String id = ProcessDiagramService.getInstance().deploy(63);
		List<ProcessDefinition> list = ActivitiHelper.GetEngine().getRepositoryService().createProcessDefinitionQuery()
				.deploymentId(id).list();
		for (ProcessDefinition a : list) {
			System.out.println(a.getId() + "   " + a.getKey());
		}
		System.out.println(id);
	}

}
