package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 *  class triangle.
 */
public class Triangle extends Polygon {


    /**
     * ctor that gets 3 points of triangle and goes to implement his father
     *
     * @param p1 point3D value
     * @param p2 point3D value
     * @param p3 point3D value
     */
    public Triangle(Point3D p1, Point3D p2, Point3D p3) {
        super(p1, p2, p3);
    }

    /**
     * get normal of a triangle
     * @param point point3D value
     * @return vector vector value
     */
    @Override
    public Vector getNormal(Point3D point) {
        return super.getNormal(point);
    }

    /**
     * finds intersections of the triangle
     * @param ray ray value
     * @return a list of intersection points
     */
     @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        return super.findGeoIntersections(ray);
    }

    @Override
    public String toString() {
        return "points of Triangle:" + vertices + '\n' + "Plane" + plane;
    }
}

