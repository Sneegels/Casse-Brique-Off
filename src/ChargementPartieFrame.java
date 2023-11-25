import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChargementPartieFrame extends JFrame {
    private JComboBox<String> partieListeDeroulante;
    private JButton charge;

    public ChargementPartieFrame() {
        initUI();
        initListeners();
    }

    private void initUI() {
        // Initialisez les composants de la fenêtre de chargement ici
        JPanel panel = new JPanel();
        partieListeDeroulante = new JComboBox<>(new String[]{"Partie 1", "Partie 2", "Partie 3"});  // Ajoutez vos parties ici
        charge = new JButton("Charger la partie");

        panel.add(partieListeDeroulante);
        panel.add(charge);

        add(panel);
    }

    private void initListeners() {
        // Ajoutez un ActionListener au bouton de chargement
        charge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chargerPartieSelectionnee();
            }
        });
    }

    private void chargerPartieSelectionnee() {
        // Ajoutez la logique de chargement ici
        // Vous pouvez obtenir la partie sélectionnée depuis la liste déroulante
        String partieSelectionnee = (String) partieListeDeroulante.getSelectedItem();
        // Ajoutez votre logique de chargement ici en utilisant la partie sélectionnée
        System.out.println("Chargement de la partie : " + partieSelectionnee);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ChargementPartieFrame frame = new ChargementPartieFrame();
                frame.setSize(300, 150);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}