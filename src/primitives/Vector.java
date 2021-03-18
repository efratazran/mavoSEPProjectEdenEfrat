package primitives;

import static primitives.Point3D.ZERO;
/**
 * this is a vector class
 */
public class Vector {

    /*** field ***/
    private Point3D _head;

    /*** constructors ***/

    /**
     *constructor that get 3 numbers
     * @param x double value
     * @param y double value
     * @param z double value
     */
    public Vector(double x, double y, double z) {
        //this(new Point3D(x, y, z));
        Point3D head=new Point3D(x,y,z);
        if (ZERO.equals(head)) {
            throw new IllegalArgumentException("Vector had cannot be Point(0,0,0)");
        }
        this._head = head;

    }

    /**
     *a constructor that gets 3 coordinates
     * @param x coordinate value
     * @param y coordinate value
     * @param z coordinate value
     */
    public Vector(Coordinate x,Coordinate y,Coordinate z) {
        Point3D head=new Point3D(x,y,z);
        if (ZERO.equals(head)) {
            throw new IllegalArgumentException("Vector had cannot be Point(0,0,0)");
        }
        this._head = head;
    }
    public Point3D get_head() {
        return _head;
    }

     /**
     * constructor that get point and return the vector from (0,0,0) to the point
     * @param head point3D value
     */
    public Vector(Point3D head) {
        if (ZERO.equals(head)) {
            throw new IllegalArgumentException("Vector had cannot be Point(0,0,0)");
        }
        this._head = head;
    }


    /**
     * add between 2 vectors
     * @param vec vector value
     * @return vector vector value
     */
    public Vector add(Vector vec){
        return new Vector(vec._head._x.add(_head._x),
                vec._head._y.add(_head._y),
                vec._head._z.add(_head._z));
    }
    /**
     * subtraction of two vectors
     * @param vec vector value
     * @return vector vector value
     */
    public Vector subtract(Vector vec){
        return new Vector(_head._x.sub(vec._head._x),
                _head._y.sub(vec._head._y),
                _head._z.sub(vec._head._z));
    }

    /**
     * multiplies a scalar with a vector
     * @param scalar double value
     * @return vector vector value
     */
    public Vector scale(double scalar){
        return new Vector(_head._x.get_coord()*scalar,
                _head._y.get_coord()*scalar,
                _head._z.get_coord()*scalar);
    }

    /**
     * multiplies vectors,with a vector result.
     * @param v vector value
     * @return vector vector value
     */
    public Vector crossProduct(Vector v) {
        double u1 = _head._x.get_coord();
        double u2 = _head._y.get_coord();
        double u3 = _head._z.get_coord();

        double v1 = v._head._x.get_coord();
        double v2 = v._head._y.get_coord();
        double v3 = v._head._z.get_coord();

        return new Vector(
                u2 * v3 - u3 * v2,
                u3 * v1 - u1 * v3,
                u1 * v2 - u2 * v1
        );
    }

    /**
     * multiplie vectors, with a number result
     * @param vec vector value
     * @return double value
     */
    public double dotProduct(Vector vec) {
        return vec._head._x.get_coord()*_head._x.get_coord()+
               vec._head._y.get_coord()*_head._y.get_coord()+
               vec._head._z.get_coord()*_head._z.get_coord();

    }

    /**
     * calculates the length of vector in pow 2
     * @return double value
     */
    public double lengthSquared(){
        return (_head._x.get_coord())*(_head._x.get_coord())
                +(_head._y.get_coord())*(_head._y.get_coord())
                +(_head._z.get_coord())*(_head._z.get_coord());
    }

    /**
     * calculates the length of the vector
     * @return double value
     */
    public double length(){
        return Math.sqrt(lengthSquared());
    }


    /**
     * set this vector to be Normalized vector in length 1
     * @return original vector vector value
     */
    public Vector normalize(){
        double len=length();
        if(len==0)
            throw new IllegalArgumentException("divide by zero");
        Vector newvec=scale(1/length());
        _head=newvec._head;
        return this;
    }

    /**
     * normalized vector of this vector
     * @return new vector vector value
     */
    public Vector normalized(){
        Vector vec=new Vector(this._head);
        return vec.normalize();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return _head.equals(vector._head);
    }

    @Override
    public String toString() {
        return "Vector{" +
                "_head=" + _head +
                '}';
    }
}


