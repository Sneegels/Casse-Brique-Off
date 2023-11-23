import java.awt.*;

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
        this.couleur = couleur;
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

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean estTouchee() {
        return touchee;
    }

    public void setTouchee(boolean touchee) {
        this.touchee = touchee;
    }

    public void marquerCommeTouchee() {
        touchee = true;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
