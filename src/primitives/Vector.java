package primitives;
import static primitives.Point3D.ZERO;

public class Vector {
    Point3D _head;

    public Point3D get_head() {
        return _head;
    }

    //constructors
    public Vector(Point3D head) {
        if (ZERO.equals(head)) {
            throw new IllegalArgumentException("Vector had cannot be Point(0,0,0)");
        }
        this._head = _head;
    }

    public Vector(double x, double y, double z) {
        this(new Point3D(x, y, z));
        /* Point3D head=new Point3D(x,y,z);
        if (ZERO.equals(head)) {
            throw new IllegalArgumentException("Vector had cannot be Point(0,0,0)");
        }
        this._head = _head;
        */
    }

    public Vector(Coordinate x,Coordinate y,Coordinate z) {
        this(new Point3D(x, y, z));
    }

    public Vector add(Vector vec){
        return new Vector(vec._head._x.add(_head._x),
                vec._head._y.add(_head._y),
                vec._head._z.add(_head._z));
    }
    public Vector subtract(Vector vec){
        return new Vector(_head._x.sub(vec._head._x),
                _head._y.sub(vec._head._y),
                _head._z.sub(vec._head._z));
    }
    public Vector scale(double scalar){
        return new Vector(_head._x.get_coord()*scalar,
                _head._y.get_coord()*scalar,
                _head._z.get_coord()*scalar);
    }
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

    public double dotProduct(Vector vec) {
        return vec._head._x.get_coord()*_head._x.get_coord()+
               vec._head._y.get_coord()*_head._y.get_coord()+
               vec._head._z.get_coord()*_head._z.get_coord();

    }

    public double lengthSquared(){
        return (_head._x.get_coord())*(_head._x.get_coord())
                +(_head._y.get_coord())*(_head._y.get_coord())
                +(_head._z.get_coord())*(_head._z.get_coord());
    }
    public double length(){
        return Math.sqrt(lengthSquared());
    }
    public Vector normalize(){
        double len=length();
        if(len==0)
            throw new IllegalArgumentException("divide by zero");
        Vector newvec=scale(1/length());
        _head=newvec._head;
        return this;
    }

    public Vector normalized(){
        Vector vec=new Vector(this._head);
        return vec.normalize();
    }



}


