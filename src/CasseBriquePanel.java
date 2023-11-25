import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.awt.event.MouseEvent;

class CasseBriquePanel extends JPanel implements ActionListener, KeyListener, MouseMotionListener {
    private Balle balle;
    private Raquette raquette;
    private List<Brique> briques;
    private Timer timer;
    private boolean enPause;
    private PausePanel pausePanel;
    private Image fondGalaxie;

    public CasseBriquePanel() {
        setLayout(null);
        setBackground(Color.BLACK);

        balle = new Balle(200, 500, 20, Color.RED);
        raquette = new Raquette(getWidth() / 2 - 50, getHeight() - 100, 100, 20, Color.WHITE);
        timer = new Timer(10, this);
        timer.start();
        briques = creerBriques();
        centrerBriquesHorizontalement();
        fondGalaxie = new ImageIcon("C:\\Users\\tweek\\Desktop\\Projet Java POO\\images.jpg").getImage();

        addKeyListener(this);
        setFocusable(true);
        addMouseMotionListener(this);

        enPause = false;
        pausePanel = new PausePanel();
        pausePanel.setBounds(0, 0, getWidth(), getHeight());
        pausePanel.setVisible(false);
        add(pausePanel);
    }

    private List<Brique> creerBriques() {
        List<Brique> briques = new ArrayList<>();
        int largeurBrique = 80;
        int hauteurBrique = 40;
        int espacement = 5;
        int nombreLignes = 4;
        int nombreColonnes = 14;

        int offsetX = 725;
        int offsetY = 20;

        for (int i = 0; i < nombreLignes; i++) {
            for (int j = 0; j < nombreColonnes; j++) {
                int x = offsetX + j * (largeurBrique + espacement);
                int y = offsetY + i * (hauteurBrique + espacement);
                Color couleur = Color.GREEN;
                Brique brique = new Brique(x, y, largeurBrique, hauteurBrique, couleur);
                briques.add(brique);
            }
        }
        return briques;
    }

    private void centrerBriquesHorizontalement() {
        int largeurTotaleBriques = briques.get(0).getLargeur() * 13 + 5 * 12;
        int offsetX = (getWidth() - largeurTotaleBriques) / 2;

        for (Brique brique : briques) {
            brique.setX(brique.getX() + offsetX);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        balle.deplacer(getWidth(), getHeight());
        gestionCollisionRaquette();
        gestionCollisionBriques();
        repaint();
    }

    private void gestionCollisionBriques() {
        Rectangle boundingBoxBalle = balle.getBounds();
        Iterator<Brique> iterator = briques.iterator();

        while (iterator.hasNext()) {
            Brique brique = iterator.next();

            if (!brique.estTouchee() && brique.isVisible()) {
                balle.collisionBrique(brique);

                if (brique.estTouchee()) {
                    iterator.remove();
                }
            }
        }
    }

    private void gestionCollisionRaquette() {
        Rectangle boundingBoxBalle = balle.getBounds();
        Rectangle boundingBoxRaquette = raquette.getBounds();

        if (boundingBoxBalle.intersects(boundingBoxRaquette)) {
            balle.collisionRaquette(raquette);

            // Condition pour détecter un comportement indésirable
            if (Math.abs(balle.getDeplacement().getX()) < 0.5 && Math.abs(balle.getDeplacement().getY()) < 0.5) {
                // Si la vitesse de déplacement de la balle est très faible, il y a un comportement indésirable
                balle.restaurerPosition();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        raquette.setY(getHeight() - 100);

        // Dessiner l'image de fond (galaxie)
        g.drawImage(fondGalaxie, 0, 0, getWidth(), getHeight(), this);

        balle.afficher(g);
        raquette.afficher(g);

        for (Brique brique : briques) {
            if (!brique.estTouchee()) {
                brique.dessiner(g);
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        raquette.deplacer(e.getX());
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_ESCAPE) {
            enPause = !enPause;
            if (enPause) {
                timer.stop();
                pausePanel.setBounds(0,0,getWidth(),getHeight());
                pausePanel.setVisible(true);
            } else {
                timer.start();
                pausePanel.setVisible(false);
            }
            repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // Implémentation si nécessaire
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Ne rien faire ici
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Ne rien faire ici
    }
}
