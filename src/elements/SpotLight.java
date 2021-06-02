package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class SpotLight extends PointLight {

    /**
     * field
     */
    private final Vector _direction;


    /**
     * constructor spotlight
     *
     * @param intensity get a color, kC,kL,kQ ,Vector direction
     */
    public SpotLight(Color intensity, Point3D position, Vector direction) {
        super(intensity, position);
        _direction = direction.normalized();
    }

    /**
     * returns the intensity in a given point
     *
     * @param p the lighted point
     * @return color value in the given point
     */
    @Override
    public Color getIntensity(Point3D p) {
        Color pointLightIntensity = super.getIntensity(p);
        double projection = _direction.dotProduct(getL(p));
        Color IL = pointLightIntensity.scale(Math.max(0, projection));
        return IL;

    }

    @Override
    public double getDistance(Point3D point) {
        return super.getDistance(point);
    }
}


