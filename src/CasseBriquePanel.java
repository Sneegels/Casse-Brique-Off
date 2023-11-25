import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class CasseBriquePanel extends JPanel implements ActionListener, KeyListener, MouseMotionListener {
    private Balle balle;
    private Raquette raquette;
    private List<Brique> briques;
    private Timer timer;
    private JFrame pauseFrame;
    private Image fondGalaxie;
    private ImageIcon gameOverIcon;
    private boolean gameOver;
    private long gameOverTime;
    private boolean enPause;
    private PausePanel pausePanel;

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
        gameOverIcon = new ImageIcon("C:\\Users\\tweek\\Desktop\\Projet Java POO\\game-over-801800.png");

        addKeyListener(this);
        setFocusable(true);
        addMouseMotionListener(this);

        enPause = false;
        pauseFrame = createPauseFrame();

        gameOver = false;
        gameOverTime = 0;
    }

    private JFrame createPauseFrame() {
        JFrame frame = new JFrame("Pause");
        frame.setUndecorated(true);
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(new Color(0, 0, 0, 0));
        frame.setOpacity(0.7f);
        frame.setLocationRelativeTo(this);

        pausePanel = new PausePanel(this);
        frame.add(pausePanel);

        // Ajoute un ActionListener au bouton "Reprendre"
        pausePanel.getResumeButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reprendrePartieDepuisPause();
            }
        });

        return frame;
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
        if (!gameOver) {
            balle.deplacer(getWidth(), getHeight());
            gestionCollisionRaquette();
            gestionCollisionBriques();

            if (balle.getY() > getHeight()) {
                gameOver = true;
                gameOverTime = System.currentTimeMillis();
            }

            repaint();
        } else {
            long currentTime = System.currentTimeMillis();
            if (currentTime - gameOverTime >= 10000) {
                System.exit(0);
            }
        }
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

            if (Math.abs(balle.getDeplacement().getX()) < 0.5 && Math.abs(balle.getDeplacement().getY()) < 0.5) {
                balle.restaurerPosition();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        raquette.setY(getHeight() - 100);

        g.drawImage(fondGalaxie, 0, 0, getWidth(), getHeight(), this);

        balle.afficher(g);
        raquette.afficher(g);

        for (Brique brique : briques) {
            if (!brique.estTouchee()) {
                brique.dessiner(g);
            }
        }

        if (gameOver) {
            afficherGameOver(g);
        } else if (balle.getY() > getHeight()) {
            int iconWidth = gameOverIcon.getIconWidth();
            int iconHeight = gameOverIcon.getIconHeight();
            int x = (getWidth() - iconWidth) / 2;
            int y = (getHeight() - iconHeight) / 2;
            gameOverIcon.paintIcon(this, g, x, y);
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
                pauseFrame.setVisible(true);
            } else {
                timer.start();
                pauseFrame.setVisible(false);
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

    private void afficherGameOver(Graphics g) {
        removeAll();
        repaint();
        revalidate();

        JLabel gameOverLabel = new JLabel("Game Over");
        gameOverLabel.setForeground(Color.RED);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 36));
        gameOverLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gameOverLabel.setVerticalAlignment(SwingConstants.CENTER);

        int labelWidth = 200;
        int labelHeight = 50;
        int x = (getWidth() - labelWidth) / 2;
        int y = (getHeight() - labelHeight) / 2;

        gameOverLabel.setBounds(x, y, labelWidth, labelHeight);

        add(gameOverLabel);

        gameOver = true;
        gameOverTime = System.currentTimeMillis();

        Timer gameOverTimer = new Timer(10000, event -> System.exit(0));
        gameOverTimer.setRepeats(false);
        gameOverTimer.start();
    }

    public void reprendrePartie() {
        enPause = false;
        timer.start();
        pausePanel.setVisible(false);
        requestFocusInWindow();
        repaint();
    }

    public void reprendrePartieDepuisPause() {
        enPause = false;
        timer.start();
        pauseFrame.setVisible(false);
        requestFocusInWindow();
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Casse Brique");
        CasseBriquePanel casseBriquePanel = new CasseBriquePanel();
        frame.add(casseBriquePanel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
