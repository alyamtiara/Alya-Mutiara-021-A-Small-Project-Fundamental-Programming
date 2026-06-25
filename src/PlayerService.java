import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
public class PlayerService {


    public Player login(String username, String password) {
        //login, cek username password dari database apakah benar
        String sql = "SELECT * FROM players WHERE username = ? AND password = ?";
        //sambungkan
        try {
            Connection        conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, username);   //username
            stmt.setString(2, password);   //password

            ResultSet rs = stmt.executeQuery();


            if (rs.next()) { //apakah ada di querynya, kalau ada jalankan
                int    id     = rs.getInt("id");
                String uname  = rs.getString("username");
                int    wins   = rs.getInt("wins");
                int    losses = rs.getInt("losses");
                int    draws  = rs.getInt("draws");
                int    score  = rs.getInt("score");

                rs.close();
                stmt.close();
                conn.close();

                return new Player(id, uname, wins, losses, draws, score); //kirim data pemain
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            System.err.println("[PlayerService] Login error: " + e.getMessage());
        }

        return null;//tidak ada baris yang cocok
    }


    public void updateStatistics(Player player, String result) {
        String sql          = "";
        int    bonusScore   = 0;

        //tentukan SQL dan bonus poin berdasarkan hasil game
        if (result.equals("WIN")) {
            //menang: tambah wins + 1, score + 10
            sql       = "UPDATE players SET wins = wins + 1, score = score + ? WHERE id = ?";
            bonusScore = 10;
        } else if (result.equals("LOSE")) {
            //kalah: tambah losses + 1, score + 0
            sql       = "UPDATE players SET losses = losses + 1, score = score + ? WHERE id = ?";
            bonusScore = 0;
        } else if (result.equals("DRAW")) {
            //seri: tambah draws + 1, score + 3
            sql       = "UPDATE players SET draws = draws + 1, score = score + ? WHERE id = ?";
            bonusScore = 3;
        }

        //jika result tidak win/lose/draw, hentikan
        if (sql.isEmpty()) {
            System.err.println("[PlayerService] Hasil tidak dikenal: " + result);
            return;
        }

        try {
            Connection        conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, bonusScore);      //bonus score
            stmt.setInt(2, player.getId());  //id pemain

            stmt.executeUpdate(); //perintah ubah data

            stmt.close();
            conn.close();

            System.out.println("[PlayerService] Statistik diperbarui: " + result + " untuk " + player.getUsername());

        } catch (Exception e) {
            System.err.println("[PlayerService] Update statistik error: " + e.getMessage());
        }
    }
    //top 5
    public List<Player> getTopFiveScorers() {
        List<Player> topList = new ArrayList<>(); //daftar player

        //query urutin score besar ke kecil, kalau seri menang terbanyak diatas, ambil 5 saja
        String sql = "SELECT * FROM players ORDER BY score DESC, wins DESC LIMIT 5";

        try {
            Connection        conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet         rs   = stmt.executeQuery();

            //loop setiap baris hasil query
            while (rs.next()) {
                topList.add(new Player(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getInt("wins"),
                        rs.getInt("losses"),
                        rs.getInt("draws"),
                        rs.getInt("score")
                ));
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            System.err.println("[PlayerService] Top scorers error: " + e.getMessage());
        }

        return topList; //kembalikan daftar berisi maksimal 5 pemain
    }

    //ambil data terbaru satu pemain
    public Player getPlayerById(int id) {
        String sql = "SELECT * FROM players WHERE id = ?"; //cari berdasar id

        try {
            Connection        conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id); //id yang dicari
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) { //kalau ketemu
                Player p = new Player(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getInt("wins"),
                        rs.getInt("losses"),
                        rs.getInt("draws"),
                        rs.getInt("score")
                );
                rs.close();
                stmt.close();
                conn.close();
                return p; //kembalikan data terbaru
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            System.err.println("[PlayerService] getPlayerById error: " + e.getMessage());
        }

        return null;
    }
}
