package resttemplate;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import resttemplate.config.JavaConfig;

public class Main {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(JavaConfig.class);

        Communication communication = applicationContext.getBean(Communication.class);

        communication.work();

        //Вывод ключа
        System.out.println(communication.getKey());
    }
}
