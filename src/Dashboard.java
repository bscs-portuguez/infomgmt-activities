import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Dashboard extends JFrame {
    Dashboard() {
        Elements element = new Elements();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 500);
        setResizable(false);
        setLayout(null);
        setTitle("Dashboard");
        getContentPane().setBackground(element.red);

        JLabel welcomeLbl = new JLabel();
        welcomeLbl.setText("Welcome, Admin!");
        welcomeLbl.setFont(element.h1Font);
        welcomeLbl.setBounds(20, 25, 465, 50);
        welcomeLbl.setForeground(element.yellow);

        JPanel usersPanel = new JPanel();
        usersPanel.setBackground(element.red);
        usersPanel.setLayout(null); // Keep null layout for absolute positioning if needed

        JScrollPane scrollPane = new JScrollPane(usersPanel);
        scrollPane.setBounds(20, 90, 480, 300); // Adjusted height for scrolling
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // smoother scrolling
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JLabel usersLabel = new JLabel();
        usersLabel.setText("Users:");
        usersLabel.setFont(element.h2Font);
        usersLabel.setBounds(0, 0, 465, 30);
        usersLabel.setForeground(Color.white);
        usersPanel.add(usersLabel);

        try {
            Connection conn = DatabaseHelper.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT username, date_registered FROM portuguez ORDER BY date_registered DESC");

            int yOffset = 30;
            int count = 1;

            while (rs.next()) {
                String username = rs.getString("username");
                String dateRegistered = rs.getString("date_registered");

                JLabel userCount = new JLabel(count + ".");
                userCount.setFont(element.pFont);
                userCount.setBounds(0, yOffset, 30, 30);
                userCount.setForeground(Color.white);

                JLabel userName = new JLabel(username);
                userName.setFont(element.pFont);
                userName.setBounds(30, yOffset, 160, 30);
                userName.setForeground(Color.white);

                JLabel userDateTime = new JLabel(dateRegistered);
                userDateTime.setFont(element.pFont);
                userDateTime.setBounds(200, yOffset, 200, 30);
                userDateTime.setForeground(Color.white);

                JButton viewBtn = new JButton();
                viewBtn.setBounds(380, yOffset +5, 80, 20);
                viewBtn.setText("View");
                viewBtn.setBackground(element.yellow);
                viewBtn.setFont(element.pFont);
                viewBtn.addActionListener(e -> {
                    IndivRecordFrame recordFrame = new IndivRecordFrame(username);
                });

                usersPanel.add(userCount);
                usersPanel.add(userName);
                usersPanel.add(userDateTime);
                usersPanel.add(viewBtn);

                yOffset += 30;
                count++;
            }
            usersPanel.setPreferredSize(new Dimension(465, yOffset));

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to load users: " + e.getMessage());
            e.printStackTrace();
        }

        JButton sumOfRecordsBtn = new JButton();
        sumOfRecordsBtn.setBounds(20, 420, 230, 40);
        sumOfRecordsBtn.setText("Summary of Records");
        sumOfRecordsBtn.setBackground(Color.GREEN);
        sumOfRecordsBtn.setFont(element.pFont);
        sumOfRecordsBtn.setForeground(Color.BLACK);
        sumOfRecordsBtn.addActionListener(e -> {
            new SumRecordsFrame();
        });

        JButton logoutBtn = new JButton();
        logoutBtn.setBounds(365, 420, 100, 40);
        logoutBtn.setText("Logout");
        logoutBtn.setBackground(element.red);
        logoutBtn.setFont(element.pFont);
        logoutBtn.setForeground(element.beige);
        logoutBtn.addActionListener(e -> {
            new LoginFrame();
            dispose();
        });

        add(sumOfRecordsBtn);
        add(scrollPane);
        add(logoutBtn);
        add(welcomeLbl);
        setVisible(true);
    }
}
