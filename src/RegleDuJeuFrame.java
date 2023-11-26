import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class RegleDuJeuFrame extends JFrame {
    private JLabel titleLabel;
    private FenetreMenu fenetreMenu;

    public RegleDuJeuFrame(FenetreMenu fenetreMenu) {
        this.fenetreMenu = fenetreMenu;
        applyTheme();
        setDefaultAttributes();
        initUI();

        // Ajouter de l'espace verticalement
        add(Box.createVerticalGlue());

        // Ajoutez le bouton "Retour" au bas de la fenêtre
        add(createButton("Retour", e -> {
            // Ferme la fenêtre de règles du jeu
            dispose();
            // Affiche la fenêtre du menu principal
            lancerMenuPrincipal();
        }));

        // Ajouter de l'espace verticalement
        add(Box.createVerticalGlue());

        // Mettez la fenêtre en plein écran et retirez la décoration (barre de titre, bordures, etc.)
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);

        // Rendre la fenêtre visible après l'ajout de tous les composants
        setVisible(true);
    }

    private void initUI() {
        // Texte de règles
        String reglesText = "Le but du jeu est de faire rebondir la balle sur la raquette pour détruire toutes les briques et collecter les points. " +
                "La raquette est dirigée à gauche ou à droite par les deux flèches (droite et gauche) du clavier. " +
                "Quand la balle tombe par terre, le jeu s’arrête.";

        // Créer une zone de texte avec le texte
        JTextArea textArea = new JTextArea(reglesText);
        textArea.setFont(new Font("Arial", Font.PLAIN, 18));
        textArea.setForeground(Color.WHITE);
        textArea.setLineWrap(true);  // Activer le retour automatique à la ligne
        textArea.setWrapStyleWord(true);  // Coupe les mots pour s'adapter à la largeur

        // Centrer le texte dans la zone de texte
        textArea.setAlignmentX(CENTER_ALIGNMENT);

        // Utiliser un panneau pour organiser les composants
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(Color.BLACK);  // Fond du panneau

        // Créez un conteneur pour le titre
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.BLACK);  // Choisissez la couleur de fond appropriée
        titlePanel.add(titleLabel);

        // Ajoutez le conteneur du titre en haut de la fenêtre
        contentPanel.add(titlePanel, BorderLayout.NORTH);

        // Ajouter la zone de texte centrée au centre du panneau
        contentPanel.add(textArea, BorderLayout.CENTER);


        // Définir le contenu du panneau comme le contenu de la fenêtre
        setContentPane(contentPanel);

        // Configurations générales de la fenêtre
        setTitle("Règle du Jeu");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        pack();  // Redimensionner la fenêtre en fonction des composants
        setLocationRelativeTo(null);  // Centrer la fenêtre sur l'écran
        setVisible(true);
    }

    private void setDefaultAttributes() {
        setFont(new Font("Arial", Font.PLAIN, 18));
        getContentPane().setBackground(Color.BLACK);
        setTitle("Règle du Jeu");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void applyTheme() {
        titleLabel = new JLabel("Règles du jeu");
        titleLabel.setFont(new Font("Impact", Font.BOLD, 40));
        titleLabel.setForeground(new Color(150, 50, 0));
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);

        add(Box.createVerticalStrut(50));
        add(titleLabel);
        add(Box.createVerticalGlue());
    }

    private void lancerMenuPrincipal() {
        // Utilisez la référence à la fenêtre du menu principal
        fenetreMenu.setExtendedState(JFrame.MAXIMIZED_BOTH);
        fenetreMenu.setVisible(true);

        // Fermez la fenêtre actuelle
        dispose();
    }

    private JButton createButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setAlignmentX(CENTER_ALIGNMENT);

        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(Color.DARK_GRAY);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);

        if (actionListener != null) {
            button.addActionListener(actionListener);
        }

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

    private JButton createButton(String text) {
        return createButton(text, null);
    }
}