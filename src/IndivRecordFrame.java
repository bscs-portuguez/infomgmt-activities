import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class IndivRecordFrame extends JFrame{
    IndivRecordFrame(String username) {
        Elements element = new Elements();

        setSize(500, 500);
        setResizable(false);
        setLayout(null);
        setTitle("Individual Record");
        getContentPane().setBackground(element.beige);

        JLabel title = new JLabel();
        title.setBounds(0, 30, 485, 50);
        title.setFont(element.h2Font);
        title.setText(username + "'s Record");
        title.setForeground(Color.BLACK);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel uNameLabel = new JLabel();
        uNameLabel.setBounds(20, 90, 485, 50);
        uNameLabel.setFont(element.pFont);
        uNameLabel.setForeground(Color.BLACK);

        JLabel fNameLabel = new JLabel();
        fNameLabel.setBounds(20, 140, 485, 50);
        fNameLabel.setFont(element.pFont);
        fNameLabel.setForeground(Color.BLACK);

        JLabel mInitialLabel = new JLabel();
        mInitialLabel.setBounds(20, 190, 485, 50);
        mInitialLabel.setFont(element.pFont);
        mInitialLabel.setForeground(Color.BLACK);

        JLabel lNameLabel = new JLabel();
        lNameLabel.setBounds(20, 240, 485, 50);
        lNameLabel.setFont(element.pFont);
        lNameLabel.setForeground(Color.BLACK);

        JLabel emailLabel = new JLabel();
        emailLabel.setBounds(20, 290, 485, 50);
        emailLabel.setFont(element.pFont);
        emailLabel.setForeground(Color.BLACK);

        JLabel birthdayLabel = new JLabel();
        birthdayLabel.setBounds(20, 340, 485, 50);
        birthdayLabel.setFont(element.pFont);
        birthdayLabel.setForeground(Color.BLACK);

        JLabel dateLabel = new JLabel();
        dateLabel.setBounds(20, 390, 485, 50);
        dateLabel.setFont(element.pFont);
        dateLabel.setForeground(Color.BLACK);

        try {
            Connection conn = DatabaseHelper.getConnection();
            String query = "SELECT * FROM portuguez WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String uname = rs.getString("username");
                String fName = rs.getString("first_name");
                String mInitial = rs.getString("middle_initial");
                String lname = rs.getString("last_name");
                String email = rs.getString("email");
                String birthday = rs.getString("birthday");
                String dateRegistered = rs.getString("date_registered");

                uNameLabel.setText("Username: " + uname);
                fNameLabel.setText("First Name: " + fName);
                mInitialLabel.setText("Middle Initial: " + mInitial);
                lNameLabel.setText("Last Name: " + lname);
                emailLabel.setText("Email: " + email);
                birthdayLabel.setText("Birthday: " + birthday);
                dateLabel.setText("Registered on: " + dateRegistered);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to load users: " + e.getMessage());
            e.printStackTrace();
        }

        add(fNameLabel);
        add(uNameLabel);
        add(lNameLabel);
        add(mInitialLabel);
        add(emailLabel);
        add(birthdayLabel);
        add(dateLabel);
        add(title);
        setVisible(true);
    }
}
