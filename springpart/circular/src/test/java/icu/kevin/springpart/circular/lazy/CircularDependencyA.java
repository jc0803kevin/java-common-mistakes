package icu.kevin.springpart.circular.lazy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class CircularDependencyA {

    private CircularDependencyB circB;

    @Autowired
    public CircularDependencyA(/*@Lazy*/ CircularDependencyB circB) {
        this.circB = circB;
    }
}
