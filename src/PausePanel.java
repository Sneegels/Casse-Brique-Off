import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.CompoundBorder;

class PausePanel extends JPanel {
    private JButton resumeButton;
    private JButton quitButton;
    private CasseBriquePanel casseBriquePanel;

    public PausePanel(CasseBriquePanel casseBriquePanel) {
        setLayout(new BorderLayout());
        setBackground(new Color(0, 0, 0, 150));

        resumeButton = createButton("Reprendre la partie");
        quitButton = createButton("Quitter la partie");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(2, 1, 0, 10)); // 2 lignes, 1 colonne, espacement vertical de 10 pixels
        buttonPanel.add(resumeButton);
        buttonPanel.add(quitButton);

        add(Box.createVerticalGlue(), BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(Box.createVerticalGlue(), BorderLayout.SOUTH);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);

        Border line = new LineBorder(Color.WHITE);
        Border margin = new javax.swing.border.EmptyBorder(5, 15, 5, 15);
        Border compound = new CompoundBorder(line, margin);
        button.setBorder(compound);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setForeground(Color.RED); // Change la couleur lorsque la souris survole le bouton
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setForeground(Color.WHITE); // Revert à la couleur initiale quand la souris quitte le bouton
            }
        });

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (text.equals("Reprendre la partie")) {
                    casseBriquePanel.reprendrePartie();
                } else if (text.equals("Quitter la partie")) {
                    System.exit(0);
                }
            }
        });

        return button;
    }

    public JButton getResumeButton() {
        return resumeButton;
    }
}
