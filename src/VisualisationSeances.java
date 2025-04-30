import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class VisualisationSeances {

    static final String URL = "jdbc:mysql://localhost:3306/cahier_db";
    static final String USER = "root";
    static final String PASSWORD = "nabou";

    public static void afficherSeances() {
        String[] colonnes = {
                "ID Séance", "Date", "Heure Début", "Heure Fin", "Contenu"
        };

        DefaultTableModel model = new DefaultTableModel(colonnes, 0);

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = """
                SELECT idSeance, date_seance, heure_debut, heure_fin, contenu
                FROM seance
            """;

            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Vector<Object> ligne = new Vector<>();
                ligne.add(rs.getInt("idSeance"));
                ligne.add(rs.getDate("date_seance"));
                ligne.add(rs.getString("heure_debut"));
                ligne.add(rs.getString("heure_fin"));
                ligne.add(rs.getString("contenu"));
                model.addRow(ligne);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur : " + e.getMessage(), "Erreur BD", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        JFrame frame = new JFrame("📚 Visualisation des séances");
        frame.setSize(900, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.white);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(210, 230, 255));
        table.getTableHeader().setForeground(new Color(0, 85, 170));
        table.getTableHeader().setPreferredSize(new Dimension(100, 30));

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JLabel titre = new JLabel("📋 Liste des séances enregistrées", SwingConstants.CENTER);
        titre.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titre.setForeground(new Color(33, 37, 41));
        titre.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        panel.add(titre, BorderLayout.NORTH);

        frame.setContentPane(panel);
        frame.setVisible(true);
    }
}
