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

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void inverserX() {
        x = -x;
    }

    public void inverserY() {
        y = -y;
    }

    public void inverser() {
        x = -x;
        y = -y;
    }

    public Vecteur refleter(Vecteur normal) {
        // Utilisez la formule de réflexion pour calculer le vecteur réfléchi
        double dotProduct = this.dot(normal);
        Vecteur projection = normal.mult(2 * dotProduct);
        return this.subtract(projection);
    }



    public double dot(Vecteur other) {
        return x * other.x + y * other.y;
    }

    public Vecteur add(Vecteur other) {
        return new Vecteur(x + other.x, y + other.y);
    }

    public Vecteur subtract(Vecteur other) {
        return new Vecteur(x - other.x, y - other.y);
    }

    public Vecteur mult(double scalar) {
        return new Vecteur(x * scalar, y * scalar);
    }
}
