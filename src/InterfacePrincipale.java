import javax.swing.*;
import java.awt.*;

public class InterfacePrincipale {
    private static CardLayout cardLayout;
    private static JPanel panneauPrincipal;
    private static JFrame frame;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InterfacePrincipale::afficherConnexion);
    }

    public static void afficherConnexion() {
        frame = new JFrame("Cahier de Texte Numérique");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        panneauPrincipal = new JPanel(cardLayout);

        panneauPrincipal.add(creerPanneauConnexion(), "Connexion");
        panneauPrincipal.add(creerInterfaceChef(), "Chef");
        panneauPrincipal.add(creerInterfaceEnseignant(), "Enseignant");
        panneauPrincipal.add(creerInterfaceResponsable(), "Responsable");

        frame.setContentPane(panneauPrincipal);
        frame.setVisible(true);
    }

    private static JPanel creerPanneauConnexion() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 255, 250));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel titre = new JLabel("🔒 Connexion au Cahier de Texte", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 24));
        titre.setForeground(new Color(0, 123, 255));
        c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
        panel.add(titre, c);

        c.gridwidth = 1;
        c.gridx = 0; c.gridy = 1;
        panel.add(new JLabel("📧 Email :"), c);

        JTextField emailField = new JTextField(20);
        c.gridx = 1;
        panel.add(emailField, c);

        c.gridx = 0; c.gridy = 2;
        panel.add(new JLabel("🔑 Mot de passe :"), c);

        JPasswordField passwordField = new JPasswordField(20);
        c.gridx = 1;
        panel.add(passwordField, c);

        JButton btnConnexion = new JButton("Se connecter");
        btnConnexion.setBackground(new Color(50, 150, 250));
        btnConnexion.setForeground(Color.WHITE);
        btnConnexion.setFont(new Font("Arial", Font.BOLD, 14));
        btnConnexion.setFocusPainted(false);
        c.gridx = 0; c.gridy = 3; c.gridwidth = 2;
        panel.add(btnConnexion, c);

        btnConnexion.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String role = Main.verifierIdentifiants(email, password);

            if (role != null) {
                JOptionPane.showMessageDialog(frame, "✅ Connexion réussie en tant que " + role);
                cardLayout.show(panneauPrincipal, role);
            } else {
                JOptionPane.showMessageDialog(frame, "❌ Identifiants incorrects", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    private static JPanel creerInterfaceChef() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(255, 230, 250));

        JLabel titre = new JLabel("👩‍💼 Chef de Département", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 26));
        titre.setForeground(new Color(102, 0, 102));
        panel.add(titre, BorderLayout.NORTH);

        JPanel boutons = new JPanel(new GridLayout(2, 2, 20, 20));
        boutons.setBackground(panel.getBackground());

        boutons.add(creerBouton("👤 Ajouter un utilisateur", e -> new FormulaireAjoutUtilisateur().ouvrir()));
        boutons.add(creerBouton("📚 Assigner un cours", e -> new FormulaireAssignationCours().ouvrir()));
        boutons.add(creerBouton("📄 Générer fiche pédagogique", e -> new FormulaireGenerationFiche().ouvrir()));
        boutons.add(creerBouton("🗓️ Visualiser les séances", e -> new VisualisationSeances().afficherSeances()));
        boutons.add(creerBouton("📘 Voir les cours assignés", e -> VisualisationCours.afficherCours()));

        panel.add(boutons, BorderLayout.CENTER);
        panel.add(creerFooter(), BorderLayout.SOUTH);

        return panel;
    }

    private static JPanel creerInterfaceEnseignant() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(230, 245, 255));

        JLabel titre = new JLabel("👨‍🏫 Espace Enseignant", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 26));
        titre.setForeground(new Color(0, 66, 102));
        panel.add(titre, BorderLayout.NORTH);

        JPanel boutons = new JPanel(new GridLayout(3, 1, 20, 20));
        boutons.setBackground(panel.getBackground());

        boutons.add(creerBouton("📘 Voir mes cours", e -> new VisualisationCours().afficherCours()));
        boutons.add(creerBouton("➕ Ajouter une séance", e -> new FormulaireAjoutSeance().ouvrir()));
        boutons.add(creerBouton("🗓️ Voir mes séances", e -> new VisualisationSeances().afficherSeances()));

        panel.add(boutons, BorderLayout.CENTER);
        panel.add(creerFooter(), BorderLayout.SOUTH);

        return panel;
    }

    private static JPanel creerInterfaceResponsable() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(230, 255, 230));

        JLabel titre = new JLabel("📋 Responsable de Classe", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 24));
        titre.setForeground(new Color(0, 102, 51));
        panel.add(titre, BorderLayout.NORTH);

        JPanel boutons = new JPanel(new GridLayout(3, 1, 20, 20));
        boutons.setBackground(panel.getBackground());

        boutons.add(creerBouton("📖 Consulter les séances", e -> new VisualisationSeances().afficherSeances()));
        boutons.add(creerBouton("✅ Valider une séance", e -> new FormulaireValidationSeance().ouvrir()));
        boutons.add(creerBouton("🔍 Voir les validations", e -> new VisualisationValidation().afficherValidations()));

        panel.add(boutons, BorderLayout.CENTER);
        panel.add(creerFooter(), BorderLayout.SOUTH);

        return panel;
    }

    private static JButton creerBouton(String texte, java.awt.event.ActionListener action) {
        JButton bouton = new JButton(texte);
        bouton.setFont(new Font("Arial", Font.PLAIN, 16));
        bouton.setBackground(new Color(0, 123, 255));
        bouton.setForeground(Color.WHITE);
        bouton.setFocusPainted(false);
        bouton.setBorder(BorderFactory.createLineBorder(new Color(0, 66, 204), 2));
        bouton.addActionListener(action);
        return bouton;
    }

    private static JLabel creerFooter() {
        JLabel footer = new JLabel("© 2025 - Département Informatique - Université Iba Der Thiam de Thiès", SwingConstants.CENTER);
        footer.setFont(new Font("Arial", Font.PLAIN, 12));
        footer.setForeground(new Color(50, 50, 50));
        return footer;
    }
}
