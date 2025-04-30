import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class VisualisationCours {

    static final String URL = "jdbc:mysql://localhost:3306/cahier_db";
    static final String USER = "root";
    static final String PASSWORD = "nabou";

    public static void afficherCours() {
        String[] colonnes = {"ID", "Intitulé", "Niveau", "Semestre", "Volume Horaire", "Enseignant"};

        DefaultTableModel model = new DefaultTableModel(colonnes, 0);

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "SELECT * FROM cours";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Vector<Object> ligne = new Vector<>();
                ligne.add(rs.getInt("idCours"));
                ligne.add(rs.getString("intitule"));
                ligne.add(rs.getString("niveau"));
                ligne.add(rs.getString("semestre"));
                ligne.add(rs.getInt("volume_horaire"));

                model.addRow(ligne); // ✅ Ajout de la ligne au modèle
            }


        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur : " + e.getMessage(), "Erreur BD", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        JFrame frame = new JFrame("📘 Cours assignés");
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(26);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.add(new JLabel("📋 Liste des cours assignés", SwingConstants.CENTER), BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        frame.setContentPane(panel);
        frame.setVisible(true);
    }
}
