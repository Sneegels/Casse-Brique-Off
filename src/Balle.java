import java.awt.*;

public class Balle {
    private int x;
    private int y;
    private int diametre;
    private Vecteur deplacement;
    private Color couleur;
    private double facteurAmortissement;
    private int ancienX;
    private int ancienY;

    public Balle(int x, int y, int diametre, Color couleur) {
        this.x = x;
        this.y = y;
        this.facteurAmortissement = 0.98;
        this.diametre = diametre;
        this.couleur = couleur;
        this.deplacement = new Vecteur(5, 5); // Vecteur de déplacement initial
    }

    public void deplacer(int largeurPanneau, int hauteurPanneau) {
        ancienX = x;
        ancienY = y;

        x += deplacement.getX();
        y += deplacement.getY();

        // Gestion de la collision avec les bords horizontaux
        if (x < 0 || x + diametre > largeurPanneau) {
            deplacement.inverserX();
            x = Math.max(0, Math.min(x, largeurPanneau - diametre));
        }

        // Gestion de la collision avec le bord inférieur
        if (y + diametre > hauteurPanneau) {
            // Faire sortir la balle de l'écran en bas
            y = hauteurPanneau;  // Vous pouvez également ajuster cette ligne pour définir une position spécifique
            // Inverser la direction verticale pour que la balle continue en dessous du bord inférieur
            deplacement.inverserY();
        } else if (y < 0) {
            // Gestion de la collision avec le bord supérieur
            deplacement.inverserY();
            y = 0;
        }
    }


    public void restaurerPosition() {
        // Restaurer la position précédente de la balle
        x = ancienX;
        y = ancienY;
    }

    public Vecteur getDeplacement() {
        return deplacement;
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

            // Ajuster la position de la balle pour éviter la téléportation
            ajusterPosition(brique, overlapX, overlapY);
        }
    }


    public void collisionRaquette(Raquette raquette) {
        Rectangle boundingBoxBalle = getBounds();
        Rectangle boundingBoxRaquette = raquette.getBounds();

        if (boundingBoxBalle.intersects(boundingBoxRaquette)) {
            double overlapX = Math.max(0, Math.min(boundingBoxBalle.getMaxX() - boundingBoxRaquette.getMinX(), boundingBoxRaquette.getMaxX() - boundingBoxBalle.getMinX()));
            double overlapY = Math.max(0, Math.min(boundingBoxBalle.getMaxY() - boundingBoxRaquette.getMinY(), boundingBoxRaquette.getMaxY() - boundingBoxBalle.getMinY()));

            // Ajuster la position de la balle pour éviter une nouvelle collision immédiate
            if (overlapX < overlapY) {
                // Ajuster la position en X
                x += (deplacement.getX() > 0) ? -1 : 1;
            } else {
                // Ajuster la position en Y
                y += (deplacement.getY() > 0) ? -1 : 1;
            }

            // Rebondir sur la raquette
            deplacement.inverserY();
        }
    }


    private void ajusterPosition(Brique brique, double overlapX, double overlapY) {
        // Ajuster la position de la balle en fonction du chevauchement
        if (overlapX < overlapY) {
            // Ajuster la position en X
            x += (x < brique.getX()) ? -overlapX : overlapX;
        } else {
            // Ajuster la position en Y
            y += (y < brique.getY()) ? -overlapY : overlapY;
        }
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getDiametre() {
        return diametre;
    }
}