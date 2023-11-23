import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

class CasseBriquePanel extends JPanel implements ActionListener {
    private Balle balle;
    private List<Brique> briques;
    private Timer timer;

    public CasseBriquePanel() {
        setLayout(null);
        setBackground(Color.BLACK);

        balle = new Balle(200, 500, 20, Color.WHITE);
        timer = new Timer(10, this);
        timer.start();
        briques = creerBriques();
        centrerBriquesHorizontalement();
    }

    private List<Brique> creerBriques() {
        List<Brique> briques = new ArrayList<>();
        int largeurBrique = 80;
        int hauteurBrique = 40;
        int espacement = 5;
        int nombreLignes = 4;
        int nombreColonnes = 13;

        int offsetX = 750;
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

    private void centrerBriquesHorizontalement() {
        int largeurTotaleBriques = briques.get(0).getLargeur() * 13 + 5 * 12; // largeur totale des briques + espacements
        int offsetX = (getWidth() - largeurTotaleBriques) / 2;

        for (Brique brique : briques) {
            brique.setX(brique.getX() + offsetX);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        balle.deplacer(getWidth(), getHeight());
        gestionCollisionBriques();
        repaint();
    }

    private void gestionCollisionBriques() {
        Rectangle boundingBoxBalle = balle.getBounds();
        Iterator<Brique> iterator = briques.iterator();

        while (iterator.hasNext()) {
            Brique brique = iterator.next();

            if (!brique.estTouchee() && brique.isVisible()) {
                // Gérer la collision entre la balle et la brique
                balle.collisionBrique(brique);

                // Supprimer la brique si elle est touchée
                if (brique.estTouchee()) {
                    iterator.remove();
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        balle.afficher(g);

        for (Brique brique : briques) {
            if (!brique.estTouchee()) {
                brique.dessiner(g);
            }
        }
    }
}
