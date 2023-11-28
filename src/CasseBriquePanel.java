import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentAdapter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

class CasseBriquePanel extends JPanel implements ActionListener, KeyListener, MouseMotionListener {
    private Balle balle;
    private Raquette raquette;
    private List<Brique> briques;
    private Timer timer;
    private Timer closeTimer;
    private JFrame pauseFrame;
    private Image fondGalaxie;
    private boolean gameOver;
    private long gameOverTime;
    private boolean enPause;
    private PausePanel pausePanel;
    private JButton nouvellePartieButton;
    private JButton quitterButton;

    public CasseBriquePanel() {
        setLayout(null);
        setBackground(Color.BLACK);

        closeTimer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Fermez l'application
            }
        });

        balle = new Balle(200, 500, 20, new Color(195, 223, 225));
        raquette = new Raquette(getWidth() / 2 - 50, getHeight() - 100, 175, 20, new Color(224, 225, 79));
        timer = new Timer(10, this);
        timer.start();
        briques = creerBriques();
        centrerBriquesHorizontalement();
        fondGalaxie = new ImageIcon("C:\\Users\\tweek\\Desktop\\Projet Java POO\\images.jpg").getImage();

        nouvellePartieButton = createStyledButton("Nouvelle Partie");
        quitterButton = createStyledButton("Quitter");

        addKeyListener(this);
        setFocusable(true);
        addMouseMotionListener(this);

        enPause = false;
        pauseFrame = createPauseFrame();

        gameOver = false;
        gameOverTime = 0;

        // Ajout du ComponentListener pour redimensionner la fenêtre
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                raquette.setY(getHeight() - 100);
                balle.setY(Math.min(balle.getY(), getHeight() - 20));
            }
        });
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
        int nombreLignes = 1;
        int nombreColonnes = 1;

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

    private void afficherMessageDefaite() {
        SwingUtilities.invokeLater(() -> {
            JDialog defeatDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Défaite", true);
            defeatDialog.setUndecorated(true);
            defeatDialog.setSize(300, 200);
            defeatDialog.setLocationRelativeTo(this);

            JPanel panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g.create();

                    // Draw a transparent gradient background from gray to black
                    GradientPaint gradient = new GradientPaint(0, 0, new Color(50, 50, 50, 200),
                            0, getHeight(), new Color(0, 0, 0, 200));
                    g2d.setPaint(gradient);
                    g2d.fillRect(0, 0, getWidth(), getHeight());

                    g2d.dispose();
                }
            };

            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            JLabel defeatLabel = new JLabel("Défaite !");
            defeatLabel.setForeground(Color.RED);
            defeatLabel.setFont(new Font("Arial", Font.BOLD, 36));
            defeatLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JButton recommencerButton = createStyledButton("Recommencer");
            JButton quitterButton = createStyledButton("Quitter");

            recommencerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            quitterButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            recommencerButton.addActionListener(e -> {
                relancerNouvellePartie();
                defeatDialog.dispose();
            });

            quitterButton.addActionListener(e -> {
                System.exit(0);
            });

            panel.add(Box.createVerticalGlue());
            panel.add(defeatLabel);
            panel.add(Box.createRigidArea(new Dimension(0, 20)));
            panel.add(recommencerButton);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
            panel.add(quitterButton);
            panel.add(Box.createVerticalGlue());

            defeatDialog.setContentPane(panel);

            defeatDialog.setVisible(true);
        });
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
                timer.stop();
                afficherMessageDefaite();
            }

            if (briques.isEmpty()) {
                gameOver = true;
                gameOverTime = System.currentTimeMillis();
                timer.stop();
                afficherVictoire();
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
        } else {
            // Ajustez la valeur de zoneDeDefaiteY pour déplacer la zone vers le bas
            int zoneDeDefaiteY = raquette.getY() + raquette.getHauteur() + 20; // Ajustez la valeur 20 selon votre besoin

            if (balle.getY() > zoneDeDefaiteY) {
                gameOver = true;
                gameOverTime = System.currentTimeMillis();
                timer.stop();
                afficherMessageDefaite();
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

        if (balle.getY() > getHeight()) {
            afficherGameOver();
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

    private void afficherGameOver() {
        // Afficher le message "Game Over" avec une police en rouge et centré
        Graphics g = getGraphics();
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        String gameOverMessage = "Game Over";
        FontMetrics fontMetrics = g.getFontMetrics();
        int x = (getWidth() - fontMetrics.stringWidth(gameOverMessage)) / 2;
        int y = getHeight() / 2;
        g.drawString(gameOverMessage, x, y);

        // Afficher un bouton pour recommencer ou quitter
        afficherBoutonsFinPartie("Recommencer", "Quitter");
    }

    private void afficherDefaite() {
        JDialog defeatDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Défaite", true);
        defeatDialog.setUndecorated(true);
        defeatDialog.setSize(300, 200);
        defeatDialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();

                // Draw a transparent gradient background from gray to black
                GradientPaint gradient = new GradientPaint(0, 0, new Color(50, 50, 50, 200),
                        0, getHeight(), new Color(0, 0, 0, 200));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                g2d.dispose();
            }
        };

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel defeatLabel = new JLabel("Défaite !");
        defeatLabel.setForeground(Color.RED);
        defeatLabel.setFont(new Font("Arial", Font.BOLD, 36));
        defeatLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton recommencerButton = createStyledButton("Recommencer");
        JButton quitterButton = createStyledButton("Quitter");

        recommencerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        quitterButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        recommencerButton.addActionListener(e -> {
            relancerNouvellePartie();
            defeatDialog.dispose();
        });

        quitterButton.addActionListener(e -> {
            System.exit(0);
        });

        panel.add(Box.createVerticalGlue());
        panel.add(defeatLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(recommencerButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(quitterButton);
        panel.add(Box.createVerticalGlue());

        defeatDialog.setContentPane(panel);

        defeatDialog.setVisible(true);
    }

    private void afficherVictoire() {
        JDialog victoryDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Victoire", true);
        victoryDialog.setUndecorated(true);
        victoryDialog.setSize(300, 200);
        victoryDialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();

                // Draw a transparent gradient background from gray to black
                GradientPaint gradient = new GradientPaint(0, 0, new Color(50, 50, 50, 200),
                        0, getHeight(), new Color(0, 0, 0, 200));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                g2d.dispose();
            }
        };

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel victoryLabel = new JLabel("Victoire !");
        victoryLabel.setForeground(Color.GREEN);
        victoryLabel.setFont(new Font("Arial", Font.BOLD, 36));
        victoryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton nouvellePartieButton = createStyledButton("Nouvelle Partie");
        JButton quitterButton = createStyledButton("Quitter");

        nouvellePartieButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        quitterButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        nouvellePartieButton.addActionListener(e -> {
            relancerNouvellePartie();
            victoryDialog.dispose();
        });

        quitterButton.addActionListener(e -> {
            System.exit(0);
        });

        panel.add(Box.createVerticalGlue());
        panel.add(victoryLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(nouvellePartieButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(quitterButton);
        panel.add(Box.createVerticalGlue());

        victoryDialog.setContentPane(panel);

        victoryDialog.setVisible(true);
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

    private void relancerNouvellePartie() {
        removeAll();
        revalidate();

        balle = new Balle(200, 500, 20, new Color(195, 223, 225));
        raquette = new Raquette(getWidth() / 2 - 50, getHeight() - 100, 175, 20, new Color(224, 225, 79));
        briques = creerBriques();
        centrerBriquesHorizontalement();

        enPause = false;
        gameOver = false;  // Réinitialiser le statut du jeu
        gameOverTime = 0;  // Réinitialiser le temps de fin de jeu

        timer.start();
        repaint();
    }

    private void afficherBoutonsFinPartie(String labelRecommencer, String labelQuitter) {
        JButton recommencerButton = createStyledButton(labelRecommencer);
        JButton quitterButton = createStyledButton(labelQuitter);

        recommencerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        quitterButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        recommencerButton.addActionListener(e -> {
            relancerNouvellePartie();
        });

        quitterButton.addActionListener(e -> {
            System.exit(0);
        });

        afficherComposantsFinPartie(recommencerButton, quitterButton);
    }

    private void afficherComposantsFinPartie(JComponent... composants) {
        JDialog gameOverDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Fin de partie", true);
        gameOverDialog.setUndecorated(true);
        gameOverDialog.setSize(300, 200);
        gameOverDialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();

                // Draw a transparent gradient background from gray to black
                GradientPaint gradient = new GradientPaint(0, 0, new Color(50, 50, 50, 200),
                        0, getHeight(), new Color(0, 0, 0, 200));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                g2d.dispose();
            }
        };

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        for (JComponent composant : composants) {
            panel.add(Box.createVerticalGlue());
            panel.add(composant);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        gameOverDialog.setContentPane(panel);

        gameOverDialog.setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);

        Border line = new LineBorder(Color.WHITE);
        Border margin = new EmptyBorder(5, 15, 5, 15);
        Border compound = new CompoundBorder(line, margin);
        button.setBorder(compound);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setForeground(Color.RED);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setForeground(Color.WHITE);
            }
        });

        return button;
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
