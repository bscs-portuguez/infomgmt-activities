import javax.swing.*;
import java.awt.*;
import java.awt.print.*;
import java.sql.*;

public class IndivRecordFrame extends JFrame implements Printable {
    private final JPanel recordPanel;

    IndivRecordFrame(String username) {
        Elements element = new Elements();

        setSize(595, 842); // Approx. A4 size in pixels at 72 DPI
        setResizable(false);
        setLayout(null);
        setTitle("Individual Record");
        getContentPane().setBackground(element.beige);

        recordPanel = new JPanel(null);
        recordPanel.setBounds(50, 50, 495, 700); // add margins
        recordPanel.setBackground(Color.WHITE);

        JLabel title = new JLabel(username + "'s Record", SwingConstants.CENTER);
        title.setBounds(0, 20, 495, 40);
        title.setFont(element.h2Font);
        title.setForeground(Color.BLACK);

        JLabel uNameLabel = new JLabel();
        JLabel fNameLabel = new JLabel();
        JLabel mInitialLabel = new JLabel();
        JLabel lNameLabel = new JLabel();
        JLabel emailLabel = new JLabel();
        JLabel birthdayLabel = new JLabel();
        JLabel dateLabel = new JLabel();

        JLabel[] labels = {
                uNameLabel, fNameLabel, mInitialLabel,
                lNameLabel, emailLabel, birthdayLabel, dateLabel
        };

        int y = 80;
        for (JLabel label : labels) {
            label.setBounds(30, y, 435, 40);
            label.setFont(element.pFont);
            label.setForeground(Color.BLACK);
            recordPanel.add(label);
            y += 50;
        }

        try {
            Connection conn = DatabaseHelper.getConnection();
            String query = "SELECT * FROM portuguez WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                uNameLabel.setText("Username: " + rs.getString("username"));
                fNameLabel.setText("First Name: " + rs.getString("first_name"));
                mInitialLabel.setText("Middle Initial: " + rs.getString("middle_initial"));
                lNameLabel.setText("Last Name: " + rs.getString("last_name"));
                emailLabel.setText("Email: " + rs.getString("email"));
                birthdayLabel.setText("Birthday: " + rs.getString("birthday"));
                dateLabel.setText("Registered on: " + rs.getString("date_registered"));
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to load user: " + e.getMessage());
            e.printStackTrace();
        }

        JButton printButton = new JButton("Print");
        printButton.setFont(element.h2Font);
        printButton.setBounds(240, 770, 100, 30);
        printButton.setBackground(element.red);
        printButton.setForeground(Color.WHITE);
        printButton.addActionListener(e -> {
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

        recordPanel.add(title);
        add(recordPanel);
        add(printButton);
        setVisible(true);
    }

    @Override
    public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
        if (pageIndex > 0) return NO_SUCH_PAGE;

        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());
        recordPanel.printAll(g);

        return PAGE_EXISTS;
    }
}
