package elements;

import primitives.Color;

/**
 * Ambient Light Color
 */
public class AmbientLight extends Light {

    /**
     * Constructor
     * @param Ia intensity color
     * @param Ka constant for intensity
     */
    public AmbientLight(Color Ia, double Ka) {
      super(Ia.scale(Ka));
    }

    /**
     * deafult constructor
     * itensity = color black
     */
    public AmbientLight() {
        super(Color.BLACK);
    }
}
