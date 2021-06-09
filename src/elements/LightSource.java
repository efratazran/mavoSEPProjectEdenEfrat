package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * light source interface
 */
public interface LightSource {

    /**
     * getting the lights source intensity
     * @param p the lighted point
     * @return intensity color
     */
    Color getIntensity(Point3D p);

    /**
     *  this is where we get a normalized vector,in the direction coming from the light source,and tordes the point thats lighted.
     * @param p the lighted point
     * @return light to point vector
     */
    Vector getL(Point3D p);


    /**
     * returns the distance between the given point to the light source
     * @param point point3D value
     * @return double value
     */
    double getDistance(Point3D point);

}
