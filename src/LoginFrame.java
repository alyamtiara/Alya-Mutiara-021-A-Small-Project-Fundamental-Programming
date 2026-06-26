import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private PlayerService playerService;

    public LoginFrame() {
        playerService = new PlayerService(); //login

        setTitle("TIC TAC TOE GAMES LOGIN"); //judul di bar di jendela login
        setSize(360, 260); //ukuran jendela login
        setLocationRelativeTo(null); //nampilin ditengah layarnya
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //klik X untuk nutup program

        //warna tema untuk ui
        Color bgColor = new Color(28, 30, 48);
        Color titleColor = new Color(90, 180, 255);
        Color inputColor = new Color(40, 43, 65);
        Color buttonColor = new Color(52, 120, 200);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(bgColor);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("TIC TAC TOE LOGIN");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(titleColor);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setForeground(Color.WHITE);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblUsername, gbc);

        txtUsername = new JTextField(12);
        txtUsername.setBackground(inputColor);
        txtUsername.setForeground(Color.WHITE);
        txtUsername.setCaretColor(Color.WHITE);

        gbc.gridx = 1;
        panel.add(txtUsername, gbc);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setForeground(Color.WHITE);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(lblPassword, gbc);

        txtPassword = new JPasswordField(12);
        txtPassword.setBackground(inputColor);
        txtPassword.setForeground(Color.WHITE);
        txtPassword.setCaretColor(Color.WHITE);

        gbc.gridx = 1;
        panel.add(txtPassword, gbc);

        btnLogin = new JButton("LOGIN");
        btnLogin.setBackground(buttonColor);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(btnLogin, gbc);

        add(panel);
        
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText(); //get username from txtUsername
                String password = new String(txtPassword.getPassword()); //get password from txtPassword
                Player player = playerService.login(username, password); // Call playerService.login(username, password)
                //kalau login suksses
                if (player != null) {
                    JOptionPane.showMessageDialog(
                            LoginFrame.this,
                            "Login successful!"
                    );

                    MainMenuFrame menuFrame = new MainMenuFrame(player);
                    menuFrame.setVisible(true);
                    dispose();
                //kalau login gagal
                } else {
                    JOptionPane.showMessageDialog(
                            LoginFrame.this,
                            "Invalid username or password!",
                            "Login Failed",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
    }
}
