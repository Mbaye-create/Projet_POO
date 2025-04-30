import java.sql.*;

public class Main {
    private static final String URL = "jdbc:mysql://localhost:3306/cahier_db";
    private static final String USER = "root";
    private static final String PASSWORD = "nabou";

    public static Connection connecter() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String verifierIdentifiants(String email, String motDePasse) {
        String role = null;
        try (Connection conn = connecter()) {
            String sql = "SELECT role FROM Utilisateur WHERE email = ? AND mot_de_passe = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, motDePasse);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                role = rs.getString("role");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return role;
    }
}
