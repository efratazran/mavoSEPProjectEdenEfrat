package renderer;

import elements.LightSource;
import primitives.*;
import scene.Scene;
import geometries.intersectable.GeoPoint;
import java.util.List;
import static primitives.Util.alignZero;
/*
class basic tracer ray
 */
public class BasicRayTracer extends RayTracerBase {

    private static final double DELTA = 0.1;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final double INITIAL_K = 1.0;
    private static final double PI= 3.141592654; //value of pai


    /**
     * constructor
     * @param scene
     */
    public BasicRayTracer(Scene scene) {
        super(scene);
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
            if (alignZero(gp_.point.distance(gp.point) - lightDistance) <= 0 &&(gp_.geometry.getMaterial().kT==0))
                return false;
        }
        return true;

    }

    /**
     * calculates the amount of transparency
     * @param light
     * @param l vector direction of light
     * @param n normal to direction of light
     * @param gp point
     * @return double ktr
     */
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
    /*
    function absrract of class ray tracer base
     */
    @Override
    public Color traceRay(Ray ray ,double alfa) {
        List<GeoPoint> intersections = _scene.geometries.findGeoIntersections(ray);
        if (intersections != null) {
            GeoPoint closestgeoPoint = ray.findGeoClosestPoint(intersections);
            return calcColor(closestgeoPoint ,ray, alfa);
        }
        //ray did not intersect any geometrical object
        return _scene.background;
    }

    /**
     * finction recursive that Calculate the color of a ray
     * @param ray Ray to point that we searh color
     * @param level depth of recursive
     * @return Color color value
     */
    private Color calcColor(GeoPoint intersection ,Ray ray, int level, double k,double alfa) {
        Color color= intersection.geometry.getEmmission()
                 .add(calcLocalEffects(intersection, ray, k , alfa));
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray.getDir(), level, k,alfa));

    }
    /**
     * Calculate the color of a ray
     * @param ray Ray to point that we searh color
     * @return Color color value
     */
    private Color calcColor(GeoPoint geopoint, Ray ray,double alfa) {
        return calcColor(geopoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K , alfa)
                .add(_scene.ambientlight.getintensity());
    }

    private Color calcGlobalEffects(GeoPoint gp, Vector v, int level, double k,double alfa) {
        Color color = Color.BLACK;
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        double kkr = k * material.kR;
        if (kkr > MIN_CALC_COLOR_K)
            color = calcGlobalEffect(constructReflectedRay(gp.point, v, n), level, material.kR, kkr,alfa);
        double kkt = k * material.kT;
        if (kkt > MIN_CALC_COLOR_K)
            color = color.add(
                    calcGlobalEffect(constructRefractedRay(gp.point, v, n), level, material.kT, kkt,alfa));
        return color;
    }

    private Color calcGlobalEffect(Ray ray, int level, double kx, double kkx,double alfa) {
        GeoPoint gp = ray.findGeoClosestPoint(_scene.geometries.findGeoIntersections(ray));
        return (gp == null ? Color.BLACK.add(_scene.background).scale(kx) : calcColor(gp, ray, level-1, kkx ,alfa)
        ).scale(kx);
    }


    private Color calcLocalEffects(GeoPoint intersection, Ray ray, double k ,double alfa) {
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
                double ktr = callTransparency(lightSource, l, n, intersection ,alfa);
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


    /**
     * builds a reflected ray
     * @param point point3D value
     * @param v vector value
     * @param n vector value
     * @return ray value
     */
    private Ray constructReflectedRay(Point3D point,Vector v,Vector n) {

        double vn = v.dotProduct(n); // v * n
        double vn2 = vn * 2;
        Vector vec = n.scale(vn2);
        return new Ray(point, v.subtract(vec), n);
    }

    /**
     * builds a refracted ray
     * @param point point3D value
     * @param n vector value
     * @return ray value
     */
    private Ray constructRefractedRay( Point3D point,Vector v,Vector n) {

        return new Ray(point,v, n);
    }


    /**
     * Mini Project 1 - Soft Shadow
     * @param light
     * @param l
     * @param n
     * @param geopoint
     * @param alpha
     * @return
     */
    private double callTransparency(LightSource light, Vector l, Vector n, GeoPoint geopoint, double alpha) {

        Point3D lightP0 = light.getPosition(); // point of the light
        //if this direction light or not want to use in Improvement soft shadow
        if (lightP0 == null || alpha == 0)
            return transparency(light, l, n, geopoint);

        //calculate radius of the circle around the light
        double distance = lightP0.distance(geopoint.point); // distance between light and interction point
        double radius = distance * Math.tan(Math.toRadians(alpha));  // calculate radius around the light
        double sumKtr = 0; // sum of all ktr from random point around the light

        //find points on the circle at the same plane.
        //vectorim in the plane
        Vector v = new Vector(-l.get_head().gety(), l.get_head().getx(), 0).normalized();
        Vector w = l.crossProduct(v);

        // 81 ray from geopoint to circle around the light to calculate and sum the ktr
        for(int i=0; i<81;i++) { // find 81 point around the light
            //random number between (0,1)
            double t = 2 * PI * Math.random();
            double r = radius * Math.random();
            //paremetrim randomalim
            double a = r * Math.cos(t);
            double b = r * Math.sin(t);

            Point3D randomPoint = lightP0.add(v.scale(a).add(w.scale(b))); //random point around light
            Vector randomL = geopoint.point.subtract(randomPoint).normalized(); // random vector from geopont to random point around light
            sumKtr += transparency(light, randomL, n, geopoint); // sum of all ktr from random point around the light
        }
        return sumKtr/81; // average of ktr
    }


}