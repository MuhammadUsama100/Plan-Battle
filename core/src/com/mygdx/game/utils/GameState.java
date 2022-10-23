package com.mygdx.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class GameState {

    private static GameState instance;
    private boolean gameOver;
    private float  minimumEnemyY  ;
    private  float minimumEnemyX   ;
    private int score;
    private int lives;

    private boolean isPaused;

    private float timeElapsed;

    private GameState() {
        resetState();
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public boolean isPaused() { return isPaused; }

    public void resetState() {
        timeElapsed = 0;
        score = 0;
        lives = 15;
        minimumEnemyX = Gdx.graphics.getWidth() ;
        minimumEnemyY = Gdx.graphics.getHeight() ;
    }

    public void setTimeElapsed(float timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    public float getTimeElapsed() { return timeElapsed; }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean isGameOver() {
        return gameOver;
    }


    public void incrementScore(int score) {
        this.score += score;
    }

    public void decrementLife() { lives -= 1; }

    public int getRemainingLives() { return lives; }

    public boolean hasRemainingLives() {
        return lives > 0;
    }

    public int getScore() {
        return score;
    }

    public static GameState getInstance() {
        if(instance == null) {
            instance = new GameState();
        }
        return instance;
    }


    public float getMinimumEnemyY() {
        return minimumEnemyY;
    }

    public void setMinimumEnemyY(float minimumEnemyY) {
        this.minimumEnemyY = minimumEnemyY;
    }

    public float getMinimumEnemyX() {
        return minimumEnemyX;
    }

    public void setMinimumEnemyX(float minimumEnemyX) {
        this.minimumEnemyX = minimumEnemyX;
    }
}
