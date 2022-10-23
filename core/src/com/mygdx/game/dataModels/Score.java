package com.mygdx.game.dataModels;

public class Score implements Comparable<Score> {
    private String name;
    private int score;

    public Score(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    @Override
    public int compareTo(Score score) {
        return this.score < score.getScore() ? -1 : (this.score == score.getScore()) ? 0 : 1;
    }
}
