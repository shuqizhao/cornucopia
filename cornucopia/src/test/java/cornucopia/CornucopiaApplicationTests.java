package cornucopia;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import cornucopia.util.ActivitiHelper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CornucopiaApplicationTests {

	@Test
	public void contextLoads()  {
		// ProcessEngine processEngine = ActivitiHelper.GetEngine();
		// System.out.println(processEngine);
		// List<ProcessCategoryEntity> list =
		// ProcessCategoryService.getInstance().getAll();
		// System.out.println(list.size());
//		String id = ProcessDiagramService.getInstance().deploy(63);
		ActivitiHelper.GetEngine();
		System.out.println(1);
	}
}
