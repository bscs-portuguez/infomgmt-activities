import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.sql.*;

public class SumRecordsFrame extends JFrame {
    SumRecordsFrame() {
        Elements element = new Elements();

        setSize(1300, 800);
        setResizable(false);
        setLayout(null);
        setTitle("Summary of Records");
        getContentPane().setBackground(element.beige);

        JLabel title = new JLabel();
        title.setBounds(0, 30, 1285, 50);
        title.setFont(element.h1Font);
        title.setText("Summary of Records");
        title.setForeground(Color.BLACK);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title);

        String[] columnNames = {
                "ID", "Username", "First Name", "M.I.", "Last Name",
                "Email", "Birthday", "Date Registered", "Role"
        };

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(element.pFont);
        table.getTableHeader().setFont(element.h2Font);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setEnabled(false);

        try {
            Connection conn = DatabaseHelper.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, username, first_name, middle_initial, last_name, email, birthday, date_registered, role FROM portuguez");

            while (rs.next()) {
                Object[] rowData = {
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("first_name"),
                        rs.getString("middle_initial"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("birthday"),
                        rs.getString("date_registered"),
                        rs.getString("role")
                };
                model.addRow(rowData);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading records: " + e.getMessage());
            e.printStackTrace();
        }

        // Set preferred column widths
        TableColumn colID = table.getColumnModel().getColumn(0);    // ID
        TableColumn colMI = table.getColumnModel().getColumn(3);    // M.I.
        TableColumn colRole = table.getColumnModel().getColumn(8);  // Role

        colID.setPreferredWidth(40);
        colMI.setPreferredWidth(40);
        colRole.setPreferredWidth(70);

        // Optional: Set reasonable widths for others
        table.getColumnModel().getColumn(1).setPreferredWidth(180); // Username
        table.getColumnModel().getColumn(2).setPreferredWidth(150); // First Name
        table.getColumnModel().getColumn(4).setPreferredWidth(120); // Last Name
        table.getColumnModel().getColumn(5).setPreferredWidth(295); // Email
        table.getColumnModel().getColumn(6).setPreferredWidth(120); // Birthday
        table.getColumnModel().getColumn(7).setPreferredWidth(184); // Date Registered

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 100, 1200, 600);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        add(scrollPane);
        setVisible(true);
    }
}
