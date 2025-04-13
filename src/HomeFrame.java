import javax.swing.*;
import java.awt.*;

public class HomeFrame extends JFrame {
    HomeFrame() {
        Elements element = new Elements();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 500);
        setResizable(false);
        setLayout(null);
        setTitle("Home");
        getContentPane().setBackground(element.red);

        JLabel welcomeLbl = new JLabel();
        welcomeLbl.setText("Welcome!");
        welcomeLbl.setFont(element.h1Font);
        welcomeLbl.setBounds(20, 125, 465, 50);
        welcomeLbl.setForeground(element.yellow);

        JTextArea message = new JTextArea(
                "Thank you for logging in to this simple application!\n"
                        + "Have a great day ahead!\n\n"
                        + "- John Michael Portuguez, BSCS 2B"
        );
        message.setEditable(false);
        message.setBackground(element.red);
        message.setForeground(Color.WHITE);
        message.setLineWrap(true);
        message.setWrapStyleWord(true);
        message.setBounds(20, 200, 465, 100);
        message.setFont(element.pFont);

        JButton logoutBtn = new JButton();
        logoutBtn.setBounds(310, 325, 100, 40);
        logoutBtn.setText("Logout");
        logoutBtn.setBackground(element.yellow);
        logoutBtn.setFont(element.h2Font);
        logoutBtn.addActionListener(e -> {
            LoginFrame login = new LoginFrame();
            dispose();
        });

        add(logoutBtn);
        add(message);
        add(welcomeLbl);
        setVisible(true);
    }
}
