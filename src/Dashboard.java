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
        usersPanel.setBounds(20, 90, 465, 300);
        usersPanel.setBackground(element.red);
        usersPanel.setLayout(null);

        JLabel usersLabel = new JLabel();
        usersLabel.setText("Users:");
        usersLabel.setFont(element.h2Font);
        usersLabel.setBounds(0, 0, 465, 30);
        usersLabel.setForeground(Color.white);
        usersPanel.add(usersLabel);

        try {
            Connection conn = DatabaseHelper.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT username, date_registered FROM users ORDER BY date_registered DESC");

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

                usersPanel.add(userCount);
                usersPanel.add(userName);
                usersPanel.add(userDateTime);

                yOffset += 30;
                count++;
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to load users: " + e.getMessage());
            e.printStackTrace();
        }

        JButton logoutBtn = new JButton();
        logoutBtn.setBounds(365, 400, 100, 40);
        logoutBtn.setText("Logout");
        logoutBtn.setBackground(element.red);
        logoutBtn.setFont(element.h2Font);
        logoutBtn.setForeground(element.beige);
        logoutBtn.addActionListener(e -> {
            new LoginFrame();
            dispose();
        });

        add(usersPanel);
        add(logoutBtn);
        add(welcomeLbl);
        setVisible(true);
    }
}
