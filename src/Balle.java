import java.awt.*;

public class Balle {
    private int x;
    private int y;
    private int diametre;
    private Vecteur deplacement;
    private Color couleur;
    private double facteurAmortissement;

    public Balle(int x, int y, int diametre, Color couleur) {
        this.x = x;
        this.y = y;
        this.facteurAmortissement = 0.98;
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
            double overlapX = Math.max(0, Math.min(boundingBoxBalle.getMaxX() - boundingBoxBrique.getMinX(), boundingBoxBrique.getMaxX() - boundingBoxBalle.getMinX()));
            double overlapY = Math.max(0, Math.min(boundingBoxBalle.getMaxY() - boundingBoxBrique.getMinY(), boundingBoxBrique.getMaxY() - boundingBoxBalle.getMinY()));

            // Déterminer la direction du rebond en fonction de l'overlap maximum
            if (overlapX > overlapY) {
                // Rebond vertical
                deplacement.inverserY();
            } else {
                // Rebond horizontal
                deplacement.inverserX();
            }

            // Marquer la brique comme touchée
            brique.setTouchee(true);

            // Réduire la vitesse après la collision pour éviter les rebonds multiples rapides
            deplacement = deplacement.mult(facteurAmortissement);
        }
    }




    private void ajusterPosition(Brique brique, Rectangle boundingBoxBrique) {
        double overlapX = 0;
        double overlapY = 0;

        // Calculer la quantité de chevauchement en X
        if (x < boundingBoxBrique.getX()) {
            overlapX = x + diametre - boundingBoxBrique.getX();
        } else {
            overlapX = boundingBoxBrique.getMaxX() - x;
        }

        // Calculer la quantité de chevauchement en Y
        if (y < boundingBoxBrique.getY()) {
            overlapY = y + diametre - boundingBoxBrique.getY();
        } else {
            overlapY = boundingBoxBrique.getMaxY() - y;
        }

        // Ajuster la position de la balle en fonction du chevauchement
        if (overlapX < overlapY) {
            // Ajuster la position en X
            x += (x < boundingBoxBrique.getX()) ? -overlapX : overlapX;
        } else {
            // Ajuster la position en Y
            y += (y < boundingBoxBrique.getY()) ? -overlapY : overlapY;
        }
    }



}