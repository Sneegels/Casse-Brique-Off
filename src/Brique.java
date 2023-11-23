import java.awt.*;

public class Brique {

    private int x;
    private int y;
    private int largeur;
    private int hauteur;
    private Color couleur;
    private boolean visible;

    public Brique(int x, int y, int largeur, int hauteur, Color couleur) {
        this.x = x;
        this.y = y;
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.couleur = couleur;
        this.visible = true;
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
}
