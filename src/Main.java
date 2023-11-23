import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Casse-Brique");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            FenetreMenuPanel fenetreMenuPanel = new FenetreMenuPanel();
            frame.add(fenetreMenuPanel);

            // Mettre le jeu en plein écran
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setUndecorated(true);
            frame.setVisible(true);
        });
    }
}