import java.awt.*;
import java.util.Random;

public class Brique {

    private int x;
    private int y;
    private int largeur;
    private int hauteur;
    private Color couleur;
    private boolean visible;
    private boolean touchee;

    public Brique(int x, int y, int largeur, int hauteur, Color couleur) {
        this.x = x;
        this.y = y;
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.couleur = genererCouleurAleatoire();
        this.visible = true;
        this.touchee = false;
    }

    public void dessiner(Graphics g) {
        if (visible) {
            g.setColor(couleur);
            g.fillRect(x, y, largeur, hauteur);
        }
    }

    public Rectangle getRectangle() {
        return new Rectangle(x, y, largeur, hauteur);
    }

    public boolean isVisible() {
        return visible;
    }

    public boolean estTouchee() {
        return touchee;
    }

    private static final Color[] COULEURS_DISPONIBLES = {
            new Color(0, 0, 150),     // Bleu
            new Color(150, 100, 0),   // Violet
            new Color(0, 0, 0), // Gris
            new Color(0, 150, 0)      // Vert sombre
    };

    private Color genererCouleurAleatoire() {
        Random rand = new Random();
        return COULEURS_DISPONIBLES[rand.nextInt(COULEURS_DISPONIBLES.length)];
    }

    public void setTouchee(boolean touchee) {
        this.touchee = touchee;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getLargeur() {
        return largeur;
    }
}