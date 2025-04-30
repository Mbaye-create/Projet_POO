import javax.swing.*;
import java.awt.*;

public class FormulaireAjoutSeance {

    public static void ouvrir() {
        JFrame frame = new JFrame("Ajouter une séance");
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JTextField champDate = new JTextField();
        JTextField champHeure = new JTextField();
        JTextField champContenu = new JTextField();
        JTextField champCours = new JTextField(); // Ou JComboBox si tu veux les cours dynamiques

        panel.add(new JLabel("📅 Date (YYYY-MM-DD):"));
        panel.add(champDate);

        panel.add(new JLabel("⏰ Heure:"));
        panel.add(champHeure);

        panel.add(new JLabel("📘 Contenu:"));
        panel.add(champContenu);

        panel.add(new JLabel("📚 Cours:"));
        panel.add(champCours);

        JButton btnValider = new JButton("✅ Ajouter");
        btnValider.addActionListener(e -> {
            String date = champDate.getText();
            String heure = champHeure.getText();
            String contenu = champContenu.getText();
            String cours = champCours.getText();

            // 🔗 Ici tu peux insérer dans la base de données via JDBC
            // Par exemple :
            // SeanceDAO.ajouterNouvelleSeance(date, heure, contenu, cours, idEnseignant);

            JOptionPane.showMessageDialog(frame, "✅ Séance ajoutée avec succès !");
            frame.dispose();
        });

        panel.add(new JLabel()); // Espace vide
        panel.add(btnValider);

        frame.add(panel);
        frame.setVisible(true);
    }
}
