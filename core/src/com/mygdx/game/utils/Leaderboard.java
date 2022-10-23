package com.mygdx.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.mygdx.game.dataModels.Score;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Leaderboard {

    private static Leaderboard instance;

    private FileHandle fh;

    private Leaderboard() {
        fh = Gdx.files.local("data/leaderboard.txt");
    }

    public void addScore(String name, int score) {
        fh.writeString(name + "," + String.valueOf(score) + "|", true);
    }

    public ArrayList<Score> getLeaderboard() {
        ArrayList<Score> scores = new ArrayList<Score>();
        String[] data = fh.readString().split("\\|");

        for(String d: data) {
            String[] score = d.split(",");
            scores.add(new Score(score[0], Integer.valueOf(score[1])));
        }

        Collections.sort(scores, Collections.<Score>reverseOrder());

        return scores;
    }

    public static Leaderboard getInstance() {
        if(instance == null) {
            instance = new Leaderboard();
        }
        return instance;
    }

}
