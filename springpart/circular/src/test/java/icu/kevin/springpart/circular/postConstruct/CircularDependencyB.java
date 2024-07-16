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