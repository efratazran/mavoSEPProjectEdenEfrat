package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public interface LightSource {

    /**
     *
     * @param p
     * @return
     */
    public Color getIntensity(Point3D p);
    public Vector getL(Point3D p);

}
