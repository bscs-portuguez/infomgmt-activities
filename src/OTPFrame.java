import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class OTPFrame extends JFrame {
    private int code;
    private int countdown = 30;
    private Timer timer;
    private JButton sendCodeBtn;

    OTPFrame(String username) {
        Elements element = new Elements();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 500);
        setResizable(false);
        setLayout(null);
        setTitle("Verification");
        getContentPane().setBackground(element.red);

        JLabel title = new JLabel();
        title.setBounds(0, 75, 485, 50);
        title.setFont(element.h1Font);
        title.setText("Verification Frame");
        title.setForeground(element.yellow);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel form = new JPanel();
        form.setBounds(150, 125, 185, 300);
        form.setBackground(element.red);
        form.setLayout(null);

        JLabel codeLabel = new JLabel();
        codeLabel.setBounds(0, 0, 185, 50);
        codeLabel.setText("6-digit code:");
        codeLabel.setForeground(element.beige);
        codeLabel.setFont(element.h2Font);

        JTextField codeField = new JTextField();
        codeField.setBounds(0, 50, 185, 30);
        codeField.setFont(element.h2Font);
        codeField.setHorizontalAlignment(SwingConstants.CENTER);
        codeField.setEnabled(false);
        codeField.setBorder(BorderFactory.createCompoundBorder(
                codeField.getBorder(),
                new EmptyBorder(2, 6, 2, 6)
        ));

        sendCodeBtn = new JButton();
        sendCodeBtn.setBounds(0, 90, 185, 40);
        sendCodeBtn.setBackground(element.yellow);
        sendCodeBtn.setFont(element.pFont);
        sendCodeBtn.setForeground(Color.BLACK);
        sendCodeBtn.setText("Send Code");
        sendCodeBtn.addActionListener(e -> {
            code = 100000 + new java.util.Random().nextInt(900000);
            JOptionPane.showMessageDialog(new JFrame("OTP Code"), "Your code is: " + code);
            codeField.setEnabled(true);
            startCountDown();
        });

        JButton verifyBtn = new JButton();
        verifyBtn.setBounds(20, 200, 145, 40);
        verifyBtn.setBackground(new Color(0xD3D3D3));
        verifyBtn.setFont(element.h2Font);
        verifyBtn.setForeground(Color.BLACK);
        verifyBtn.setText("Verify");
        verifyBtn.addActionListener(e -> {
            if (!(codeField.getText().equals(String.valueOf(code)))) {
                JOptionPane.showMessageDialog(new JFrame(), "Wrong code. Try again.");
            } else if (username.equals("admin")) {
                new Dashboard();
                dispose();
            } else {
                new HomeFrame();
                dispose();
            }
        });

        JButton cancelBtn = new JButton();
        cancelBtn.setBounds(390, 425, 85, 30);
        cancelBtn.setBackground(element.red);
        cancelBtn.setFont(element.sFont);
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setText("Cancel");
        cancelBtn.addActionListener(e -> {
            LoginFrame login = new LoginFrame();
            dispose();
        });

        form.add(verifyBtn);
        form.add(sendCodeBtn);
        form.add(codeField);
        form.add(codeLabel);

        add(cancelBtn);
        add(form);
        add(title);
        setVisible(true);
    }

    private void startCountDown() {
        sendCodeBtn.setEnabled(false);
        timer = new Timer(1000, e -> {
            if (countdown > 0) {
                sendCodeBtn.setText("Resend Code: " + countdown);
                countdown--;
            } else {
                timer.stop();
                sendCodeBtn.setText("Resend Code");
                sendCodeBtn.setEnabled(true);
                countdown = 30; // Reset countdown for next use
            }
        });

        timer.start(); // Start countdown
    }
}
