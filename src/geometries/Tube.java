package geometries;

import primitives.Point3D;
import primitives.Vector;
import primitives.Ray;

import java.io.PipedOutputStream;
import java.util.List;

import static primitives.Util.isZero;

/**
 * this is a tube class
 */
public class Tube extends Geometry {

    /***
     * field
     * ***/
    double _radius;
    Ray _axisRay;

    /**
     * constructor that gets a radius and ray
     * @param _radius double value
     * @param _axisRay ray value
     */
    public Tube(double _radius, Ray _axisRay) {
        this._radius = _radius;
        this._axisRay = _axisRay;
    }

    /********** getter **********/

    public double getRadius() {
        return _radius;
    }

    public Ray getAxisRay() {
        return _axisRay;
    }

    /***
     * calculates the normal to a given point
     * @param p - Point in tube
     * @return vector normal to the point
     */
    @Override
    public Vector getNormal(Point3D p) {
       Point3D P0= _axisRay.getP0();
       Vector v= _axisRay.getDir();
       Vector P0_P= p.subtract(P0);

       double t=v.dotProduct(P0_P);

        //if vector v and vector p0_p is vertical
        if(isZero(t)){
            return  P0_P.normalize();
        }

        Point3D O=P0.add(v.scale(t));
        //if p is point of the ray
        if (O.equals(p)){
            throw new IllegalArgumentException("point a cannot be on the tube's axis");
        }
        Vector n=p.subtract(O);
        return  n.normalize();

    }

    @Override
    public String toString() {
        return "Tube{" +
                "radius=" + _radius +
                ", axisRay=" + _axisRay +
                '}';
    }

    /**
     * finds the tubes intersections
     * @param ray ray value
     * @return a list of intersection points
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        return null;
    }
}
