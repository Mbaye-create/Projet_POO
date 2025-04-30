import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.io.*;
import java.util.Vector;
import java.io.File;

public class VisualisationFichePedagogique {

    static final String URL = "jdbc:mysql://localhost:3306/cahier_db";
    static final String USER = "root";
    static final String PASSWORD = "nabou";

    public static void afficherFiches() {
        JFrame frame = new JFrame("📄 Fiches pédagogiques");
        frame.setSize(900, 450);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] colonnes = {"ID Fiche", "ID Enseignant", "Date de génération", "Chemin d'export"};
        DefaultTableModel model = new DefaultTableModel(colonnes, 0);

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "SELECT * FROM fichepedagogique";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Vector<Object> ligne = new Vector<>();
                ligne.add(rs.getInt("idFichePedagogique"));
                ligne.add(rs.getInt("enseignantId"));
                ligne.add(rs.getDate("date_generation"));
                ligne.add(rs.getString("chemin_export"));
                model.addRow(ligne);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erreur lors du chargement des fiches : " + ex.getMessage());
            return;
        }

        JTable table = new JTable(model);
        styliserTable(table);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.white);
        JLabel titre = new JLabel("📄 Liste des fiches pédagogiques", SwingConstants.CENTER);
        styliserTitre(titre);

        panel.add(titre, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        frame.setContentPane(panel);
        frame.setVisible(true);
    }

    private static void styliserTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(210, 230, 255));
        table.getTableHeader().setForeground(new Color(0, 85, 170));
    }

    private static void styliserTitre(JLabel label) {
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        label.setForeground(new Color(33, 37, 41));
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
    }

    // Méthode pour générer la fiche et l'enregistrer
    public static void genererFiche(int idEnseignant) {
        // Créer la fiche pédagogique (exemple simplifié)
        String cheminFichierPDF = "C:/fiches/fiche_ens" + idEnseignant + ".pdf"; // Exemple de chemin

        // Simulation de la génération de la fiche PDF
        // Normalement, ici, tu génères un fichier PDF avec des outils comme iText ou Apache PDFBox
        try {
            // Enregistrer la fiche dans la base de données
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                String insertQuery = "INSERT INTO fichepedagogique (enseignantId, date_generation, chemin_export) VALUES (?, NOW(), ?)";
                PreparedStatement ps = conn.prepareStatement(insertQuery);
                ps.setInt(1, idEnseignant);
                ps.setString(2, cheminFichierPDF);
                ps.executeUpdate();
            }

            // Afficher un message de succès
            JOptionPane.showMessageDialog(null, "✅ Fiche générée avec succès !\nFichier : " + cheminFichierPDF, "Succès", JOptionPane.INFORMATION_MESSAGE);

            // Demander si l'utilisateur veut ouvrir le fichier
            int choix = JOptionPane.showConfirmDialog(null, "Souhaitez-vous ouvrir la fiche ?", "Ouvrir le fichier", JOptionPane.YES_NO_OPTION);
            if (choix == JOptionPane.YES_OPTION) {
                File fichier = new File(cheminFichierPDF);
                if (fichier.exists()) {
                    Desktop.getDesktop().open(fichier);  // Ouvrir le fichier PDF
                } else {
                    JOptionPane.showMessageDialog(null, "Le fichier n'existe pas.");
                }
            }

            // Rafraîchir la liste des fiches
            afficherFiches();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la génération de la fiche : " + e.getMessage());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erreur lors de l'ouverture du fichier : " + e.getMessage());
        }
    }
}
