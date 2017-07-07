package commons.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author chenyifei
 * @date 2017-07-07
 */
public class SpringUtils implements ApplicationContextAware {

	private static ApplicationContext context;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}

	public static Object getBean(String key) {
        if (context == null) {
            throw new NullPointerException("ApplicationContext is null");
        }
        return context.getBean(key);
    }
}
