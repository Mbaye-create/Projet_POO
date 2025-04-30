import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class VisualisationValidation {

    public static void afficherValidations() {
        JFrame frame = new JFrame("✅ Visualisation des Validations");
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(250, 255, 245));

        JLabel titre = new JLabel("📋 Liste des Validations de Séances", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 22));
        titre.setForeground(new Color(34, 139, 34));
        titre.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.add(titre, BorderLayout.NORTH);

        // En-têtes des colonnes
        String[] colonnes = {"ID Validation", "ID Séance", "Date Séance", "Heure", "Contenu"};

        // Création du modèle de table vide
        DefaultTableModel model = new DefaultTableModel(colonnes, 0);
        JTable table = new JTable(model);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        table.setGridColor(new Color(200, 200, 200));

        // Chargement des données depuis la base de données
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cahier_db", "root", "nabou");
             Statement stmt = con.createStatement()) {

            String query = """
                SELECT v.idValidation, s.idSeance, s.date_seance, CONCAT(s.heure_debut, ' - ', s.heure_fin) AS horaire, s.contenu
                FROM Validation v
                JOIN Seance s ON v.seance_id = s.idSeance
                ORDER BY v.idValidation DESC
            """;

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Object[] ligne = {
                        rs.getInt("idValidation"),
                        rs.getInt("idSeance"),
                        rs.getString("date_seance"),
                        rs.getString("horaire"),
                        rs.getString("contenu")
                };
                model.addRow(ligne);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "❌ Erreur SQL : " + e.getMessage());
        }

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        frame.setContentPane(panel);
        frame.setVisible(true);
    }
}
