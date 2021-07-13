package renderer;

import geometries.intersectable.GeoPoint;
import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * abstract class ray tracer base
 */
public abstract class RayTracerBase {

    /**
     * field
     */
    protected Scene _scene;

    /**
     * constructor get a scene
     * @param scene
     */
    public RayTracerBase(Scene scene) {
        _scene = scene;
    }

    /**
     * function absract
     * @param ray
     * @param alfa
     * @return
     */
    public abstract Color traceRay(Ray ray ,double alfa);

}