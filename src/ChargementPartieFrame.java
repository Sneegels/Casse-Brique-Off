import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;

class ChargementPartieFrame extends JFrame {
    private JLabel titleLabel;
    private FenetreMenu fenetreMenu;

    public ChargementPartieFrame(FenetreMenu fenetreMenu) {
        this.fenetreMenu = fenetreMenu;
        applyTheme();
        setDefaultAttributes();

        // Utilisez un BoxLayout vertical pour aligner les composants de haut en bas
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Ajouter de l'espace verticalement
        add(Box.createVerticalGlue());

        // Ajouter trois boutons pour les parties
        add(createPartieButton("Partie 1"));
        add(createPartieButton("Partie 2"));
        add(createPartieButton("Partie 3"));

        // Ajouter de l'espace verticalement
        add(Box.createVerticalGlue());

        // Ajoutez le bouton "Retour" au bas de la fenêtre
        add(createButton("Retour", e -> {
            // Ferme la fenêtre de chargement et affiche la fenêtre du menu principal
            dispose();
            lancerMenuPrincipal();
        }));

        // Ajouter de l'espace verticalement
        add(Box.createVerticalGlue());

        // Mettez la fenêtre en plein écran et retirez la décoration (barre de titre, bordures, etc.)
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
    }

    private void setDefaultAttributes() {
        setFont(new Font("Arial", Font.PLAIN, 18));
        getContentPane().setBackground(Color.BLACK);  // Définir la couleur de fond du conteneur principal
        setTitle("Chargement de la Partie");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void applyTheme() {
        titleLabel = new JLabel("Veuillez charger une partie");
        titleLabel.setFont(new Font("Impact", Font.BOLD, 40));
        titleLabel.setForeground(new Color(150, 50, 0));
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);

        add(Box.createVerticalStrut(50));
        add(titleLabel);
        add(Box.createVerticalGlue());
    }

    // Ajoutez cette méthode pour lancer le menu principal depuis ChargementPartieFrame
    private void lancerMenuPrincipal() {
        FenetreMenu fenetreMenu = new FenetreMenu();
        fenetreMenu.setVisible(true);

        // Fermez la fenêtre actuelle
        Window window = SwingUtilities.getWindowAncestor(this);
        if (window != null) {
            window.dispose();
        }
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

    private JButton createPartieButton(String text) {
        JButton button = createButton(text);
        button.addActionListener(e -> {
            // Ajoutez le code pour charger la partie sélectionnée
            System.out.println("Charger la " + text);
        });

        // Ajoutez une bordure autour du bouton pour l'effet d'encadrement
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2),
                BorderFactory.createEmptyBorder(50, 10, 50, 10)
        ));

        return button;
    }

    // Utilisez la surcharge de la méthode pour créer le bouton "Retour" sans ActionListener
    private JButton createButton(String text) {
        return createButton(text, null);
    }
}
