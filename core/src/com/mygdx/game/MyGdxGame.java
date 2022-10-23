package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screens.SplashScreen;
import com.mygdx.game.utils.Leaderboard;

public class MyGdxGame extends Game {
    public static final String TAG = MyGdxGame.class.getName();

    public SpriteBatch batch;
    public BitmapFont font;
    public AssetManager assetManager;
    public Leaderboard db;

    private void loadAssets() {
        assetManager.load("PlayerJet/PLANE.png", Texture.class);
        assetManager.load("enemy.png", Texture.class);
        assetManager.load("enemy1.png", Texture.class);
        assetManager.load("enemy2.png", Texture.class);
        assetManager.load("backbutton.png", Texture.class);
        assetManager.load("leader.jpg", Texture.class);
        assetManager.load("stop.png", Texture.class);
        assetManager.load("bullet1.png" , Texture.class) ;
        assetManager.load("bullet.png", Texture.class);
        assetManager.load("background-0.png", Texture.class);
        assetManager.load("background-1.png", Texture.class);
        assetManager.load("background-2.png", Texture.class);
        assetManager.load("background-3.png", Texture.class);
        assetManager.load("background-4.png", Texture.class);
        assetManager.load("background-5.png", Texture.class);
        assetManager.load("background-7.png", Texture.class);
        assetManager.load("playbtn.png", Texture.class);
        assetManager.load("replaybtn.png", Texture.class);
        assetManager.load("rocket.png", Texture.class);
        assetManager.load("splashScreen.jpg", Texture.class);
        assetManager.load("blast/blast-0.png", Texture.class);
        assetManager.load("blast/blast-1.png", Texture.class);
        assetManager.load("blast/blast-2.png", Texture.class);
        assetManager.load("blast/blast-3.png", Texture.class);
        assetManager.load("blast/blast-4.png", Texture.class);
        assetManager.load("blast/blast-5.png", Texture.class);
        assetManager.load("blast/blast-6.png", Texture.class);
        assetManager.load("blast/blast-7.png", Texture.class);
        assetManager.load("blast/blast-8.png", Texture.class);


    }

    @Override
    public void create() {
        assetManager = new AssetManager();
        batch = new SpriteBatch();
        font = new BitmapFont();
        db = Leaderboard.getInstance();

        loadAssets();
        this.setScreen(new SplashScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
        font.dispose();
        batch.dispose();
        assetManager.dispose();
    }
}