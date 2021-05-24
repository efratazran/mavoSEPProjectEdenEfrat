package primitives;

public class Material {

    /**
     * field
     */
    public double kD=0;
    public double kS=0;
    public int Shininess=0;

    /**
     * setters format builder
     */
    public Material setKd(double kD) {
        this.kD = kD;
        return this;
    }

    public Material setKs(double kS) {
        this.kS = kS;
        return this;
    }

    public Material setShininess(int Shininess) {
        this.Shininess = Shininess;
        return this;
    }
}
