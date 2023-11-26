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
        // Ajoutez du texte ou tout composant nécessaire à votre fenêtre de règles ici
        String reglesText = "Le but du jeu est de faire rebondir la balle sur la raquette pour détruire toutes les briques et collecter les points. \n\n" +
                "La raquette est dirigée à gauche ou à droite par les deux flèches (droite et gauche) du clavier. \n\n" +
                "Quand la balle tombe par terre le jeu s’arrête.";

        JLabel textLabel = new JLabel(reglesText);
        textLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        textLabel.setForeground(Color.WHITE);

        // Utilisez une mise en page pour organiser les composants
        setLayout(new BorderLayout());

        // Créez un conteneur pour le titre
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.BLACK);  // Choisissez la couleur de fond appropriée
        titlePanel.add(titleLabel);

        // Ajoutez le conteneur du titre en haut de la fenêtre
        add(titlePanel, BorderLayout.NORTH);

        // Ajoutez la zone de texte au centre
        add(textLabel, BorderLayout.CENTER);
    }

    private void setDefaultAttributes() {
        setFont(new Font("Arial", Font.PLAIN, 18));
        getContentPane().setBackground(Color.BLACK);
        setTitle("Règle du Jeu");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Définir une icône pour la fenêtre (assurez-vous d'avoir le fichier icon.png dans le même répertoire)
        ImageIcon icon = new ImageIcon("icon.png");
        setIconImage(icon.getImage());
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
