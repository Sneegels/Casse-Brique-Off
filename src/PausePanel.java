import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class PausePanel extends JPanel {
    private boolean enPause;

    public PausePanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(0, 0, 0, 150));

        JButton resumeButton = new JButton("Poursuivre la partie");
        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enPause = false;
                setVisible(false);
            }
        });

        JButton quitButton = new JButton("Quitter la partie");
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ajoutez ici toute logique pour quitter la partie
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(resumeButton);
        buttonPanel.add(quitButton);

        add(Box.createVerticalGlue(), BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(Box.createVerticalGlue(), BorderLayout.SOUTH);
    }
}