package icu.kevin.equals.equalitymethod;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Slf4j
public class EqualityMethodTest {

    @Test
    public void newArrayList(){

        int[] arr = {1, 2, 3};
        List list = Arrays.asList(arr);
        System.out.println(list + " " + list.size() + " " + list.get(0).getClass());

        // jdk8的条件下执行如下
        // [[I@4dfa3a9d] 1 class [I

//        不同的jdk版本，运气结果可能有差异
    }

@Test
    public void wrong() {
        Point p1 = new Point(1, 2, "a");
        Point p2 = new Point(1, 2, "b");
        Point p3 = new Point(1, 2, "a");
        log.info("p1.equals(p2) ? {}", p1.equals(p2));
        log.info("p1.equals(p3) ? {}", p1.equals(p3));

    }


    @Test
    public void wrong2() {
        PointWrong p1 = new PointWrong(1, 2, "a");
        try {
            log.info("p1.equals(null) ? {}", p1.equals(null));
        } catch (Exception ex) {
            //java.lang.NullPointerException
            log.error(ex.toString());
        }

        Object o = new Object();
        try {
            log.info("p1.equals(expression) ? {}", p1.equals(o));
        } catch (Exception ex) {
            //java.lang.ClassCastException: java.lang.Object cannot be cast to
            log.error(ex.toString());
        }

        PointWrong p2 = new PointWrong(1, 2, "b");
        log.info("p1.equals(p2) ? {}", p1.equals(p2));

        HashSet<PointWrong> points = new HashSet<>();
        points.add(p1);
        // 没有重写hashcode
        log.info("points.contains(p2) ? {}", points.contains(p2));
    }


    @Test
    public void right() {
        PointRight p1 = new PointRight(1, 2, "a");
        try {
            log.info("p1.equals(null) ? {}", p1.equals(null));
        } catch (Exception ex) {
            log.error(ex.toString());
        }

        Object o = new Object();
        try {
            log.info("p1.equals(expression) ? {}", p1.equals(o));
        } catch (Exception ex) {
            log.error(ex.toString());
        }

        PointRight p2 = new PointRight(1, 2, "b");
        log.info("p1.equals(p2) ? {}", p1.equals(p2));

        HashSet<PointRight> points = new HashSet<>();
        points.add(p1);
        // 因为重写了hashcode
        log.info("points.contains(p2) ? {}", points.contains(p2));
    }
}
