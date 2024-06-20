package icu.kevin.equals.lombokequals;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class LombokEquealsTest {

    /**
     * 排除属性name之后，只比较identity，两个对象equals相等
     */
    @Test
    public void test1() {
        Person person1 = new Person("zhuye", "001");
        Person person2 = new Person("Joseph", "001");
        log.info("person1.equals(person2) ? {}", person1.equals(person2));
    }


    @Test
    public void test2() {
        Employee employee1 = new Employee("zhuye", "001", "bkjk.com");
        Employee employee2 = new Employee("Joseph", "002", "bkjk.com");
        log.info("employee1.equals(employee2) ? {}", employee1.equals(employee2));
    }


}
