import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FenetreMenu extends JFrame {

    private JFrame frame;
    private JPanel cards;
    private CasseBriquePanel casseBriquePanel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Casse-Brique");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            FenetreMenuPanel fenetreMenuPanel = new FenetreMenuPanel();  // Passer la référence de la fenêtre à FenetreMenuPanel
            frame.add(fenetreMenuPanel);

            frame.setSize(400, 700);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    public FenetreMenu() {
        frame = new JFrame("Casse-Brique");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cards = new JPanel(new CardLayout());

        FenetreMenuPanel menuPanel = new FenetreMenuPanel();
        cards.add(menuPanel, "menu");

        casseBriquePanel = new CasseBriquePanel();
        cards.add(casseBriquePanel, "casseBrique");

        frame.add(cards);

        frame.setSize(400, 700);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

class FenetreMenuPanel extends JPanel implements ActionListener {
    private JLabel titleLabel;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JLabel teamLabel;
    private FenetreMenu fenetreMenu;

    public FenetreMenuPanel() {
        this.fenetreMenu = fenetreMenu;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.BLACK);
        titleLabel = new JLabel("Casse-Brique");
        titleLabel.setFont(new Font("Impact", Font.BOLD, 40));
        titleLabel.setForeground(new Color(150, 50, 0)); // Titre plus clair
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);

        button1 = createButton("Nouvelle Partie", KeyEvent.VK_1, "Ctrl+1");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lancerPartie();
            }
        });

        button2 = createButton("Charger une Partie", KeyEvent.VK_2, "Ctrl+2");

        button3 = createButton("Consulter les règles", KeyEvent.VK_3, "Ctrl+3");

        add(Box.createVerticalGlue());

        add(titleLabel);

        add(Box.createVerticalGlue());

        add(createOptionPanel(button1));
        add(Box.createVerticalStrut(20));
        add(createOptionPanel(button2));
        add(Box.createVerticalStrut(20));
        add(createOptionPanel(button3));
        add(Box.createVerticalStrut(100));

        add(Box.createVerticalGlue());

        teamLabel = new JLabel("Gottfried Cyril, Chalmet Gabriel, Biji Fayçal");
        teamLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        teamLabel.setForeground(new Color(60, 60, 60));
        teamLabel.setAlignmentX(CENTER_ALIGNMENT);
        teamLabel.setAlignmentY(10);
        add(teamLabel);

        // Dans la méthode constructeur de FenetreMenuPanel
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lancerChargementPartie();
            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lancerRegle();
            }
        });
    }

    public void lancerRegle() {
        RegleDuJeuFrame regleDuJeuFrame = new RegleDuJeuFrame(fenetreMenu);
        regleDuJeuFrame.setVisible(true);

        // Fermez la fenêtre actuelle
        Window window = SwingUtilities.getWindowAncestor(this);
        if (window != null) {
            window.dispose();
        }
        repaint(); // Ajoutez cette ligne si nécessaire
    }

    public void lancerChargementPartie() {
        ChargementPartieFrame chargementPartieFrame = new ChargementPartieFrame(fenetreMenu);
        chargementPartieFrame.setVisible(true);

        // Fermez la fenêtre actuelle
        Window window = SwingUtilities.getWindowAncestor(this);
        if (window != null) {
            window.dispose();
        }
        repaint(); // Ajoutez cette ligne si nécessaire
    }




    public void lancerPartie() {
        // Créez votre instance CasseBriquePanel ici
        CasseBriquePanel casseBriquePanel = new CasseBriquePanel();

        // Créez une nouvelle JFrame pour votre jeu
        JFrame jeuFrame = new JFrame("Casse-Brique");

        // Mettez la fenêtre en plein écran
        jeuFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jeuFrame.setUndecorated(true);

        // Ajoutez le CasseBriquePanel à la fenêtre du jeu
        jeuFrame.setContentPane(casseBriquePanel);

        // Définissez les opérations à effectuer lorsque la fenêtre est fermée
        jeuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Rendez la fenêtre visible
        jeuFrame.setVisible(true);

        // Fermez la fenêtre actuelle
        Window window = SwingUtilities.getWindowAncestor(this);
        window.dispose();
    }


    private JPanel createOptionPanel(JButton button) {
        JPanel optionPanel = new JPanel();
        optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.X_AXIS));
        optionPanel.setBackground(Color.BLACK);

        optionPanel.add(Box.createHorizontalGlue());
        optionPanel.add(button);
        optionPanel.add(Box.createHorizontalGlue());

        optionPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        optionPanel.setMaximumSize(new Dimension(200, 40));

        return optionPanel;
    }

    private JButton createButton(String text, int mnemonic, String tooltip) {
        JButton button = new JButton(text);
        button.setMnemonic(mnemonic);
        button.setToolTipText(tooltip);

        button.setAlignmentX(CENTER_ALIGNMENT);
        button.addActionListener(this);

        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(Color.DARK_GRAY);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);

        // Ajouter un effet de survol
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setForeground(Color.LIGHT_GRAY);
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setForeground(Color.WHITE);
                setCursor(Cursor.getDefaultCursor());
            }
        });

        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button1) {
            System.out.println("Option 1 selected");
        } else if (e.getSource() == button2) {
            System.out.println("Option 2 selected");
        } else if (e.getSource() == button3) {
            System.out.println("Option 3 selected");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();

        // Dessine des étoiles
        for (int i = 0; i < 100; i++) {
            int x = (int) (Math.random() * width);
            int y = (int) (Math.random() * height);
            int size = (int) (Math.random() * 2) + 1;

            g.setColor(Color.WHITE);
            g.fillRect(x, y, size, size);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(400, 700);
    }
}