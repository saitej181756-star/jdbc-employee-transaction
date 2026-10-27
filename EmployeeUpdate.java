import java.sql.*;

public class EmployeeUpdate {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/company";
        String user = "root";
        String password = "1234";

        String oldDept = "IT";
        String newDept = "Finance";

        Connection con = null;

        try {
            con = DriverManager.getConnection(url, user, password);
            con.setAutoCommit(false);

            PreparedStatement sel = con.prepareStatement(
                "SELECT emp_id, emp_name, department FROM employees WHERE department = ?");
            sel.setString(1, oldDept);
            ResultSet rs = sel.executeQuery();

            System.out.println("Employees in " + oldDept + " department:");
            while (rs.next()) {
                System.out.println(rs.getInt("emp_id") + " "
                    + rs.getString("emp_name") + " "
                    + rs.getString("department"));
            }

            PreparedStatement upd = con.prepareStatement(
                "UPDATE employees SET department = ? WHERE department = ?");
            upd.setString(1, newDept);
            upd.setString(2, oldDept);
            int rows = upd.executeUpdate();
            System.out.println(rows + " employees updated.");

            con.commit();
            System.out.println("Transaction committed successfully.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            try {
                if (con != null) {
                    con.rollback();
                    System.out.println("Transaction rolled back.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}