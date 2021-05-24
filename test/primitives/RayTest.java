package primitives;


import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import geometries.intersectable.GeoPoint;


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


    @Test
    void testFindGeoClosestPoint() {
        Ray ray=new Ray(new Point3D(0,0,0),new Vector(1,1,1));
        List<GeoPoint> listPoints1=new LinkedList<>();
        GeoPoint gp1=new GeoPoint(new Sphere(2d, new Point3D(2,2,3)),new Point3D(3.39,3.39,3.39));
        GeoPoint gp2=new GeoPoint(new Sphere(2d, new Point3D(2,2,3)),new Point3D(1.3,1.3,1.3));
        GeoPoint gp3=new GeoPoint(new Triangle(new Point3D(2,2,3),new Point3D(3,0,0),new Point3D(0,3,0)),new Point3D(1.8,1.8,1.8));
        listPoints1.add(gp1);
        listPoints1.add(gp2);
        listPoints1.add(gp3);

        // ============ Equivalence Partitions Tests ==============
        //T01:A point
        assertEquals(gp2,ray.findGeoClosestPoint(listPoints1),"A point in the middle of the list is closest the beginning of ray");

        // =============== Boundary Values Tests =================
        //T11: empty list
        listPoints1.clear();
        assertNull(ray.findGeoClosestPoint(listPoints1) , "empty list");

        //T12:first point
        listPoints1.add(gp2);
        listPoints1.add(gp1);
        listPoints1.add(gp3);
        assertEquals(gp2,ray.findGeoClosestPoint(listPoints1),"A point in the first of the list is closest the beginning of ray");

        //T12:last point
        listPoints1.clear();
        listPoints1.add(gp1);
        listPoints1.add(gp3);
        listPoints1.add(gp2);
        assertEquals(gp2,ray.findGeoClosestPoint(listPoints1),"A point in the last of the list is closest the beginning of ray");


    }
}
