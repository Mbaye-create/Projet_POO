import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class FormulaireValidationSeance {

    private JFrame frame;
    private JComboBox<String> comboBoxSeances;
    private JButton validerButton;

    public void ouvrir() {
        frame = new JFrame("Validation de Séance");
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        JLabel titre = new JLabel("✅ Valider une séance", JLabel.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 20));
        frame.add(titre, BorderLayout.NORTH);

        JPanel panelCentre = new JPanel(new BorderLayout(10, 10));
        panelCentre.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        comboBoxSeances = new JComboBox<>();
        chargerSeances();

        panelCentre.add(new JLabel("📘 Séance à valider :"), BorderLayout.NORTH);
        panelCentre.add(comboBoxSeances, BorderLayout.CENTER);

        validerButton = new JButton("✅ Valider la séance");
        validerButton.setBackground(new Color(0, 150, 136));
        validerButton.setForeground(Color.WHITE);
        validerButton.setFont(new Font("Arial", Font.BOLD, 14));
        validerButton.addActionListener(this::validerSeance);
        panelCentre.add(validerButton, BorderLayout.SOUTH);

        frame.add(panelCentre, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void chargerSeances() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cahier_db", "root", "nabou");
             Statement stmt = con.createStatement()) {

            String query = "SELECT idSeance, date_seance, heure_debut, heure_fin, contenu FROM Seance";
            ResultSet rs = stmt.executeQuery(query);

            boolean aDesSeances = false;

            while (rs.next()) {
                int id = rs.getInt("idSeance");
                String date = rs.getString("date_seance");
                String debut = rs.getString("heure_debut");
                String fin = rs.getString("heure_fin");
                String contenu = rs.getString("contenu");

                String texte = String.format("ID:%d | %s (%s-%s) - %s", id, date, debut, fin, contenu);
                comboBoxSeances.addItem(texte);
                aDesSeances = true;
            }

            if (!aDesSeances) {
                JOptionPane.showMessageDialog(frame, "⚠️ Aucune séance trouvée dans la base de données.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "❌ Erreur lors du chargement des séances : " + e.getMessage(), "Erreur SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void validerSeance(ActionEvent e) {
        String selected = (String) comboBoxSeances.getSelectedItem();

        if (selected == null) {
            JOptionPane.showMessageDialog(frame, "⚠️ Veuillez sélectionner une séance.");
            return;
        }

        try {
            int seance_id = Integer.parseInt(selected.split(" ")[0].split(":")[1]); // récupère ID:123 → 123

            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cahier_db", "root", "nabou");
                 PreparedStatement pst = con.prepareStatement("INSERT INTO Validation (seance_id) VALUES (?)")) {

                pst.setInt(1, seance_id);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(frame, "✅ Séance validée avec succès !");
                frame.dispose();

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "❌ Erreur SQL lors de la validation : " + ex.getMessage());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "⚠️ Erreur lors de la récupération de l'identifiant de la séance.");
        }
    }
}