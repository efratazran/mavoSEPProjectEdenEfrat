package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource{

    /**
     * field
     */
    private final Vector _direction;

    /**
     * constructor
     * @param intensity get a color
     * @param direction vector
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        _direction = direction.normalized();
    }

    /**
     * @param point
     * @return the new intensity of the directionLight
     */
    @Override
    public Color getIntensity(Point3D point) {

        return this.getintensity();
    }

    /**
     * this is the way to get the direction vector
     */
    @Override
    public Vector getL(Point3D p) {
        return _direction;
    }

    /**
     * returns the distance between the given point to the light source
     * @param point
     * @return double value distance light
     */
    @Override
    public double getDistance(Point3D point) {
        return Double.POSITIVE_INFINITY;
    }


}
