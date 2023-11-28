public class Vecteur {
    private double x;
    private double y;

    public Vecteur(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void inverserX() {
        x = -x;
    }

    public void inverserY() {
        y = -y;
    }

    public Vecteur add(Vecteur other) {
        return new Vecteur(x + other.x, y + other.y);
    }

    public Vecteur mult(double scalar) {
        return new Vecteur(x * scalar, y * scalar);
    }
}
