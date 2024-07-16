package icu.kevin.springpart.circular.lazy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfig.class })
public class CircularDependencyIntegrationTest {

    // Error creating bean with name 'circularDependencyA': Requested bean is currently in creation: Is there an unresolvable circular reference?
    @Test
    public void givenCircularDependency_whenConstructorInjection_thenItFails() {
        // 测试可以为空，因为在上下文加载期间将检测到循环依赖关系
    }
}
