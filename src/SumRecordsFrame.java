import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.print.*;
import java.sql.*;

public class SumRecordsFrame extends JFrame implements Printable {
    private JPanel printablePanel;

    SumRecordsFrame() {
        Elements element = new Elements();

        setSize(900, 650);
        setResizable(false);
        setLayout(null);
        setTitle("Summary of Records");
        getContentPane().setBackground(element.beige);

        // Printable content panel
        printablePanel = new JPanel(null);
        printablePanel.setBackground(Color.WHITE);
        printablePanel.setBounds(30, 30, 842, 595); // Approx. A4 landscape
        add(printablePanel);

        JLabel title = new JLabel("Summary of Records", SwingConstants.CENTER);
        title.setFont(element.h1Font);
        title.setForeground(Color.BLACK);
        title.setBounds(0, 20, 842, 40);
        printablePanel.add(title);

        String[] columnNames = {
                "ID", "Username", "First Name", "M.I.", "Last Name",
                "Email", "Birthday", "Date Registered", "Role"
        };

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        table.setRowHeight(24);
        table.setFont(new Font("SansSerif", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
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

        // Set column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(30);  // ID
        table.getColumnModel().getColumn(1).setPreferredWidth(120); // Username
        table.getColumnModel().getColumn(2).setPreferredWidth(90); // First Name
        table.getColumnModel().getColumn(3).setPreferredWidth(30);  // M.I.
        table.getColumnModel().getColumn(4).setPreferredWidth(80); // Last Name
        table.getColumnModel().getColumn(5).setPreferredWidth(197); // Email
        table.getColumnModel().getColumn(6).setPreferredWidth(80);  // Birthday
        table.getColumnModel().getColumn(7).setPreferredWidth(120); // Date Registered
        table.getColumnModel().getColumn(8).setPreferredWidth(50);  // Role

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 80, 800, 470);
        printablePanel.add(scrollPane);

        JButton printBtn = new JButton("Print");
        printBtn.setFont(element.h2Font);
        printBtn.setBounds(750, 590, 80, 30);
        printBtn.setBackground(element.red);
        printBtn.setForeground(element.beige);
        printBtn.addActionListener(e -> {
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setJobName("Print");

            job.setPrintable(this);

            boolean accepted = job.printDialog();
            if (accepted) {
                try {
                    job.print();
                } catch (PrinterException ex) {
                    ex.printStackTrace();
                }
            }
        });

        add(printBtn);
        setVisible(true);
    }

    @Override
    public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
        if (pageIndex > 0) return NO_SUCH_PAGE;

        Graphics2D g2 = (Graphics2D) g;
        g2.translate(pf.getImageableX(), pf.getImageableY());
        g2.scale(0.75, 0.75);

        printablePanel.printAll(g2);
        return PAGE_EXISTS;
    }
}
