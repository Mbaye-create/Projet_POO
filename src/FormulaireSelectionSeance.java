import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class FormulaireSelectionSeance {

    static final String URL = "jdbc:mysql://localhost:3306/cahier_db";
    static final String USER = "root";
    static final String PASSWORD = "nabou";

    public static void afficherFormulaire(Runnable callbackApresSelection) {
        String[] colonnes = {
                "ID", "Date", "Heure Début", "Heure Fin", "Contenu"
        };

        DefaultTableModel model = new DefaultTableModel(colonnes, 0);

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "SELECT idSeance, date_seance, heure_debut, heure_fin, contenu FROM seance";
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

        JFrame frame = new JFrame("📝 Sélectionner une séance");
        frame.setSize(900, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(Color.WHITE);

        JLabel titre = new JLabel("📋 Choisissez une séance à utiliser", SwingConstants.CENTER);
        titre.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titre.setForeground(new Color(33, 37, 41));
        panel.add(titre, BorderLayout.NORTH);

        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(26);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(230, 240, 255));
        table.getTableHeader().setForeground(new Color(0, 102, 204));
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton boutonSelection = new JButton("✅ Sélectionner cette séance");
        boutonSelection.setFont(new Font("Segoe UI", Font.BOLD, 16));
        boutonSelection.setBackground(new Color(0, 153, 76));
        boutonSelection.setForeground(Color.WHITE);
        boutonSelection.setFocusPainted(false);

        boutonSelection.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int idSeance = (int) model.getValueAt(selectedRow, 0);
                String contenu = (String) model.getValueAt(selectedRow, 4);

                JOptionPane.showMessageDialog(frame,
                        "Séance sélectionnée : ID " + idSeance + "\nContenu : " + contenu,
                        "✅ Confirmation", JOptionPane.INFORMATION_MESSAGE);

                // Ici tu peux appeler une fonction avec l'ID sélectionné
                if (callbackApresSelection != null) {
                    callbackApresSelection.run();
                }

                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(frame, "❌ Veuillez sélectionner une ligne.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel panelBas = new JPanel();
        panelBas.setBackground(Color.WHITE);
        panelBas.add(boutonSelection);

        panel.add(panelBas, BorderLayout.SOUTH);

        frame.setContentPane(panel);
        frame.setVisible(true);
    }
}

