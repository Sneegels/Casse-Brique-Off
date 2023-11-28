import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.*;

public class ChargementPartieFrame extends JFrame {
    private JLabel titleLabel;
    private FenetreMenu fenetreMenu;

    public ChargementPartieFrame(FenetreMenu fenetreMenu) {
        this.fenetreMenu = fenetreMenu;
        applyTheme();
        setDefaultAttributes();

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        add(Box.createVerticalGlue());

        add(createPartieButton("Partie 1"));
        add(createPartieButton("Partie 2"));
        add(createPartieButton("Partie 3"));

        add(Box.createVerticalGlue());

        add(createButton("Retour", e -> {
            dispose();
            lancerMenuPrincipal();
        }));

        add(Box.createVerticalGlue());

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
    }

    private void setDefaultAttributes() {
        setFont(new Font("Arial", Font.PLAIN, 18));
        getContentPane().setBackground(Color.BLACK);
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

    private void lancerMenuPrincipal() {
        FenetreMenu fenetreMenu = new FenetreMenu();
        fenetreMenu.setVisible(true);

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
            chargerPartie(text);
        });

        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2),
                BorderFactory.createEmptyBorder(50, 10, 50, 10)
        ));

        return button;
    }

    private void chargerPartie(String nomPartie) {
        String cheminFichier = "parties/" + nomPartie + ".ser";

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(cheminFichier))) {
            Partie partieChargee = (Partie) ois.readObject();
            System.out.println("Partie chargée : " + partieChargee);
            // Ajoutez le code pour utiliser la partie chargée
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private JButton createButton(String text) {
        return createButton(text, null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FenetreMenu fenetreMenu = new FenetreMenu();
            fenetreMenu.setVisible(true);
        });
    }
}
