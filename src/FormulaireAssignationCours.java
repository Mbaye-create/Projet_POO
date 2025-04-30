import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class FormulaireAssignationCours {

    static final String URL = "jdbc:mysql://localhost:3306/cahier_db";
    static final String USER = "root";
    static final String PASSWORD = "nabou";

    public static void ouvrir() {
        JFrame frame = new JFrame("📚 Assigner un cours");
        frame.setSize(500, 320);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(240, 248, 255));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel titre = new JLabel("📝 Formulaire d’assignation de cours", SwingConstants.CENTER);
        titre.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titre.setForeground(new Color(33, 37, 41));
        c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
        panel.add(titre, c);

        JTextField champIntitule = new JTextField();
        JTextField champNiveau = new JTextField();
        JTextField champSemestre = new JTextField();
        JTextField champVolume = new JTextField();

        JLabel lblIntitule = new JLabel("📘 Intitulé du cours :");
        JLabel lblNiveau = new JLabel("🎓 Niveau :");
        JLabel lblSemestre = new JLabel("🗓️ Semestre :");
        JLabel lblVolume = new JLabel("⏱ Volume horaire :");

        lblIntitule.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblNiveau.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSemestre.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblVolume.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        c.gridwidth = 1;

        c.gridx = 0; c.gridy = 1; panel.add(lblIntitule, c);
        c.gridx = 1; panel.add(champIntitule, c);

        c.gridx = 0; c.gridy = 2; panel.add(lblNiveau, c);
        c.gridx = 1; panel.add(champNiveau, c);

        c.gridx = 0; c.gridy = 3; panel.add(lblSemestre, c);
        c.gridx = 1; panel.add(champSemestre, c);

        c.gridx = 0; c.gridy = 4; panel.add(lblVolume, c);
        c.gridx = 1; panel.add(champVolume, c);

        JButton btnAssigner = new JButton("✅ Assigner le cours");
        btnAssigner.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAssigner.setBackground(new Color(0, 153, 76));
        btnAssigner.setForeground(Color.WHITE);
        btnAssigner.setFocusPainted(false);

        c.gridx = 0; c.gridy = 5; c.gridwidth = 2;
        panel.add(btnAssigner, c);

        btnAssigner.addActionListener(e -> {
            String intitule = champIntitule.getText();
            String niveau = champNiveau.getText();
            String semestre = champSemestre.getText();
            String volumeHoraire = champVolume.getText();

            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                String sql = "INSERT INTO cours (intitule, niveau, semestre, volume_horaire) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, intitule);
                stmt.setString(2, niveau);
                stmt.setString(3, semestre);
                stmt.setInt(4, Integer.parseInt(volumeHoraire));

                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(frame, "✅ Cours assigné avec succès !");
                    frame.dispose();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "❌ Erreur lors de l'insertion : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        frame.setContentPane(panel);
        frame.setVisible(true);
    }
}
