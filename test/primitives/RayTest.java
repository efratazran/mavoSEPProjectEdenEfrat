package primitives;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    @Test
    void testFindClosestPoint() {
        // ============ Equivalence Partitions Tests ==============
        //t1= the point that closest in middle in list
        Ray ray = new Ray(new Point3D(0, 0, 10), new Vector(1, 10, -100));

        List<Point3D> list = new LinkedList<Point3D>();
        list.add(new Point3D(1, 1, -100));
        list.add(new Point3D(-1, 1, -99));
        list.add(new Point3D(0, 2, -10));
        list.add(new Point3D(0.5, 0, -100));

        assertEquals(list.get(2), ray.findClosestPoint(list));

    }

    @Test
    void testFindClosestPoint2() {
        // =============== Boundary Values Tests =================
        //t1-empty list of points should be return null
        Ray ray = new Ray(new Point3D(0, 0, 10), new Vector(1, 10, -100));
        List<Point3D> list = null;
        assertNull(ray.findClosestPoint(list), "try again");

        List<Point3D> list2 = new LinkedList<Point3D>();
        list2.add(new Point3D(0, 2, -10));
        list2.add(new Point3D(1, 1, -100));
        list2.add(new Point3D(-1, 1, -99));
        list2.add(new Point3D(0.5, 0, -100));

        //t2- the point that closest in start of list
        assertEquals(list2.get(0), ray.findClosestPoint(list2));

        List<Point3D> list3 = new LinkedList<Point3D>();
        list3.add(new Point3D(1, 1, -100));
        list3.add(new Point3D(-1, 1, -99));
        list3.add(new Point3D(0.5, 0, -100));
        list3.add(new Point3D(0, 2, -10));

        //t2- the point that closest in end of list
        assertEquals(list3.get(3), ray.findClosestPoint(list3));

    }

}
