import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class FormulaireAjoutUtilisateur {

    static final String URL = "jdbc:mysql://localhost:3306/cahier_db";
    static final String USER = "root";
    static final String PASSWORD = "nabou";

    public static void ouvrir() {
        JFrame frame = new JFrame("👤 Ajouter un utilisateur");
        frame.setSize(500, 350);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 250, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel titre = new JLabel("➕ Formulaire d’ajout d’utilisateur");
        titre.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titre.setForeground(new Color(0, 85, 160));
        c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
        panel.add(titre, c);

        c.gridwidth = 1;

        JLabel lblNom = new JLabel("Nom complet :");
        JTextField champNom = new JTextField(20);
        c.gridx = 0; c.gridy = 1;
        panel.add(lblNom, c);
        c.gridx = 1;
        panel.add(champNom, c);

        JLabel lblEmail = new JLabel("Adresse email :");
        JTextField champEmail = new JTextField(20);
        c.gridx = 0; c.gridy = 2;
        panel.add(lblEmail, c);
        c.gridx = 1;
        panel.add(champEmail, c);

        JLabel lblMotDePasse = new JLabel("Mot de passe :");
        JPasswordField champMotDePasse = new JPasswordField(20);
        c.gridx = 0; c.gridy = 3;
        panel.add(lblMotDePasse, c);
        c.gridx = 1;
        panel.add(champMotDePasse, c);

        JLabel lblRole = new JLabel("Rôle :");
        String[] roles = {"Enseignant", "Responsable"};
        JComboBox<String> comboRole = new JComboBox<>(roles);
        c.gridx = 0; c.gridy = 4;
        panel.add(lblRole, c);
        c.gridx = 1;
        panel.add(comboRole, c);

        JButton btnAjouter = new JButton("✅ Ajouter");
        btnAjouter.setBackground(new Color(0, 153, 76));
        btnAjouter.setForeground(Color.WHITE);
        btnAjouter.setFont(new Font("Segoe UI", Font.BOLD, 14));
        c.gridx = 0; c.gridy = 5; c.gridwidth = 2;
        panel.add(btnAjouter, c);

        btnAjouter.addActionListener(e -> {
            String nom = champNom.getText();
            String email = champEmail.getText();
            String mdp = new String(champMotDePasse.getPassword());
            String role = (String) comboRole.getSelectedItem();

            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                String sql = "INSERT INTO utilisateur(nom, email, mot_de_passe, role) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, nom);
                stmt.setString(2, email);
                stmt.setString(3, mdp);
                stmt.setString(4, role);
                stmt.executeUpdate();

                JOptionPane.showMessageDialog(frame, "🎉 Utilisateur ajouté avec succès !");
                frame.dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "❌ Erreur : " + ex.getMessage(), "Erreur BD", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        frame.setContentPane(panel);
        frame.setVisible(true);
    }
}
