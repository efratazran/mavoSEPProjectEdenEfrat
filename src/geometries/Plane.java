package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * this is a plane class
 */
public class Plane implements Geometry {

    /*** field ***/
    private final Point3D _q0;
    private final Vector _normal;

    /*** constructors ***/
    /**
     * constructor that get 3 points of plane
     *
     * @param p1 point3D value
     * @param p2 point3D value
     * @param p3 point3D value
     */
    public Plane(Point3D p1, Point3D p2, Point3D p3) {
        _q0 = p1;

        Vector v1=p2.subtract(p1);
        Vector v2=p3.subtract(p1);

        Vector n=v1.crossProduct(v2);

        _normal = n.normalize();
    }

    /**
     * constructor that get point and normal
     *
     * @param q0     point3D value
     * @param normal Vector value,vector for the normal(will bwe normalized outomatical)
     */
    public Plane(Point3D q0, Vector normal) {
        this._q0 = q0;
        this._normal = normal.normalized();
    }

    /**
     * getter of the normal vector of the Plane
     * getNormal implaematation
     * @return
     * @deprecated use the oveeriden version from Geometry
     * {@link Plane#getNormal(Point3D)} with null parameter value
     */
    public Vector getNormal() {
        return _normal;
    }

    /**
     * gets the normal of the plane
     * getter of _Normal field
     * @param point point3D value
     * @return vector vector value
     */
    @Override
    public Vector getNormal(Point3D point) {
        return null;
    }

    /**
     * getter Q0 field
     * @return reference to the q0 point of the plane
     */
    public Point3D getQ0() {
        return _q0;
    }


    @Override
    public List<Point3D> findIntersections(Ray ray) {
       Point3D p0=ray.getP0();
       Vector v=ray.getDir();

       if(_q0.equals(p0))return null;

       Vector p0_q0=_q0.subtract(p0);
       Vector n=_normal;

       double mechane=alignZero(n.dotProduct(p0_q0));;

       if(isZero(mechane))
           return null;
       //mone
       double nv= alignZero(n.dotProduct(v));
       if(isZero(nv))
           return null;
       double t =alignZero(mechane/nv);
       Point3D P=p0.add(v.scale(t));

       return List.of(P);
    }
}
