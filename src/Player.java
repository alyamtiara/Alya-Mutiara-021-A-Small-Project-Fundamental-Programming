public class Player {
    private int id;
    private String username;
    private int wins;
    private int losses;
    private int draws;
    private int score;

    public Player(int id, String username, int wins, int losses, int draws, int score) {
        this.id = id;
        this.username = username;
        this.wins = wins;
        this.losses = losses;
        this.draws = draws;
        this.score = score;
    }

    public int getId() { //dapatkan ID
        return id;
    }

    public String getUsername() { //dapatkan username
        return username;
    }

    public int getWins() { //dapatkan pemenang
        return wins;
    }

    public int getLosses() { //dapatkan yang kalah
        return losses;
    }

    public int getDraws() { //dapatkan seri
        return draws;
    }

    public int getScore() { // dapatkan scores
        return score;
    }
}
