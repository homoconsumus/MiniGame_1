package kr.co.company.minigame_1;

public class ScoreData {
    private int enemyKillCount;
    private String timeString;
    private String playerName;

    public ScoreData(String playerName, int enemyKillCount, String timeString) {
        this.playerName = playerName;
        this.enemyKillCount = enemyKillCount;
        this.timeString = timeString;
    }

    public int getEnemyKillCount() {
        return enemyKillCount;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getTimeString() {
        return timeString;
    }
}