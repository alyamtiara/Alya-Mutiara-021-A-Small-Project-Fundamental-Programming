import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//buat kelas GameLogic
public class GameLogic {
    private char[] board; //buat board yang nantinya kepake buat kotaknya
    private Random random; //untuk buat obejek angka acak

    public GameLogic() {
        board = new char[9]; //buat array untuk isinya 9 kotak
        random = new Random(); //buat alat acak
        resetBoard(); //kosongkan semua kotak
    }

    public void resetBoard() { //isi semua kotak dengan (' ')  yang artinya kotak kosong
        for (int i = 0; i < board.length; i++) { //dimulai dari ke 0 sampai 0
            board[i] = ' ';
        }
    }

    //coba mmeletakkan simbol X atau O di kotaknya nanti, true kalo berhasil dan false jika tidak berhasil(kotak udah ada, naruh diluarnya)
    public boolean makeMove(int index, char symbol) {
        if (index < 0 || index >= 9) {
            return false;
        }
        if (board[index] != ' ') {
            return false;
        }
        board[index] = symbol;
        return true;
    }
    //cek apakah simbol membentuk 3 segaris (menang) yang nantinya 3 baris, 3 kolom, 2 diagonal
    public boolean checkWinner(char symbol) {
        int[][] patterns = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
                {0, 4, 8}, {2, 4, 6}
        };
        //periksa tiap garis satu persatu
        for (int i = 0; i < patterns.length; i++) {
            int a = patterns[i][0];
            int b = patterns[i][1];
            int c = patterns[i][2];
            //kalau 3 kotak isinya simbol yang sama nantinya menang
            if (board[a] == symbol && board[b] == symbol && board[c] == symbol) {
                return true;
            }
        }
        return false; //tidak ada yang cocok (belum menang)
    }
    //cek apakah papan penuh (tidak ada yang kosong)
    public boolean isDraw() {
        for (int i = 0; i < board.length; i++) {
            if (board[i] == ' ') {
                return false;
            }
        }
        return true;
    }

    //pilih kotak kosong secara acak untuk gerakannya komputer
    public int computerMove() {
        List<Integer> emptyCells = new ArrayList<>(); //daftar nomor kotak yang kosong
        for (int i = 0; i < board.length; i++) {
            if (board[i] == ' ') { //kalau kotaknya kosong
                emptyCells.add(i); //tambahkan
            }
        }
        if (emptyCells.isEmpty()) { //kalau tidak ada yang kosong
            return -1; //tandai tidak boleh adaa gerakan komputernya
        }
        int pick = random.nextInt(emptyCells.size()); //pilih acak 1 posisi dari daftar
        return emptyCells.get(pick); //kembalikan nomor kotaknya
    }
    //buat akses ke isi papan
    public char[] getBoard() {
        return board;
    }
    //method tambahan untuk pemenang, buat menandai garis kemenangan
    public int[] getWinningPattern(char symbol) {
        int[][] patterns = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
                {0, 4, 8}, {2, 4, 6}
        };
        for (int[] pattern : patterns) { //periksa tiap garis
            if (board[pattern[0]] == symbol &&
                    board[pattern[1]] == symbol &&
                    board[pattern[2]] == symbol) {
                return pattern; //ketemu garis kemenangan nanti kembalikan ketiga nomornya
            }
        }
        return null; //kalau tidak ada yang menang
    }
}
