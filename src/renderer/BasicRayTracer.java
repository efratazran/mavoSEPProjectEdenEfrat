package renderer;

import elements.LightSource;
import primitives.*;
import scene.Scene;
import geometries.intersectable.GeoPoint;

import java.util.List;

import static primitives.Util.alignZero;

public class BasicRayTracer extends RayTracerBase {

    private static final double DELTA = 0.1;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final double INITIAL_K = 1.0;



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
            if (alignZero(gp_.point.distance(gp.point) - lightDistance) <= 0 &&(gp_.geometry.getMaterial().kT==0))
                return false;
        }
        return true;

    }

    private double transparency(LightSource light, Vector l, Vector n, GeoPoint gp)
    {
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);
        Point3D point = gp.point.add(delta);
        Ray lightRay = new Ray(point, lightDirection);
        List<GeoPoint> intersections = (_scene.geometries.findGeoIntersections(lightRay));

        if (intersections == null) return 1.0;

        double ktr=1.0;
        double lightDistance = light.getDistance(gp.point);
        for (GeoPoint gp_ : intersections) {
            if (alignZero(gp_.point.distance(gp.point) - lightDistance) <= 0 ){
                ktr*= gp_.geometry.getMaterial().kT;
                if (ktr<MIN_CALC_COLOR_K) return 0.0;
            }
        }
        return ktr;
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

    private Color calcColor(GeoPoint intersection ,Ray ray, int level, double k) {
        Color color= intersection.geometry.getEmmission()
                 .add(calcLocalEffects(intersection, ray, k));
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray.getDir(), level, k));

    }

    private Color calcColor(GeoPoint geopoint, Ray ray) {
        return calcColor(geopoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(_scene.ambientlight.getintensity());
    }

    private Color calcGlobalEffects(GeoPoint gp, Vector v, int level, double k) {
        Color color = Color.BLACK;
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        double kkr = k * material.kR;
        if (kkr > MIN_CALC_COLOR_K)
            color = calcGlobalEffect(constructReflectedRay(gp.point, v, n), level, material.kR, kkr);
        double kkt = k * material.kT;
        if (kkt > MIN_CALC_COLOR_K)
            color = color.add(
                    calcGlobalEffect(constructRefractedRay(gp.point, v, n), level, material.kT, kkt));
        return color;
    }
    private Color calcGlobalEffect(Ray ray, int level, double kx, double kkx) {
        GeoPoint gp = ray.findGeoClosestPoint(_scene.geometries.findGeoIntersections(ray));
        return (gp == null ? Color.BLACK.add(_scene.background).scale(kx) : calcColor(gp, ray, level-1, kkx)
        ).scale(kx);
    }


    private Color calcLocalEffects(GeoPoint intersection, Ray ray, double k) {
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
                double ktr = transparency(lightSource, l, n, intersection);
                if (ktr * k > MIN_CALC_COLOR_K) {
                    Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
                    color = color.add(calcDiffusive(material.kD, l, n, lightIntensity),
                            calcSpecular(material.kS, l, n, v, nShininess, lightIntensity));



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



    private Ray constructReflectedRay(Point3D point,Vector v,Vector n) {
        // Vector delta = n.scale(DELTA);
        // Point3D new_point = point.add(delta);
        double vn = v.dotProduct(n); // v * n
        double vn2 = vn * 2;
        Vector vec = n.scale(vn2);
        return new Ray(point, v.subtract(vec), n);
        // return new Ray (new_point,vec.subtract(inRay.getDir()));
    }

    private Ray constructRefractedRay( Point3D point,Vector v,Vector n) {
        // Vector delta = n.scale(- DELTA);
        // Point3D new_point = point.add(delta);

        return new Ray(point,v, n);
    }


}