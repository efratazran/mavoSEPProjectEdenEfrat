package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * class point light
 */
public class PointLight extends Light implements LightSource {

    /**
     * field
     */
    protected final Point3D _position;
    private double kC = 1;
    private double kL = 0;
    private double kQ = 0;


    /**
     * constructor that gets a color, point3D
     * @param intensity color
     * @param position point
     */
    public PointLight(Color intensity, Point3D position) {
        super(intensity);
        _position = position;
    }


    /**
     * setters in format builder
     */
    public PointLight setkC(double kC) {
        this.kC = kC;
        return this;
    }

    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }

    /**
     * getters
     **/
    public Point3D getPosition() {
        return _position;
    }

    /**
     * returns the intensity in a given point
     * @param lightedP the lighted point
     * @return color value in the given point
     */
    @Override
    public Color getIntensity(Point3D lightedP) {
        double d = lightedP.distance(_position);
        Color IL = getintensity().scale(1d/(kC + d * kL + d * d * kQ));
        return IL;
    }

    /**
     * return the vector direction to a point
     * @param lightedP the lighted point
     * @return vector value
     */
    @Override
    public Vector getL(Point3D lightedP) {
        if (lightedP.equals(_position)) {
            return null;
        } else {
            return lightedP.subtract(_position).normalize();
        }
    }

    /**
     * returns the distance between the given point to the light source
     * @param point point3D value
     * @return double value distance light
     */
    @Override
    public double getDistance(Point3D point) {
        return _position.distance(point);
    }

}
