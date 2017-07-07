import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import aliyun.oss.AliyunOSSManager;
import commons.utils.SpringUtils;

/**
 * @author chenyifei
 * @date 2017-06-22
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-config.xml"})
public class AppTest {
	
	
	@Test
	public void testProperties(){
		AliyunOSSManager manager = (AliyunOSSManager) SpringUtils.getBean("aliyunOSSManager");
		System.out.println(manager.getAccessKeyId());	
		
	}
}
