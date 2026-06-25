import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    private Player currentPlayer; //pemain yang login
    private PlayerService playerService; //nyambung ke database
    private GameLogic gameLogic; //aturan main
    private JButton[] buttons; //kotaknya
    private JLabel lblStatus; //status di papan

    //warna untuk tampilan background, cell, X, O, yang menang
    private static final Color BG_COLOR   = new Color(28, 28, 45);
    private static final Color CELL_COLOR = new Color(45, 45, 70);
    private static final Color X_COLOR    = new Color(80, 180, 255);
    private static final Color O_COLOR    = new Color(255, 90, 90);
    private static final Color WIN_COLOR  = new Color(255, 210, 0);

    public GameFrame(Player player) {
        this.currentPlayer = player; //pemain
        this.playerService = new PlayerService();
        this.gameLogic     = new GameLogic(); //logic game

        buildUI(); // membuat uinya
        //aksi klik ke tiap tombol papan
        for (int i = 0; i < buttons.length; i++) {
            final int index = i;
            buttons[i].addActionListener(e -> handlePlayerMove(index)); //saat tombol  ke index di klik maka jalankan handlePlayerMove(index)
        }
    }
    private void handlePlayerMove(int index) {
        boolean berhasil = gameLogic.makeMove(index, 'X'); //coba X ditaruh
        if (!berhasil) return; //kotak sudah terisi, abaikan

        //Update button text to X
        buttons[index].setText("X");
        buttons[index].repaint();
        SoundManager.playClickSound();

        //Check whether player wins
        if (gameLogic.checkWinner('X')) {
            tandaiSelPemenang('X');
            finishGame("WIN");
            return;
        }

        //Check draw
        if (gameLogic.isDraw()) {
            finishGame("DRAW");
            return;
        }

        //Generate computer move (dengan jeda supaya terasa natural)
        Timer jeda = new Timer(600, e -> {
            int compIndex = gameLogic.computerMove();

            if (compIndex != -1) {
                gameLogic.makeMove(compIndex, 'O');

                //Update computer button text to O
                buttons[compIndex].setText("O");
                buttons[compIndex].repaint();
                SoundManager.playComputerSound();

                //Check whether computer wins
                if (gameLogic.checkWinner('O')) {
                    tandaiSelPemenang('O');
                    finishGame("LOSE");
                    return;
                }

                if (gameLogic.isDraw()) {
                    finishGame("DRAW");
                    return;
                }

                //Game masih lanjut
                setAllButtonsEnabled(true);
                for (int i = 0; i < 9; i++) {
                    if (!buttons[i].getText().isEmpty()) {
                        buttons[i].setEnabled(false);
                    }
                }
                lblStatus.setText("Giliran kamu — letakkan X");
            }
        });
        jeda.setRepeats(false);
        jeda.start();
    }
    private void finishGame(String result) {
        setAllButtonsEnabled(false);

        //Update database statistics after game ends
        playerService.updateStatistics(currentPlayer, result);

        //Mainkan suara
        SoundManager.playGameResult(result);

        //Update label status
        switch (result) {
            case "WIN":
                lblStatus.setText("Kamu MENANG! 🎉 +10 poin");
                lblStatus.setForeground(new Color(80, 230, 80));
                break;
            case "LOSE":
                lblStatus.setText("Kamu KALAH! 😢 Coba lagi!");
                lblStatus.setForeground(new Color(255, 90, 90));
                break;
            case "DRAW":
                lblStatus.setText("SERI! 🤝 +3 poin");
                lblStatus.setForeground(new Color(255, 200, 50));
                break;
        }

        //jeda sebentar supaya suara selesai dulu
        Timer dialogTimer = new Timer(800, e -> {
            //tampilkan hasil
            JOptionPane.showMessageDialog(this, "Game result: " + result);

            //ambil data terbaru dari database
            Player updatedPlayer = playerService.getPlayerById(currentPlayer.getId());
            if (updatedPlayer != null) currentPlayer = updatedPlayer;

            //buka MainMenuFrame lalu tutup GameFrame
            MainMenuFrame menuFrame = new MainMenuFrame(currentPlayer);
            menuFrame.setVisible(true);
            this.dispose();
        });
        dialogTimer.setRepeats(false);
        dialogTimer.start();
    }
    //buat tampilan ui
    private void buildUI() {
        setTitle("Tic-Tac-Toe — " + currentPlayer.getUsername() + " vs Komputer");
        setSize(460, 560);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBackground(BG_COLOR);
        root.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 0, 4));
        headerPanel.setBackground(BG_COLOR);

        lblStatus = new JLabel("Giliran kamu — letakkan X", SwingConstants.CENTER);
        lblStatus.setFont(new Font("Arial", Font.BOLD, 16));
        lblStatus.setForeground(Color.WHITE);

        JLabel lblInfo = new JLabel(currentPlayer.getUsername() + "  [X]   vs   Komputer  [O]", SwingConstants.CENTER);
        lblInfo.setFont(new Font("Arial", Font.PLAIN, 12));
        lblInfo.setForeground(new Color(160, 160, 190));

        headerPanel.add(lblStatus);
        headerPanel.add(lblInfo);
        root.add(headerPanel, BorderLayout.NORTH);

        JPanel boardPanel = new JPanel(new GridLayout(3, 3, 6, 6));
        boardPanel.setBackground(new Color(18, 18, 35));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));

        buttons = new JButton[9];
        for (int i = 0; i < 9; i++) {
            buttons[i] = createCellButton();
            boardPanel.add(buttons[i]);
        }
        root.add(boardPanel, BorderLayout.CENTER);

        JButton btnBack = new JButton("← Kembali ke Menu");
        btnBack.setBackground(new Color(60, 60, 85));
        btnBack.setForeground(new Color(180, 180, 210));
        btnBack.setFont(new Font("Arial", Font.PLAIN, 12));
        btnBack.setFocusPainted(false);
        btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnBack.addActionListener(e -> {
            int pilihan = JOptionPane.showConfirmDialog(this, "Kembali ke menu? Game yang sedang berjalan akan dibatalkan.", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (pilihan == JOptionPane.YES_OPTION) {
                MainMenuFrame menu = new MainMenuFrame(currentPlayer);
                menu.setVisible(true);
                this.dispose();
            }
        });
        root.add(btnBack, BorderLayout.SOUTH);

        add(root);
    }

    private JButton createCellButton() {
        JButton btn = new JButton() {

            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                int w = getWidth();
                int h = getHeight();
                int pad = 18;
                String sym = getText();

                if (sym.equals("X")) {
                    // Gambar X: dua garis silang biru
                    g2.setColor(X_COLOR);
                    g2.setStroke(new BasicStroke(7f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    g2.drawLine(pad, pad, w - pad, h - pad);
                    g2.drawLine(w - pad, pad, pad, h - pad);
                } else if (sym.equals("O")) {
                    // Gambar O: lingkaran merah
                    g2.setColor(O_COLOR);
                    g2.setStroke(new BasicStroke(7f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    g2.drawOval(pad, pad, w - 2 * pad, h - 2 * pad);
                }
            }
        };
        btn.setFont(new Font("Arial", Font.BOLD, 1)); // teks disembunyikan, digambar manual
        btn.setBackground(CELL_COLOR);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void tandaiSelPemenang(char symbol) {
        int[] pola = gameLogic.getWinningPattern(symbol);
        if (pola != null) {
            for (int idx : pola) {
                buttons[idx].setBackground(WIN_COLOR);
                buttons[idx].repaint();
            }
        }
    }

    private void setAllButtonsEnabled(boolean enabled) {
        for (JButton btn : buttons) btn.setEnabled(enabled);
    }
}
