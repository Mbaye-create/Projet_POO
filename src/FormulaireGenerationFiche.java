import javax.swing.*;
import java.awt.*;

public class FormulaireGenerationFiche {

    public static void ouvrir() {
        JFrame frame = new JFrame("📄 Générer Fiche Pédagogique");
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField email = new JTextField();
        JTextField intitule = new JTextField();

        panel.add(new JLabel("Email de l'enseignant :"));
        panel.add(email);
        panel.add(new JLabel("Cours :"));
        panel.add(intitule);

        JButton btnGenerer = new JButton("Générer");
        btnGenerer.addActionListener(e -> {
            // TODO: Générer un PDF ou une fiche avec les séances correspondantes
            JOptionPane.showMessageDialog(frame, "📄 Fiche générée avec succès !");
            frame.dispose();
        });

        panel.add(new JLabel());
        panel.add(btnGenerer);

        frame.setContentPane(panel);
        frame.setVisible(true);
    }
}
