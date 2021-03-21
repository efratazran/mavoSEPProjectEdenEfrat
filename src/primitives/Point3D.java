package primitives;
import primitives.Coordinate.*;
/**
 * this is the class of a 3d point
 */
public class Point3D {

    /*** field ***/
    final Coordinate _x;
    final Coordinate _y;
    final Coordinate _z;
    public final static Point3D ZERO = new Point3D(0, 0, 0);


    /**
     * constructor that get 3 Coordinate
     * this is the ctor that gets three different coordinates.
     *
     * @param x Coordinate value
     * @param y Coordinate value
     * @param z Coordinate value
     */
    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        _x = x;
        _y = y;
        _z = z;
    }

    /**
     * constructor that get 3 numbers
     * this is a ctor that gets 3 different doubles.
     *
     * @param x double value
     * @param y double value
     * @param z double value
     */
    public Point3D(double x, double y, double z) {
        //this(new Coordinate(x), new Coordinate(y), new Coordinate(z));
        _x = new Coordinate(x);
        _y = new Coordinate(y);
        _z = new Coordinate(z);
    }

//    /*** getters ***/
//    public double getX() {
//        return _x._coord;
//    }
//
//    public double getY() {
//        return _y._coord;
//    }
//
//    public double getZ() {
//        return _z._coord;
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return _x.equals(point3D._x) && _y.equals(point3D._y) && _z.equals(point3D._z);
    }

    @Override
    public String toString() {
        return "(" + _x + "," + _y + "," + _z + ")";
    }


    /**
     * subtraction of two points.
     *
     * @param p point3D value
     * @return vector vector value
     */
    public Vector subtract(Point3D p) {
        Point3D head = new Point3D(
                _x.sub(p._x),
                _y.sub(p._y),
                _z.sub(p._z)
        );
        if (ZERO.equals(head)) {
            throw new IllegalArgumentException("Vector had cannot be Point(0,0,0)");
        }
        return new Vector(head);
    }

    /**
     * addition of two points
     *
     * @param vec vector value
     * @return Point point3D value,return point of add of vector and point
     */
    public Point3D add(Vector vec) {
        Point3D point = new Point3D(_x.add(vec.get_head()._x), _y.add(vec.get_head()._y), _z.add(vec.get_head()._z));
        return point;
    }

    /**
     * istance between 2 points in pow 2
     *
     * @param point point3D value
     * @return distance in double value
     */
    public double distanceSquared(Point3D point) {
        return ((point._x._coord - _x._coord) * (point._x._coord - _x._coord)
                + (point._y._coord - _y._coord) * (point._y._coord - _y._coord) +
                (point._z._coord - _z._coord) * (point._z._coord - _z._coord));

    }

    /**
     * calculates distance between two points
     *
     * @param point point3D value
     * @return distance in double value
     */
    public double distance(Point3D point) {
        return Math.sqrt(distanceSquared(point));
    }


}
