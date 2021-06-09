package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * sphere class
 */
public class Sphere extends Geometry {

    /***
     *  field
     **/
    private final Point3D _center;
    private final double _radius;

    /**
     * constructor
     * @param radius double
     * @param center point
     */
    public Sphere(double radius, Point3D center) {
        _center = center;
        _radius = radius;
    }

    /**
     * calculates the normal to a given point
     * @param point point3D value
     * @return vector vector value
     */
    @Override
    public Vector getNormal(Point3D point) {
        Vector p0_p = point.subtract(_center);
        return p0_p.normalize();
    }


    /**
     * finds intersections of the sphere
     * @param ray ray value
     * @return a list of intersection points
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        Point3D p0 = ray.getP0();
        Vector v = ray.getDir();

        if (p0.equals(_center))
            return List.of(new GeoPoint(this,_center.add(v.scale(_radius))));

        Vector u = _center.subtract(p0);
        double tm = alignZero(v.dotProduct(u));
        double d=alignZero(Math.sqrt(u.lengthSquared()-tm*tm));

        //no intersections the ray direction is above the spere
        if(d>=_radius){
            return null;
        }

        double th=alignZero(Math.sqrt(_radius*_radius-d*d));
        double t1=alignZero(tm-th);
        double t2=alignZero(tm+th);

        if(t1>0 && t2>0)//two points
        {
            Point3D p1=p0.add(v.scale(t1));
            Point3D p2=p0.add(v.scale(t2));
            return List.of(new GeoPoint(this, p1),new GeoPoint(this,p2));
        }
        else if(t1>0)
        {
            Point3D p1=p0.add(v.scale(t1));
            return List.of(new GeoPoint(this ,p1));
        }
        else if(t2>0)
        {
            Point3D p1=p0.add(v.scale(t2));
            return List.of(new GeoPoint(this,p1));
        }
        return null;
    }
}
