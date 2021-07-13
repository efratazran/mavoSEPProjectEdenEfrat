package lights;

import geometries.Plane;
import geometries.Polygon;
import jdk.jfr.Label;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import elements.*;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Format;
import primitives.*;
import renderer.*;
import scene.Scene;


/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 *
 * @author dzilb
 */
public class ReflectionRefractionTests {
    private Scene scene = new Scene("Test scene");

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheres() {
        Camera camera = new Camera.Builder(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setHeight(150).setWidth(150).setDistance(1000).build();

        scene.geometries.add( //
                new Sphere(50, new Point3D(0, 0, -50)) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKT(0.3)),
                new Sphere(25, new Point3D(0, 0, -50)) //
                        .setEmission(new Color(java.awt.Color.RED)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
        scene.lights.add( //
                new SpotLight(new Color(1000, 600, 0), new Point3D(-100, -100, 500), new Vector(-1, -1, -2)) //
                        .setKl(0.0004).setKq(0.0000006));

        Render render = new Render() //
                .setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));
        render.renderImage(0);
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheresOnMirrors() {
        Camera camera = new Camera.Builder(new Point3D(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setWidth(2500).setHeight(2500).setDistance(10000).build(); //

        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

        scene.geometries.add( //
                new Sphere(400, new Point3D(-950, -900, -1000)) //
                        .setEmission(new Color(0, 0, 100)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20).setKT(0.5)),
                new Sphere(200, new Point3D(-950, -900, -1000)) //
                        .setEmission(new Color(100, 20, 20)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                new Triangle(new Point3D(1500, -1500, -1500), new Point3D(-1500, 1500, -1500),
                        new Point3D(670, 670, 3000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setkR(1)),
                new Triangle(new Point3D(1500, -1500, -1500), new Point3D(-1500, 1500, -1500),
                        new Point3D(-1500, -1500, -2000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setkR(0.5)));

        scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point3D(-750, -750, -150), new Vector(-1, -1, -4)) //
                .setKl(0.00001).setKq(0.000005));

        ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));

        render.renderImage(5);
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light with a partially
     * transparent Sphere producing partial shadow
     */
    @Test
    public void trianglesTransparentSphere() {
        Camera camera = new Camera.Builder(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setHeight(200).setWidth(200).setDistance(1000).build();

        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.geometries.add( //
                new Triangle(new Point3D(-150, -150, -115), new Point3D(150, -150, -135), new Point3D(75, 75, -150)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
                new Triangle(new Point3D(-150, -150, -115), new Point3D(-70, 70, -140), new Point3D(75, 75, -150)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
                new Sphere(30, new Point3D(60, 50, -50)) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKT(0.6)));

        scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point3D(60, 50, 0), new Vector(0, 0, -1)) //
                .setKl(4E-5).setKq(2E-7));

        ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));

        render.renderImage(5);
        render.writeToImage();
    }

    /**
     * targil 7 c- image with three bodies
     */
    @Test
    public void Threebodies() {
        Camera camera = new Camera.Builder(new Point3D(50, 100, -10000), new Vector(0, 0, 1), new Vector(0, -1, 0))
                .setDistance(9000).setHeight(3500).setWidth(3500).build();
        scene.setAmbientLight(new AmbientLight(new Color(0, 0, 0), 0.1));

        scene.geometries.add(
                new Sphere(400, new Point3D(-950, 0, 1100))
                        .setEmission(new Color(java.awt.Color.BLUE))
                        .setMaterial(new Material().setKd(0.8).setKs(0.8).setShininess(200).setKT(0.5).setkR(0)),
                new Sphere(500, new Point3D(1000, -150, 1100))
                        .setEmission(new Color(java.awt.Color.MAGENTA))
                        .setMaterial(new Material().setKd(0.8).setKs(0.25).setShininess(120).setKT(0).setkR(0.3)),
                new Sphere(600, new Point3D(0, -700, 1600))
                        .setEmission(new Color(java.awt.Color.ORANGE))
                        .setMaterial(new Material().setKd(0.88).setKs(0.25).setShininess(700).setKT(0).setkR(0.2)),
                new Plane(new Point3D(1500, 1500, 0), new Point3D(-1500, -1500, 3850), new Point3D(-1500, 1500, 0))
                        .setEmission(new Color(java.awt.Color.black))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(20000).setKT(0).setkR(0.4))
        );

        scene.lights.add(new SpotLight(new Color(1000, 400, 400), new Point3D(0, 300, -400)
                , new Vector(-1, 1, 2)).setkC(1).setKl(0.00001).setKq(0.000005));
        scene.lights.add(new DirectionalLight(new Color(java.awt.Color.darkGray), new Vector(-0.5, 0.5, 0)));


        ImageWriter imageWriter = new ImageWriter("Threebodies", 2500, 2500);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));

        render.renderImage(0);
        render.writeToImage();

    }
 @Test
    public void room() {
        Camera camera = new Camera.Builder(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setDistance(1000).setHeight(200).setWidth(200).build();
        scene.setAmbientLight(new AmbientLight(new Color(0, 0, 0), 0.1));

        scene.geometries.add(
                //walls of room
                new Plane(new Point3D(0,0, 0), new Point3D(50,50,0), new Point3D(50,-50,0))
                        .setEmission(new Color(java.awt.Color.BLACK))
                        .setMaterial(new Material().setKd(0.8).setKs(0.25).setShininess(100).setKT(0).setkR(0)),
                new Plane(new Point3D(50,-50, 0), new Point3D(100,-50,0), new Point3D(100,-70,100))
                        .setEmission(new Color(java.awt.Color.darkGray))
                        .setMaterial(new Material().setKd(0).setKs(0.8).setShininess(60).setKT(0).setkR(0)),
                new Plane(new Point3D(50,50, 0), new Point3D(100,50,0), new Point3D(100,70,100))
                        .setEmission(new Color(java.awt.Color.darkGray))
                        .setMaterial(new Material().setKd(0).setKs(0.8).setShininess(60).setKT(0).setkR(0)),
                new Plane(new Point3D(50,-50, 0), new Point3D(50,-100,0), new Point3D(70,-100,100))
                        .setEmission(new Color(java.awt.Color.BLACK))
                        .setMaterial(new Material().setKd(0).setKs(0.8).setShininess(60).setKT(0).setkR(0)),
                new Plane(new Point3D(-50,50, 0), new Point3D(-50,100,0), new Point3D(-70,100,100))
                        .setEmission(new Color(java.awt.Color.BLACK))
                        .setMaterial(new Material().setKd(0).setKs(0.8).setShininess(60).setKT(0).setkR(0)),
                //bodies in room
                new Sphere(20, new Point3D(10, -40, 200))
                        .setEmission(new Color(196,26,26))
                        .setMaterial(new Material().setKd(0.8).setKs(0.8).setShininess(200).setKT(0.5).setkR(0.2)),
                new Sphere(15, new Point3D(-28, -40, 320))
                        .setEmission(new Color(java.awt.Color.MAGENTA))
                        .setMaterial(new Material().setKd(0.8).setKs(0.25).setShininess(120).setKT(0).setkR(0.4)),
                new Sphere(11, new Point3D(-20, -17, 250))
                        .setEmission(new Color(java.awt.Color.YELLOW))
                        .setMaterial(new Material().setKd(0.88).setKs(0.25).setShininess(700).setKT(0).setkR(0.4))
        );

        scene.lights.add(
               new SpotLight(new Color(java.awt.Color.white), new Point3D(-100, -100, 1000), new Vector(-1, -1, -2))
                       .setKl(0.00001).setKq(0.000005));

        scene.lights.add(
              new PointLight(new Color(java.awt.Color.white), new Point3D(0,40,50))
                      .setKl(0.00005).setKq(0.00005));
       scene.lights.add(new DirectionalLight(new Color(java.awt.Color.darkGray), new Vector(-0.5, 0.5, 0)));



        ImageWriter imageWriter = new ImageWriter("room1", 2000, 2000);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));

        render.renderImage(0);
        render.writeToImage();

    }

    /**
     *
     */
    @Test
    public void Room() {
        Camera camera = new Camera.Builder(new Point3D(50, -2200, -20000), new Vector(0, 0, 1), new Vector(0, -1, 0))
                .setDistance(650).setHeight(500).setWidth(500).build();
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLACK), 0.15));

        scene.geometries.add(
                //pin 1
                new Polygon(new Point3D(-3110, -910, -200), new Point3D(-3110, -850, -200), new Point3D(-2850, -850, -200), new Point3D(-2850, -910, -200))
                        .setEmission(new Color(java.awt.Color.red))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(165, new Point3D(-3000, -1150, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(165, new Point3D(-3000, -1100, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(155, new Point3D(-3000, -1050, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(140, new Point3D(-3000, -1000, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(135, new Point3D(-3000, -950, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(130, new Point3D(-3000, -900, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(135, new Point3D(-3000, -850, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(140, new Point3D(-3000, -800, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(150, new Point3D(-3000, -750, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(165, new Point3D(-3000, -700, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(190, new Point3D(-3000, -650, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(210, new Point3D(-3000, -600, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(230, new Point3D(-3000, -550, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(250, new Point3D(-3000, -500, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(270, new Point3D(-3000, -450, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(290, new Point3D(-3000, -400, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(290, new Point3D(-3000, -350, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(280, new Point3D(-3000, -300, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(260, new Point3D(-3000, -250, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(240, new Point3D(-3000, -200, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(220, new Point3D(-3000, -150, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(190, new Point3D(-3000, -100, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(185, new Point3D(-3000, -95, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),

                //pin 2
                new Polygon(new Point3D(-1110, -910, -200), new Point3D(-1110, -850, -200), new Point3D(-850, -850, -200), new Point3D(-850, -910, -200))
                        .setEmission(new Color(java.awt.Color.red))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(165, new Point3D(-1000, -1150, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(165, new Point3D(-1000, -1100, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(155, new Point3D(-1000, -1050, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(150, new Point3D(-1000, -1000, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(135, new Point3D(-1000, -950, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(130, new Point3D(-1000, -900, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(135, new Point3D(-1000, -850, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(140, new Point3D(-1000, -800, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(150, new Point3D(-1000, -750, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(165, new Point3D(-1000, -700, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(190, new Point3D(-1000, -650, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(210, new Point3D(-1000, -600, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(230, new Point3D(-1000, -550, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(250, new Point3D(-1000, -500, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(270, new Point3D(-1000, -450, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(290, new Point3D(-1000, -400, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(290, new Point3D(-1000, -350, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(280, new Point3D(-1000, -300, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(260, new Point3D(-1000, -250, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(240, new Point3D(-1000, -200, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(220, new Point3D(-1000, -150, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(190, new Point3D(-1000, -100, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(185, new Point3D(-1000, -95, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                //pin 3
                new Polygon(new Point3D(-2110, -910, -200), new Point3D(-2110, -850, -200), new Point3D(-1850, -850, -200), new Point3D(-1850, -910, -200))
                        .setEmission(new Color(java.awt.Color.red))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(165, new Point3D(-2000, -1150, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(165, new Point3D(-2000, -1100, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(155, new Point3D(-2000, -1050, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(140, new Point3D(-2000, -1000, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(135, new Point3D(-2000, -950, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(130, new Point3D(-2000, -900, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(135, new Point3D(-2000, -850, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(140, new Point3D(-2000, -800, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(150, new Point3D(-2000, -750, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(165, new Point3D(-2000, -700, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(190, new Point3D(-2000, -650, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(210, new Point3D(-3000, -600, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(230, new Point3D(-2000, -550, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(250, new Point3D(-2000, -500, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(270, new Point3D(-2000, -450, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(290, new Point3D(-2000, -400, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(290, new Point3D(-2000, -350, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(280, new Point3D(-2000, -300, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(260, new Point3D(-2000, -250, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(240, new Point3D(-2000, -200, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(220, new Point3D(-2000, -150, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(190, new Point3D(-2000, -100, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(185, new Point3D(-2000, -95, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),

                //pin 4
                new Polygon(new Point3D(-120, -910, -200), new Point3D(-120, -850, -200), new Point3D(150, -850, -200), new Point3D(150, -910, -200))
                        .setEmission(new Color(java.awt.Color.red))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(165, new Point3D(0, -1150, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(165, new Point3D(0, -1100, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(155, new Point3D(0, -1050, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(140, new Point3D(0, -1000, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(135, new Point3D(0, -950, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(130, new Point3D(0, -900, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(135, new Point3D(0, -850, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(140, new Point3D(0, -800, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(150, new Point3D(0, -750, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(165, new Point3D(0, -700, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(190, new Point3D(0, -650, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(210, new Point3D(0, -600, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(230, new Point3D(0, -550, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(250, new Point3D(0, -500, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(270, new Point3D(0, -450, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(290, new Point3D(0, -400, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(290, new Point3D(0, -350, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(280, new Point3D(0, -300, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(260, new Point3D(0, -250, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(240, new Point3D(0, -200, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(220, new Point3D(0, -150, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(190, new Point3D(0, -100, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(185, new Point3D(0, -95, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                //pin 5
                new Polygon(new Point3D(850, -910, -200), new Point3D(850, -850, -200), new Point3D(1150, -850, -200), new Point3D(1150, -910, -200))
                        .setEmission(new Color(java.awt.Color.red))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(165, new Point3D(1000, -1150, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(165, new Point3D(1000, -1100, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
              /*  new Sphere(155, new Point3D(1000, -1050, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(140, new Point3D(1000, -1000, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),

               */
                new Sphere(135, new Point3D(1000, -950, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(130, new Point3D(1000, -900, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(135, new Point3D(1000, -850, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(140, new Point3D(1000, -800, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(150, new Point3D(1000, -750, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(165, new Point3D(1000, -700, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(190, new Point3D(1000, -650, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(210, new Point3D(1000, -600, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(230, new Point3D(1000, -550, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(250, new Point3D(1000, -500, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
             /*   new Sphere(270, new Point3D(1000, -450, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(290, new Point3D(1000, -400, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),

              */
                new Sphere(290, new Point3D(1000, -350, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(280, new Point3D(1000, -300, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(260, new Point3D(1000, -250, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(240, new Point3D(1000, -200, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
             /*   new Sphere(220, new Point3D(1000, -150, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),
                new Sphere(190, new Point3D(1000, -100, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2)),*/
                new Sphere(185, new Point3D(1000, -95, 0))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material().setKd(0.8).setKs(0.4).setShininess(200).setKT(0).setkR(0.2))


        );

        scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point3D(0, 300, -400)
                , new Vector(-1, 1, 2)).setkC(1).setKl(0.00001).setKq(0.000005));


        ImageWriter imageWriter = new ImageWriter("Room", 500, 500);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));

        render.renderImage(0);
        render.writeToImage();

    }


}