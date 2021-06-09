package primitives;

/**
 * class material
 */
public class Material {

    /**
     * field
     */
    public double kD=0;
    public double kS=0;
    public int Shininess=0;
    public double kT=0.0;
    public double kR=0.0;

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
    public Material setKT(double kT) {
        this.kT = kT;
        return this;
    }

    public Material setkR(double kR) {
        this.kR = kR;
        return this;
    }

    public Material setShininess(int Shininess) {
        this.Shininess = Shininess;
        return this;
    }
}
