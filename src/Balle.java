import java.awt.*;

public class Balle {

    private int x;
    private int y;
    private int diametre;
    private int dx; // changement de position en x
    private int dy; // changement de position en y
    private Color couleur;

    public Balle(int x, int y, int diametre, Color couleur) {
        this.x = x;
        this.y = y;
        this.diametre = diametre;
        this.couleur = couleur;
        this.dx = 5;
        this.dy = 5;
    }

    public void deplacer(int largeurPanneau, int hauteurPanneau) {
        if (x + dx < 0 || x + dx > largeurPanneau - diametre) {
            dx = -dx;
        }
        if (y + dy < 0 || y + dy > hauteurPanneau - diametre) {
            dy = -dy;
        }

        x += dx;
        y += dy;
    }

    public void afficher(Graphics g) {
        g.setColor(couleur);
        g.fillOval(x, y, diametre, diametre);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, diametre, diametre);
    }

    public void inverserDeplacementX() {
        dx = -dx;
    }

    public void inverserDeplacementY() {
        dy = -dy;
    }
}