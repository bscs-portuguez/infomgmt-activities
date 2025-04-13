import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class RegistrationFrame extends JFrame {
    RegistrationFrame() {
        Elements element = new Elements();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 500);
        setResizable(false);
        setLayout(null);
        setTitle("Register");
        getContentPane().setBackground(element.red);

        JLabel title = new JLabel();
        title.setBounds(0, 30, 485, 50);
        title.setFont(element.h1Font);
        title.setText("Registration Form");
        title.setForeground(element.yellow);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel form = new JPanel();
        form.setBounds(20, 90, 445, 400);
        form.setBackground(element.red);
        form.setLayout(null);

        JLabel unLabel = new JLabel();
        unLabel.setBounds(0, 0, 212, 50);
        unLabel.setText("Username:");
        unLabel.setFont(element.pFont);
        unLabel.setForeground(element.beige);

        JTextField unTextField = new JTextField();
        unTextField.setFont(element.pFont);
        unTextField.setBounds(0, 50, 212, 30);
        unTextField.setBorder(BorderFactory.createCompoundBorder(
                unTextField.getBorder(),
                new EmptyBorder(2, 6, 2, 6)
        ));

        JLabel pwLabel = new JLabel();
        pwLabel.setBounds(222, 0, 225, 50);
        pwLabel.setText("Password:");
        pwLabel.setFont(element.h2Font);
        pwLabel.setForeground(element.beige);

        JPasswordField pwField = new JPasswordField();
        pwField.setFont(element.h2Font);
        pwField.setBounds(222, 50, 225, 30);
        pwField.setBorder(BorderFactory.createCompoundBorder(
                pwField.getBorder(),
                new EmptyBorder(2, 6, 2, 6)
        ));

        JCheckBox showPwBtn = new JCheckBox();
        showPwBtn.setFont(element.pFont);
        showPwBtn.setText("Show");
        showPwBtn.setBounds(374, 12, 76, 30);
        showPwBtn.setVerticalAlignment(SwingConstants.CENTER);
        showPwBtn.setBackground(element.red);
        showPwBtn.setForeground(Color.WHITE);
        showPwBtn.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED)
                pwField.setEchoChar((char) 0);
            else
                pwField.setEchoChar('*');
        });

        JLabel fnLabel = new JLabel();
        fnLabel.setBounds(0, 90, 192, 50);
        fnLabel.setText("First Name:");
        fnLabel.setFont(element.pFont);
        fnLabel.setForeground(element.beige);

        JTextField fnField = new JTextField();
        fnField.setFont(element.pFont);
        fnField.setBounds(0, 140, 192, 30);
        fnField.setBorder(BorderFactory.createCompoundBorder(
                fnField.getBorder(),
                new EmptyBorder(2, 6, 2, 6)
        ));

        JLabel miLabel = new JLabel();
        miLabel.setBounds(202, 90, 50, 50);
        miLabel.setText("M.I:");
        miLabel.setFont(element.pFont);
        miLabel.setForeground(element.beige);

        JTextField miField = new JTextField();
        miField.setFont(element.pFont);
        miField.setBounds(202, 140, 50, 30);
        miField.setBorder(BorderFactory.createCompoundBorder(
                fnField.getBorder(),
                new EmptyBorder(2, 6, 2, 6)
        ));

        JLabel lnLabel = new JLabel();
        lnLabel.setBounds(262, 90, 182, 50);
        lnLabel.setText("Last Name:");
        lnLabel.setFont(element.pFont);
        lnLabel.setForeground(element.beige);

        JTextField lnField = new JTextField();
        lnField.setFont(element.pFont);
        lnField.setBounds(262, 140, 182, 30);
        lnField.setBorder(BorderFactory.createCompoundBorder(
                lnField.getBorder(),
                new EmptyBorder(2, 6, 2, 6)
        ));

        JLabel emailLabel = new JLabel();
        emailLabel.setBounds(0, 180, 212, 50);
        emailLabel.setText("Email:");
        emailLabel.setFont(element.pFont);
        emailLabel.setForeground(element.beige);

        JTextField emailTextField = new JTextField();
        emailTextField.setFont(element.pFont);
        emailTextField.setBounds(0, 230, 212, 30);
        emailTextField.setBorder(BorderFactory.createCompoundBorder(
                emailTextField.getBorder(),
                new EmptyBorder(2, 6, 2, 6)
        ));

        JLabel bdLabel = new JLabel();
        bdLabel.setBounds(222, 180, 212, 50);
        bdLabel.setText("Birthday:");
        bdLabel.setFont(element.pFont);
        bdLabel.setForeground(element.beige);

        Integer[] years = new Integer[80];
        int year = 2025;
        for (int i = 0; i < years.length; i++) {
            years[i] = year;
            year--;
        }

        JComboBox<Integer> bdYyField = new JComboBox<>(years);
        bdYyField.setFont(element.pFont);
        bdYyField.setBounds(222, 230, 100, 30);

        Integer[] months = new Integer[12];
        for (int i = 0; i < months.length; i++) {
            months[i] = i+1;
        }

        JComboBox<Integer> bdMmField = new JComboBox<>(months);
        bdMmField.setFont(element.pFont);
        bdMmField.setBounds(332, 230, 46, 30);

        Integer[] days = new Integer[30];
        for (int i = 0; i < days.length; i++) {
            days[i] = i+1;
        }

        JComboBox<Integer> bdDdField = new JComboBox<>(days);
        bdDdField.setFont(element.pFont);
        bdDdField.setBounds(388, 230, 46, 30);

        JButton registerBtn = new JButton();
        registerBtn.setBounds(175, 390, 150, 40);
        registerBtn.setText("Register");
        registerBtn.setBackground(element.yellow);
        registerBtn.setFont(element.h2Font);
        registerBtn.addActionListener(e -> {
            try {
                String username = unTextField.getText().trim();
                String password = new String(pwField.getPassword()).trim();
                String firstName = fnField.getText().trim();
                String middleInitial = miField.getText().trim();
                String lastName = lnField.getText().trim();
                String email = emailTextField.getText().trim();

                int yyyy = (int) bdYyField.getSelectedItem();
                int mm = (int) bdMmField.getSelectedItem();
                int dd = (int) bdDdField.getSelectedItem();
                String birthday = String.format("%04d-%02d-%02d", yyyy, mm, dd);

                String dateRegistered = java.time.LocalDateTime.now()
                        .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                // Connect to DB
                Connection conn = DriverManager.getConnection("jdbc:sqlite:users.db");

                // Check for existing username
                PreparedStatement checkUsername = conn.prepareStatement("SELECT id FROM users WHERE username = ?");
                checkUsername.setString(1, username);
                ResultSet rsUser = checkUsername.executeQuery();
                if (rsUser.next()) {
                    JOptionPane.showMessageDialog(this, "Username already exists. Please choose another one.");
                    rsUser.close();
                    checkUsername.close();
                    conn.close();
                    return;
                }
                rsUser.close();
                checkUsername.close();

                // Check for existing email
                PreparedStatement checkEmail = conn.prepareStatement("SELECT id FROM users WHERE email = ?");
                checkEmail.setString(1, email);
                ResultSet rsEmail = checkEmail.executeQuery();
                if (rsEmail.next()) {
                    JOptionPane.showMessageDialog(this, "Email already in use. Please use another email.");
                    rsEmail.close();
                    checkEmail.close();
                    conn.close();
                    return;
                }
                rsEmail.close();
                checkEmail.close();

                // Insert new user
                String sql = "INSERT INTO users (username, password, first_name, middle_initial, last_name, email, birthday, date_registered, role) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'user');";

                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                pstmt.setString(3, firstName);
                pstmt.setString(4, middleInitial);
                pstmt.setString(5, lastName);
                pstmt.setString(6, email);
                pstmt.setString(7, birthday);
                pstmt.setString(8, dateRegistered);

                pstmt.executeUpdate();

                pstmt.close();
                conn.close();

                JOptionPane.showMessageDialog(this, "User registered successfully!");
                dispose(); // Close form
                new LoginFrame(); // Go to login screen (optional)

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Registration failed: " + ex.getMessage());
                ex.printStackTrace();
            }
        });



        form.add(bdLabel);
        form.add(bdYyField);
        form.add(bdMmField);
        form.add(bdDdField);
        form.add(emailLabel);
        form.add(emailTextField);
        form.add(lnLabel);
        form.add(lnField);
        form.add(miField);
        form.add(miLabel);
        form.add(fnField);
        form.add(fnLabel);
        form.add(pwField);
        form.add(pwLabel);
        form.add(showPwBtn);
        form.add(unLabel);
        form.add(unTextField);

        add(registerBtn);
        add(title);
        add(form);

        setVisible(true);
    }
}
