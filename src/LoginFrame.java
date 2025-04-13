import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ItemEvent;

public class LoginFrame extends JFrame {
    LoginFrame() {
        Elements element = new Elements();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 500);
        setResizable(false);
        setLayout(null);
        setTitle("Log in");
        getContentPane().setBackground(element.red);

        JLabel title = new JLabel();
        title.setBounds(0, 75, 485, 50);
        title.setFont(element.h1Font);
        title.setText("Login Form");
        title.setForeground(element.yellow);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel form = new JPanel();
        form.setBounds(130, 125, 300, 200);
        form.setBackground(element.red);
        form.setLayout(null);

        JLabel unLabel = new JLabel();
        unLabel.setBounds(0, 0, 225, 50);
        unLabel.setText("Username:");
        unLabel.setFont(element.h2Font);
        unLabel.setForeground(element.beige);

        JTextField unTextField = new JTextField();
        unTextField.setFont(element.h2Font);
        unTextField.setBounds(0, 50, 225, 30);
        unTextField.setBorder(BorderFactory.createCompoundBorder(
                unTextField.getBorder(),
                new EmptyBorder(2, 6, 2, 6)
        ));

        JLabel pwLabel = new JLabel();
        pwLabel.setBounds(0, 90, 225, 50);
        pwLabel.setText("Password:");
        pwLabel.setFont(element.h2Font);
        pwLabel.setForeground(element.beige);

        JPasswordField pwField = new JPasswordField();
        pwField.setFont(element.h2Font);
        pwField.setBounds(0, 140, 225, 30);
        pwField.setBorder(BorderFactory.createCompoundBorder(
                pwField.getBorder(),
                new EmptyBorder(2, 6, 2, 6)
        ));

        JCheckBox showPwBtn = new JCheckBox();
        showPwBtn.setFont(element.pFont);
        showPwBtn.setText("Show");
        showPwBtn.setBounds(225, 140, 70, 30);
        showPwBtn.setVerticalAlignment(SwingConstants.CENTER);
        showPwBtn.setBackground(element.red);
        showPwBtn.setForeground(Color.WHITE);
        showPwBtn.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED)
                pwField.setEchoChar((char) 0);
            else
                pwField.setEchoChar('*');
        });

        JButton loginBtn = new JButton();
        loginBtn.setBounds(175, 325, 150, 40);
        loginBtn.setText("Login");
        loginBtn.setBackground(element.yellow);
        loginBtn.setFont(element.h2Font);
        loginBtn.addActionListener(e -> {
            if (!(unTextField.getText().equals("admin"))) {
                JOptionPane.showMessageDialog(new JFrame(), "Invalid Username");
            } else if (!(new String(pwField.getPassword()).equals("password"))) {
                JOptionPane.showMessageDialog(new JFrame(), "Invalid Password");
            } else {
                OTPFrame home = new OTPFrame();
                dispose();
            }
        });

        JLabel unameLabel = new JLabel();
        unameLabel.setText("Username: admin");
        unameLabel.setForeground(Color.WHITE);
        unameLabel.setFont(element.sFont);
        unameLabel.setBounds(5, 420, 100, 20);

        JLabel pWordLabel = new JLabel();
        pWordLabel.setText("Password: password");
        pWordLabel.setForeground(Color.WHITE);
        pWordLabel.setFont(element.sFont);
        pWordLabel.setBounds(5, 440, 100, 20);

        form.add(showPwBtn);
        form.add(pwLabel);
        form.add(pwField);
        form.add(unTextField);
        form.add(unLabel);

        add(pWordLabel);
        add(unameLabel);
        add(loginBtn);
        add(form);
        add(title);
        setVisible(true);
    }
}
