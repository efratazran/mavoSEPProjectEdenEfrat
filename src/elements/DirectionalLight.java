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
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        _direction = direction.normalized();
    }

    /**
     * @param point
     *           THIS IS THE LIGHTED POINT, we do not use it,we only mention it
     * @return the new intensity of the directionLight
     */
    @Override
    public Color getIntensity(Point3D point) {
        return this.getintensity();
    }

    //this is the way to get the direction vector()
    @Override
    public Vector getL(Point3D p) {
        return _direction;
    }




}
