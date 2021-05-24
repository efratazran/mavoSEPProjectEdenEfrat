package elements;

import primitives.Color;

abstract class Light {
    /**
     * field
     */
    private Color intensity;

    /**
     * constructor of Light
     * @param intensity get a color
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * @return color intesity
     */
    public Color getintensity() {
        return intensity;
    }
}
