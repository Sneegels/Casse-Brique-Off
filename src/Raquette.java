import java.awt.*;

public class Raquette {
    private int x;
    private int y;
    private int largeur;
    private int hauteur;
    private Color couleur;

    public Raquette(int x, int y, int largeur, int hauteur, Color couleur) {
        this.x = x;
        this.y = y;
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.couleur = couleur;
    }

    public void deplacer(int newX) {
        // Déplacer la raquette en fonction de la position x de la souris
        x = newX - largeur / 2;
    }

    public void afficher(Graphics g) {
        g.setColor(couleur);
        g.fillRect(x, y, largeur, hauteur);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, largeur, hauteur);
    }

    public void setY(int newY) {
        y = newY;
    }

    public void dessiner(Graphics g) {
        g.setColor(couleur);
        g.fillRect(x, y, largeur, hauteur);
    }

}