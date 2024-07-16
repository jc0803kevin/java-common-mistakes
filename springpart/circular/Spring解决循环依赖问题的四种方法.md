
# Spring解决循环依赖问题的四种方法

* @Lazy方式
* 使用Setter/Field Injection
* 使用`@PostConstruct`
* 实现 ApplicationContextAware 和 InitializingBean

不管使用那种方式，最佳的还是通过调整代码结构，从根上设计从而达到避免。


## 定义两个相互依赖的 bean（通过构造函数注入）

```java
package icu.kevin.springpart.circular.constructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CircularDependencyA {

    private CircularDependencyB circB;

    @Autowired
    public CircularDependencyA(CircularDependencyB circB) {
        this.circB = circB;
    }
}

```

```java
package icu.kevin.springpart.circular.constructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CircularDependencyB {

    private CircularDependencyA circA;

    @Autowired
    public CircularDependencyB(CircularDependencyA circA) {
        this.circA = circA;
    }
}
```

```java
package icu.kevin.springpart.circular.constructor;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "icu.kevin.springpart.circular.constructor" })
public class TestConfig {
}

```

* 编写测试用例，测试是否存在循环依赖
```java
package icu.kevin.springpart.circular.constructor;

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

```

## @Lazy方式

`@Lazy`告诉 Spring 延迟初始化其中一个 bean。因此，它不会完全初始化 Bean，而是创建一个代理将其注入另一个 Bean。注入的 Bean 只有在第一次需要时才会完全创建。

@Lazy 注解是通过建立一个中间代理层，来破解循环依赖的。

```java
package icu.kevin.springpart.circular.lazy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class CircularDependencyA {

    private CircularDependencyB circB;

    @Autowired
    public CircularDependencyA(@Lazy CircularDependencyB circB) {
        this.circB = circB;
    }
}

```

```java
package icu.kevin.springpart.circular.lazy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CircularDependencyB {

    private CircularDependencyA circA;

    @Autowired
    public CircularDependencyB(CircularDependencyA circA) {
        this.circA = circA;
    }
}
```

```java
package icu.kevin.springpart.circular.lazy;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "icu.kevin.springpart.circular.lazy" })
public class TestConfig {
}

```

```java
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

```

启动可以看出，在上下文检查循环依赖是可以正常通过的

查看源码`ContextAnnotationAutowireCandidateResolver#getLazyResolutionProxyIfNecessary`首先会调用 isLazy 去判断一下是否需要延迟加载，
如果需要，则调用 `buildLazyResolutionProxy` 方法构建一个延迟加载的对象；

## 使用Setter/Field Injection

在setter注入的时候发生的依赖循环是可以被解决的，但只能解决单例情况的循环依赖。
对于setter注入造成的依赖是通过Spring容器提前暴露刚刚完成构造器但还没有进行注入的bean来完成的。通过提前暴露一个单例工厂让其他的bean可以引用到该Bean。

```java
package icu.kevin.springpart.circular.setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CircularDependencyA {

    private CircularDependencyB circB;

    public CircularDependencyB getCircB() {
        return circB;
    }

    @Autowired
    public void setCircB(CircularDependencyB circB) {
        this.circB = circB;
    }
}

```

```java
package icu.kevin.springpart.circular.setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CircularDependencyB {

    private CircularDependencyA circA;

    private String message = "Hi!";

    public CircularDependencyA getCircA() {
        return circA;
    }

    @Autowired
    public void setCircA(CircularDependencyA circA) {
        this.circA = circA;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
```

```java
package icu.kevin.springpart.circular.setter;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "icu.kevin.springpart.circular.setter" })
public class TestConfig {
}

```

```java
package icu.kevin.springpart.circular.setter;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfig.class })
public class CircularDependencyIntegrationTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void givenCircularDependency_whenConstructorInjection_thenItFails() {
        // Empty test; we just want the context to load

        CircularDependencyA circA = context.getBean(CircularDependencyA.class);

        Assert.assertEquals("Hi!", circA.getCircB().getMessage());
    }
}

```

## 使用`@PostConstruct`

```java
package icu.kevin.springpart.circular.postConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CircularDependencyA {

    @Autowired
    private CircularDependencyB circB;

    @PostConstruct
    public void init(){
        circB.setCircA(this);
    }

    public CircularDependencyB getCircB() {
        return circB;
    }
}

```

```java
package icu.kevin.springpart.circular.postConstruct;

import org.springframework.stereotype.Component;

@Component
public class CircularDependencyB {

    private CircularDependencyA circA;

    private String message = "Hi!";

    public CircularDependencyA getCircA() {
        return circA;
    }

    public void setCircA(CircularDependencyA circA) {
        this.circA = circA;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
```

```java
package icu.kevin.springpart.circular.postConstruct;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "icu.kevin.springpart.circular.postConstruct" })
public class TestConfig {
}

```

```java
package icu.kevin.springpart.circular.postConstruct;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfig.class })
public class CircularDependencyIntegrationTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void givenCircularDependency_whenConstructorInjection_thenItFails() {
        //

        CircularDependencyA circA = context.getBean(CircularDependencyA.class);

        Assert.assertEquals("Hi!", circA.getCircB().getMessage());
    }
}

```

## 实现 ApplicationContextAware 和 InitializingBean

如果其中一个 Bean 实现了 ApplicationContextAware，则该 Bean 可以访问 Spring 上下文，并可以从那里提取另一个 Bean。

通过实现 InitializingBean，我们指示该 Bean 在设置完所有属性后必须执行一些操作。在这种情况下，我们要手动设置依赖项。

```java
package icu.kevin.springpart.circular.initializingBean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class CircularDependencyA implements ApplicationContextAware, InitializingBean {

    private CircularDependencyB circB;

    private ApplicationContext context;

    public CircularDependencyB getCircB() {
        return circB;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        circB = context.getBean(CircularDependencyB.class);
    }

    @Override
    public void setApplicationContext(final ApplicationContext ctx) throws BeansException {
        context = ctx;
    }
}

```

```java
package icu.kevin.springpart.circular.initializingBean;

import org.springframework.stereotype.Component;

@Component
public class CircularDependencyB {

    private CircularDependencyA circA;

    private String message = "Hi!";

    public CircularDependencyA getCircA() {
        return circA;
    }

    public void setCircA(CircularDependencyA circA) {
        this.circA = circA;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
```

```java
package icu.kevin.springpart.circular.initializingBean;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "icu.kevin.springpart.circular.initializingBean" })
public class TestConfig {
}

```

```java
package icu.kevin.springpart.circular.initializingBean;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfig.class })
public class CircularDependencyIntegrationTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void givenCircularDependency_whenConstructorInjection_thenItFails() {
        // Empty test; we just want the context to load

        CircularDependencyA circA = context.getBean(CircularDependencyA.class);

        Assert.assertEquals("Hi!", circA.getCircB().getMessage());
    }
}

```