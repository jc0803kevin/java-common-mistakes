package icu.kevin.springpart.circular.postConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CircularDependencyA {

    @Autowired
    private CircularDependencyB circB;

    // 手动将自身依赖项赋值过去，从而打破循环
    @PostConstruct
    public void init(){
        circB.setCircA(this);
    }

    public CircularDependencyB getCircB() {
        return circB;
    }
}
