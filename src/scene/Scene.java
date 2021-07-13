package scene;

import elements.AmbientLight;
import elements.LightSource;
import geometries.Geometries;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;
/*
class acene
 */
public class Scene {

    /** field **/
    private final String _name;

    //according to Dan Zilberstein directives
    public Color background = Color.BLACK;
    public AmbientLight ambientlight= new AmbientLight();
    public Geometries geometries = null;
    public List<LightSource> lights=new LinkedList<LightSource>();

    /**
     * constructor
     **/
    public Scene(String name) {
        _name = name;
        geometries= new Geometries();
    }

    //set builder pattern
    public Scene setBackground(Color background) {
        this.background = background;
        return  this;
    }

    public Scene setAmbientLight(AmbientLight ambientlight) {
        this.ambientlight = ambientlight;
        return this;
    }

    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return  this;
    }

    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }
}