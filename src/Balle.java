import java.awt.*;

public class Balle {
    private int x;
    private int y;
    private int diametre;
    private Vecteur deplacement;
    private Color couleur;

    public Balle(int x, int y, int diametre, Color couleur) {
        this.x = x;
        this.y = y;
        this.diametre = diametre;
        this.couleur = couleur;
        this.deplacement = new Vecteur(5, 5); // Vecteur de déplacement initial
    }

    public void deplacer(int largeurPanneau, int hauteurPanneau) {
        if (x + deplacement.getX() < 0 || x + deplacement.getX() > largeurPanneau - diametre) {
            // Inverser la composante x du vecteur de déplacement en cas de collision avec les bords horizontaux
            deplacement.inverserX();
        }
        if (y + deplacement.getY() < 0 || y + deplacement.getY() > hauteurPanneau - diametre) {
            // Inverser la composante y du vecteur de déplacement en cas de collision avec les bords verticaux
            deplacement.inverserY();
        }

        x += deplacement.getX();
        y += deplacement.getY();
    }

    public void afficher(Graphics g) {
        g.setColor(couleur);
        g.fillOval(x, y, diametre, diametre);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, diametre, diametre);
    }

    public void collisionBrique(Brique brique) {
        Rectangle boundingBoxBalle = getBounds();
        Rectangle boundingBoxBrique = brique.getRectangle();

        if (boundingBoxBalle.intersects(boundingBoxBrique)) {
            // La balle touche la brique
            // Ajoutez ici toute autre logique de gestion de collision

            // Calculer le vecteur normal à la surface de la brique
            Vecteur normal = new Vecteur(0, 0);
            if (x + diametre <= brique.getX() || x >= brique.getX() + brique.getLargeur()) {
                // La collision se produit sur le côté de la brique, inverser le déplacement en x
                normal.setX((x + diametre <= brique.getX()) ? -1 : 1);
            } else {
                // La collision se produit en haut ou en bas de la brique, inverser le déplacement en y
                normal.setY((y + diametre <= brique.getY()) ? -1 : 1);
            }

            // Calculer la composante du vecteur de déplacement parallèle à la surface de la brique
            double dotProduct = deplacement.dot(normal);
            Vecteur parallel = normal.mult(dotProduct);

            // Calculer la composante du vecteur de déplacement perpendiculaire à la surface de la brique
            Vecteur perpendicular = deplacement.subtract(parallel);

            // Inverser la composante perpendiculaire
            perpendicular.inverser();

            // Mettre à jour le vecteur de déplacement en utilisant les composantes recalculées
            deplacement = parallel.add(perpendicular);

            // Marquer la brique comme touchée
            brique.setTouchee(true);
        }
    }

}
