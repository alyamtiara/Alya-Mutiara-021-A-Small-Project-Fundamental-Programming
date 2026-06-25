import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//mendefinisikan class DatabaseManager yang nantinya untuk mengurus koneksi ke database
public class DatabaseManager {
    //ambil alamat url yang berasal dari database sql yang telah dibuat
    private static final String URL = "jdbc:mysql://localhost:3306/game_project";
    //ambil user dari database yang telah dibuat
    private static final String USER = "root";
    //ambil lalu masukkan password yang telah dibuat didatabase sebelummya
    private static final String PASSWORD = "Ayaya260707";
    //method untuk mendapatkan koneksi jadi method ini bisa gagal dan pemanggilnya harus bersiap menangani eror
    public static Connection getConnection() throws SQLException {
        //menyuruh DriverManager buat membuka koneksi dengan URL,USER,PASSWORD
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
