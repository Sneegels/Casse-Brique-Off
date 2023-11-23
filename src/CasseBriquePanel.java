import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

class CasseBriquePanel extends JPanel implements ActionListener {
    private Balle balle;
    private List<Brique> briques;
    private Timer timer;

    public CasseBriquePanel() {
        setLayout(null);
        setBackground(Color.BLACK);

        balle = new Balle(200, 500, 20, Color.WHITE);
        briques = creerBriques();
        timer = new Timer(10, this);
        timer.start();
    }

    private List<Brique> creerBriques() {
        List<Brique> briques = new ArrayList<>();
        int largeurBrique = 80;
        int hauteurBrique = 40;
        int espacement = 5;
        int nombreLignes = 4;
        int nombreColonnes = 10;

        int offsetX = (getWidth() - (nombreColonnes * (largeurBrique + espacement) - espacement)) / 2;
        int offsetY = 20;

        for (int i = 0; i < nombreLignes; i++) {
            for (int j = 0; j < nombreColonnes; j++) {
                int x = offsetX + j * (largeurBrique + espacement);
                int y = offsetY + i * (hauteurBrique + espacement);
                Color couleur = Color.GREEN; // Choisissez la couleur de la brique
                Brique brique = new Brique(x, y, largeurBrique, hauteurBrique, couleur);
                briques.add(brique);
            }
        }
        return briques;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        balle.deplacer(getWidth(), getHeight());
        gestionCollisionBriques();
        repaint();
    }

    private void gestionCollisionBriques() {
        Rectangle boundingBoxBalle = balle.getBounds();

        for (Brique brique : briques) {
            if (brique.isVisible()) {
                Rectangle boundingBoxBrique = brique.getRectangle();
                if (boundingBoxBalle.intersects(boundingBoxBrique)) {
                    // La balle touche la brique

                    // Déterminez la direction de la collision (haut, bas, gauche, droite)
                    int dx = (int) Math.signum(balle.getDeplacementX());
                    int dy = (int) Math.signum(balle.getDeplacementY());

                    // Ajustez la position de la balle pour qu'elle ne soit pas à l'intérieur de la brique
                    if (dx != 0) {
                        balle.setX(brique.getX() - dx * (balle.getDiametre() / 2 + 1));
                    }
                    if (dy != 0) {
                        balle.setY(brique.getY() - dy * (balle.getDiametre() / 2 + 1));
                    }

                    // Inversez la direction de la balle dans la direction de la collision
                    if (dx != 0) {
                        balle.inverserDeplacementX();
                    }
                    if (dy != 0) {
                        balle.inverserDeplacementY();
                    }

                    // Masquez la brique
                    brique.setVisible(false);

                    // Ajoutez ici toute autre logique de gestion de collision
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        briques = creerBriques(); // Recrée les briques à chaque redessin
        balle.afficher(g);
        for (Brique brique : briques) {
            brique.dessiner(g);
        }
    }
}