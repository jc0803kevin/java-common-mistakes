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
