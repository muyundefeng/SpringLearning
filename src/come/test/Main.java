package come.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import come.log.muyundefeng.csdn.Actor;

public class Main {
	public static void main(String[]args)
	{
		ApplicationContext context=new ClassPathXmlApplicationContext("Bean.xml");
		Actor actor=(Actor)context.getBean("show");
		actor.perform();
	}

}
