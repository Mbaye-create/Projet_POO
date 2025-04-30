import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ListeEtModificationCours {

    static final String URL = "jdbc:mysql://localhost:3306/cahier_db";
    static final String USER = "root";
    static final String PASSWORD = "nabou";

    public static void ouvrir() {
        JFrame frame = new JFrame("📚 Liste des cours");
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.white);

        JLabel titre = new JLabel("📋 Cours enregistrés", SwingConstants.CENTER);
        titre.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titre.setForeground(new Color(33, 37, 41));
        panel.add(titre, BorderLayout.NORTH);

        String[] colonnes = {"ID", "Intitulé", "Niveau", "Semestre", "Volume Horaire"};
        DefaultTableModel model = new DefaultTableModel(colonnes, 0);
        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "SELECT * FROM cours";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("idCours"),
                        rs.getString("intitule"),
                        rs.getString("niveau"),
                        rs.getString("semestre"),
                        rs.getInt("volume_horaire")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "❌ Erreur chargement : " + e.getMessage());
            return;
        }

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton btnModifier = new JButton("💾 Mettre à jour");
        btnModifier.setBackground(new Color(0, 153, 76));
        btnModifier.setForeground(Color.WHITE);
        btnModifier.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnModifier.setFocusPainted(false);

        btnModifier.addActionListener(e -> {
            int ligne = table.getSelectedRow();
            if (ligne == -1) {
                JOptionPane.showMessageDialog(frame, "Veuillez sélectionner un cours à modifier.");
                return;
            }

            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                int idCours = (int) model.getValueAt(ligne, 0);
                String intitule = (String) model.getValueAt(ligne, 1);
                String niveau = (String) model.getValueAt(ligne, 2);
                String semestre = (String) model.getValueAt(ligne, 3);
                int volume = Integer.parseInt(model.getValueAt(ligne, 4).toString());

                String update = "UPDATE cours SET intitule = ?, niveau = ?, semestre = ?, volume_horaire = ? WHERE idCours = ?";
                PreparedStatement stmt = conn.prepareStatement(update);
                stmt.setString(1, intitule);
                stmt.setString(2, niveau);
                stmt.setString(3, semestre);
                stmt.setInt(4, volume);
                stmt.setInt(5, idCours);

                int updated = stmt.executeUpdate();
                if (updated > 0) {
                    JOptionPane.showMessageDialog(frame, "✅ Cours mis à jour avec succès !");
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "❌ Erreur mise à jour : " + ex.getMessage());
            }
        });

        JPanel bas = new JPanel();
        bas.setBackground(Color.white);
        bas.add(btnModifier);
        panel.add(bas, BorderLayout.SOUTH);

        frame.setContentPane(panel);
        frame.setVisible(true);
    }
}

