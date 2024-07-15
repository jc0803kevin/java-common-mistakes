package icu.kevin.springpart.beansingletonandorder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SayBye extends SayService {
    // @Scope 属性proxyMode源码出处
    // org.springframework.context.annotation.AnnotationConfigUtils.applyScopedProxyMode
    @Override
    public void say() {
        super.say();
        log.info("bye");
    }
}
