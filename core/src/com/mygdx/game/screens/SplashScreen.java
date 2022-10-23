package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.MyGdxGame;

import java.util.concurrent.ForkJoinTask;

public class SplashScreen implements Screen {
    private final MyGdxGame game;
    private final long startTime = TimeUtils.millis();
    private Music music = Gdx.audio.newMusic(Gdx.files.internal("aliem.mp3"));
    Texture texture0;
    Texture texture1;
    Texture texture2;
    float variable;


    private final long width = Gdx.graphics.getWidth() / 2 - 60;
    private final long height = Gdx.graphics.getHeight() / 5 + 30;

    public SplashScreen(final MyGdxGame game) {
        this.game = game;

        texture0 = new Texture("loading0.png");
        texture1 = new Texture("loading1.png");
        texture2 = new Texture("splashScreen.jpg");

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        music.play();
        game.batch.begin();
        game.batch.draw(texture2, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.draw(texture0, Gdx.graphics.getWidth() / 8, Gdx.graphics.getHeight() / 5, Gdx.graphics.getWidth() / 1.3f, 50);
        variable = game.assetManager.getProgress() * (Gdx.graphics.getWidth() / 1.3f) - 10;

        game.batch.draw(texture1, Gdx.graphics.getWidth() / 8 + 8, Gdx.graphics.getHeight() / 5 + 5, variable, 40);

        game.batch.end();
        game.batch.begin();

        game.font.draw(game.batch, "Loading..." + String.valueOf(game.assetManager.getProgress() * 100) + "%", width, height);
        game.batch.end();
        if (game.assetManager.update() && TimeUtils.timeSinceMillis(startTime) > 3500) {
            game.setScreen(new MenuScreen(game));
            dispose();
            music.dispose();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }
}

