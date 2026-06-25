import javax.swing.*;
import java.awt.*;

public class StatisticsFrame extends JFrame {

    private PlayerService playerService;
    private Player        player;

    public StatisticsFrame(Player playerLama) {
        playerService = new PlayerService();

        Player playerBaru = playerService.getPlayerById(playerLama.getId());
        this.player = (playerBaru != null) ? playerBaru : playerLama;

        buildUI();
    }

    private void buildUI() {
        setTitle("Statistik — " + player.getUsername());
        setSize(360, 420);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel root = new JPanel(new GridBagLayout());
        root.setBackground(new Color(28, 28, 45));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 20, 6, 20);
        gbc.fill   = GridBagConstraints.HORIZONTAL;

        JLabel lblJudul = new JLabel("📊  STATISTIK PEMAIN", SwingConstants.CENTER);
        lblJudul.setFont(new Font("Arial", Font.BOLD, 18));
        lblJudul.setForeground(new Color(100, 180, 255));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        root.add(lblJudul, gbc);

        gbc.gridy = 1;
        root.add(new JSeparator(), gbc);

        int totalGame = player.getWins() + player.getLosses() + player.getDraws();

        addStatRow(root, gbc, 2, "👤  Nama Pemain",  player.getUsername(), Color.WHITE);
        addStatRow(root, gbc, 3, "🎮  Total Game",   String.valueOf(totalGame),    new Color(180, 180, 255));
        addStatRow(root, gbc, 4, "🏆  Menang",       String.valueOf(player.getWins()),   new Color(80, 220, 80));
        addStatRow(root, gbc, 5, "💀  Kalah",        String.valueOf(player.getLosses()), new Color(255, 100, 100));
        addStatRow(root, gbc, 6, "🤝  Seri",         String.valueOf(player.getDraws()),  new Color(255, 200, 50));
        addStatRow(root, gbc, 7, "⭐  Total Score",  String.valueOf(player.getScore()),  new Color(255, 200, 50));

        String winRate = totalGame > 0
                ? String.format("%.1f%%", (player.getWins() * 100.0) / totalGame)
                : "0.0%";
        addStatRow(root, gbc, 8, "📈  Win Rate",     winRate, new Color(100, 200, 255));

        gbc.gridy = 9; gbc.gridwidth = 2;
        root.add(new JSeparator(), gbc);

        JButton btnTutup = new JButton("Tutup");
        btnTutup.setFont(new Font("Arial", Font.BOLD, 13));
        btnTutup.setBackground(new Color(60, 100, 160));
        btnTutup.setForeground(Color.WHITE);
        btnTutup.setFocusPainted(false);
        btnTutup.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnTutup.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        gbc.gridy = 10;
        root.add(btnTutup, gbc);

        btnTutup.addActionListener(e -> this.dispose());

        add(root);
    }

    private void addStatRow(JPanel panel, GridBagConstraints gbc,
                            int baris, String label, String nilai, Color warna) {
        gbc.gridwidth = 1;

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Arial", Font.PLAIN, 13));
        lbl.setForeground(new Color(170, 170, 190));
        gbc.gridx = 0; gbc.gridy = baris; gbc.anchor = GridBagConstraints.WEST;
        panel.add(lbl, gbc);

        JLabel val = new JLabel(nilai);
        val.setFont(new Font("Arial", Font.BOLD, 14));
        val.setForeground(warna);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.EAST;
        panel.add(val, gbc);
    }
}
