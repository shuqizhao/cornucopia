package cornucopia;

import org.activiti.engine.ProcessEngine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import cornucopia.entity.ProcessDataEntity;
import cornucopia.service.ProcessDataService;
import cornucopia.util.ActivitiHelper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CornucopiaApplicationTests {

	@Test
	@Transactional(rollbackFor = Exception.class)
	public void contextLoads() {
		// ProcessEngine processEngine = ActivitiHelper.GetEngine();
		// System.out.println(processEngine);
		// List<ProcessCategoryEntity> list =
		// ProcessCategoryService.getInstance().getAll();
		// System.out.println(list.size());
		// String id = ProcessDiagramService.getInstance().deploy(63);
		// ActivitiHelper.GetEngine();
		// System.out.println(1);
		// List<?> acs = ApproveService.getInstance().getConditions(39);
		// ProcessDataEntity pde = ProcessDataService.getInstance().get(134);
		// @SuppressWarnings("unchecked")
		// boolean result = ConditionUtil.calculateCondition((List<ConditionEntity>)acs,
		// pde);
		// System.out.println(result);
		//  ProcessDataEntity processDataEntity = new ProcessDataEntity();
		//  ProcessDataService.getInstance().insert(processDataEntity);

		ProcessEngine processEngine = ActivitiHelper.GetEngine();
		System.out.println(processEngine);
	}
}
