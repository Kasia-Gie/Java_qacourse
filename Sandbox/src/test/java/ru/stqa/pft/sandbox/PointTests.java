package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;


//public class SquareTests {
//
//    @Test
//    public void testArea() {
//
//        Square s = new Square(5);
//        Assert.assertEquals(s.area(), 25.0);
//    }
//}
public class PointTests {

    @Test
    public void testDistance() {

        Point p1 = new Point(3, 2);
        Point p2 = new Point(7, 5);

        double distance = p1.distance(p2);

        Assert.assertEquals(distance, 5.0);
    }

    @Test
    public void testStaticMethodDistance() {

        Point p1 = new Point(3, 2);
        Point p2 = new Point(7, 5);

        double distanceBetweenP1AndP2 = MyFirstProgram.distance(p1, p2);

        Assert.assertEquals(distanceBetweenP1AndP2, 5.0);
    }

}
