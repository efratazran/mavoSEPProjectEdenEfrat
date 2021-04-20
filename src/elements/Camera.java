package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

public class Camera {

    /*** field ***/
    private final Point3D _p0;
    private final Vector _vTo;
    private final Vector _vUp;
    private final Vector _vRight;

    //view plane parameters
    private double _height;
    private double _width;
    private double _distance;

    //constructor
    private Camera(Builder builder) {
        _p0=builder._p0;
        _vTo=builder._vTo;
        _vUp=builder._vUp;
        _vRight=builder._vRight;
        _height=builder._height;
        _width=builder._width;
        _distance=builder._distance;
    }
   /** setters **/
    public Camera setViewPlaneSize(double width, double height){
        _width = width;
        _height =height;
        return this;
    }

    public Camera setDistance(double distance) {
        _distance = distance;
        return this;
    }

    public Ray constructRayThroughPixel(int nX, int nY, int j, int i){
        // the point of view plane
        Point3D Pc=_p0.add(_vTo.scale(_distance));

        double Rx=_width/nX;
        double Ry=_height/nY;

        Point3D pIJ=Pc;

        double xj=(j-(nX-1)/2d)*Rx;
        double yi=-(i-(nY-1)/2d)*Ry;
        // to move to the true point (pixel)
        if(!isZero(xj)) {
            pIJ=pIJ.add(_vRight.scale(xj));
        }
        if(!isZero(yi)) {
            pIJ=pIJ.add(_vUp.scale(yi));
        }
        //create the ray from the camera to true pixel
        Vector Vij=pIJ.subtract(_p0);
        return new Ray(_p0,Vij);
    }
    /** getters **/
    public Point3D getP0() {
        return _p0;
    }

    public Vector getvTo() {
        return _vTo;
    }

    public Vector getvUp() {
        return _vUp;
    }

    public Vector getvRight() {
        return _vRight;
    }

    public static class Builder{
        /**field**/
        final Point3D _p0;
        final Vector _vTo;
        final Vector _vUp;
        final Vector _vRight;

        private double _height=1;
        private double _width=1;
        private double _distance=1;

        public Builder(Point3D p0, Vector vTo, Vector vUp) {
            _p0 = p0;

            if (!isZero(vTo.dotProduct(vUp))){
                throw new IllegalArgumentException("vTo and Vup are not orthogonal");
            }
            _vTo = vTo.normalized();
            _vUp = vUp.normalized();

            _vRight=_vTo.crossProduct(_vUp);
        }

        public Builder setHeight(double height) {
            _height = height;
            return this;
        }

        public Builder setWidth(double width) {
            _width = width;
            return this;
        }

        public Builder setDistance(double distance) {
            _distance = distance;
            return this;
        }

        public Camera build(){
            Camera camera =new Camera(this);
            return camera;
        }
    }
}
