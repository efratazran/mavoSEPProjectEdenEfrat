package primitives;

import java.util.List;

import geometries.intersectable.GeoPoint;
import static primitives.Util.isZero;

/**
 * a ray class with a starting point
 */
public class Ray {

     /***
     *  field
     *  ***/
    private final Point3D p0;
    private final Vector dir;
    private static final double DELTA = 0.1;

        /**
     * constructor that gets a point and a vector
     * @param p0  point3D value
     * @param dir vector value
     */
    public Ray(Point3D p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalized();
    }

    /**
     * constructor that get 3 paramters: point,direction,normal
     * @param p0 point
     * @param dir direction vector
     * @param n normal vector
     */
    public Ray(Point3D p0, Vector dir, Vector n) {

        this.p0=p0.add(n.scale(n.dotProduct(dir)>0 ? DELTA:-DELTA));
        this.dir = dir.normalized();
    }


    /**
     * find the closest Point to Ray origin
     * @param pointsList intersections point List
     * @return closest point
     */
    public Point3D findClosestPoint(List<Point3D> pointsList){
        Point3D result =null;
        double closestDistance = Double.MAX_VALUE;

        if(pointsList== null){
            return null;
        }

        for (Point3D p: pointsList) {
            double temp = p.distance(p0);
            if(temp < closestDistance){
                closestDistance =temp;
                result =p;
            }
        }

        return  result;
    }

    /**
     *find the closest Point to Ray origin
     * @param pointsList intersections point List
     * @return  closest point
     */
    public GeoPoint findGeoClosestPoint(List<GeoPoint> pointsList) {
        if (pointsList==null) {
            return null;
        }

        GeoPoint max = pointsList.get(0);
        for (GeoPoint geopoint : pointsList) {
            if (geopoint.point.distance(p0) < max.point.distance(p0)) {
                max = geopoint;
            }
        }
        return max;
    }


    /**
     * getter for origin of the Ray
     * @return p0
     */
    public Point3D getP0() {
        return p0;
    }

    /**
     * getter for direction vector of the Ray
     * @return dir
     */
    public Vector getDir() {
        return new Vector(dir.get_head());
    }

    /**
     * returns the intersections point
     * @param delta double value
     * @return Point point3D value
     */
    public Point3D getPoint(double delta ){
        if (isZero(delta)){
            return  p0;
        }
        return p0.add(dir.scale(delta));
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return p0.equals(ray.p0) && dir.equals(ray.dir);
    }

    @Override
    public String toString() {
        return "p0: " + p0.toString() + ", dir=" + dir.toString();
    }
}
