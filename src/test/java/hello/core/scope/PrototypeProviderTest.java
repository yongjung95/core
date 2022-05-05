package hello.core.scope;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import static org.assertj.core.api.Assertions.assertThat;

public class PrototypeProviderTest {

    @Test
    void providerTest() {
        AnnotationConfigApplicationContext ac = new
                AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(1);
    }

    static class ClientBean {
//        @Autowired
//        private ApplicationContext ac; // 1.스프링 컨테이너에 요청하는 방법

//        @Autowired
//        private ObjectProvider<PrototypeBean> prototypeBeanProvider; // 2.ObjectFactory, ObjectProvider 방법

        @Autowired
        private Provider<PrototypeBean> provider; // 3.JSR-330 Provider 방법

        public int logic() {
//            PrototypeBean prototypeBean = ac.getBean(PrototypeBean.class); // 1.스프링 컨테이너에 요청하는 방법

//            PrototypeBean prototypeBean = prototypeBeanProvider.getObject(); // 2.ObjectFactory, ObjectProvider 방법

            PrototypeBean prototypeBean = provider.get(); // 3.JSR-330 Provider 방법
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }
    }
    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init " + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }
}
