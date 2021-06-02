package renderer;

import elements.LightSource;
import primitives.*;
import scene.Scene;
import geometries.intersectable.GeoPoint;

import java.util.List;

import static primitives.Util.alignZero;

public class BasicRayTracer extends RayTracerBase {

    private static final double DELTA = 0.1;


    private boolean unshaded(Vector l, Vector n, GeoPoint gp) {

        return true;
    }

    /**
     * checks if a geometry is shaded
     * @param light lightSource value
     * @param l vector value
     * @param n vector value
     * @param gp geoPoint value
     * @return boolean value
     */
    private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint gp)
    {
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);
        Point3D point = gp.point.add(delta);
        Ray lightRay = new Ray(point, lightDirection);
        List<GeoPoint> intersections = (_scene.geometries.findGeoIntersections(lightRay));

        if (intersections == null) return true;
        double lightDistance = light.getDistance(gp.point);
        for (GeoPoint gp_ : intersections) {
            if (alignZero(gp_.point.distance(gp.point) - lightDistance) <= 0)
                return false;
        }
        return true;

    }

    public BasicRayTracer(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersections = _scene.geometries.findGeoIntersections(ray);
        if (intersections != null) {
            GeoPoint closestgeoPoint = ray.findGeoClosestPoint(intersections);
            return calcColor(closestgeoPoint ,ray);
        }
        //ray did not intersect any geometrical object
        return _scene.background;
    }

    private Color calcColor(GeoPoint geopoint ,Ray ray) {
        return _scene.ambientlight.getintensity()
                .add(geopoint.geometry.getEmmission())
                 .add(calcLocalEffects(geopoint, ray));

    }

    private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
        Vector v = ray.getDir();
        Vector n = intersection.geometry.getNormal(intersection.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0){
            return _scene.background;
        }
        Material material = intersection.geometry.getMaterial();
        int nShininess = material.Shininess;
        double kd = material.kD, ks = material.kS;
        Color color = Color.BLACK;
        for (LightSource lightSource : _scene.lights) {
            Vector l = lightSource.getL(intersection.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                if (unshaded(lightSource, l, n, intersection)) {
                    Color lightIntensity = lightSource.getIntensity(intersection.point);
                    color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                            calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                }
            }
        }
        return color;
    }


    /* Calculates specular light
     * @param ks
     * @param l
     * @param n
     * @param v
     * @param nShininess
     * @param lightIntensity
     * @return */
    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity)
    {
        Vector r = l.subtract(n.scale(l.dotProduct(n) * 2));
        double u = v.scale(-1).dotProduct(r);
        double o = Math.pow(Math.max(u, 0), nShininess) * ks;
        return lightIntensity.scale(o);
    }
    /**
     * Calculates diffuse light
     * @param kd
     * @param l
     * @param n
     * @param lightIntensity
     * @return
     */
    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity)
    {
        double t = Math.abs(alignZero(n.dotProduct(l)))* kd;
        return lightIntensity.scale(t);
    }

}