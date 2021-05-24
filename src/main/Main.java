package main;
/*Targil 1
Efrat Azran 206848640
Eden Lasri 315485987
*/

import geometries.Sphere;
import geometries.Triangle;
import geometries.intersectable;
import primitives.*;

import java.util.LinkedList;
import java.util.List;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Test program for the 1st stage
 *
 * @author Dan Zilberstein
 */
public final class Main {

    /**
     * Main program to tests initial functionality of the 1st stage
     *
     * @param args irrelevant here
     */
    public static void main(String[] args) {

        Ray ray=new Ray(new Point3D(0,0,0),new Vector(1,1,1));
        List<intersectable.GeoPoint> listPoints1=new LinkedList<>();
        intersectable.GeoPoint gp1=new intersectable.GeoPoint(new Sphere(2d, new Point3D(2,2,3)),new Point3D(3.39,3.39,3.39));
        intersectable.GeoPoint gp2=new intersectable.GeoPoint(new Sphere(2d, new Point3D(2,2,3)),new Point3D(1.3,1.3,1.3));
        intersectable.GeoPoint gp3=new intersectable.GeoPoint(new Triangle(new Point3D(2,2,3),new Point3D(3,0,0),new Point3D(0,3,0)),new Point3D(1.0,1.0,1.0));
        listPoints1.add(gp1);
        listPoints1.add(gp2);
        listPoints1.add(gp3);

        // ============ Equivalence Partitions Tests ==============
        //T01:A point
        intersectable.GeoPoint gp4=ray.findGeoClosestPoint(listPoints1);
        out.println(gp4.point);
        //assertEquals(gp2,ray.findGeoClosestPoint(listPoints1),"A point in the middle of the list is closest the beginning of ray");






    }
}
