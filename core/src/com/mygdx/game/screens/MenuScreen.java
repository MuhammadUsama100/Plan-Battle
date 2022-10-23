package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.MyGdxGame;

public class MenuScreen implements Screen, InputProcessor {

    MyGdxGame game;

    TextureAtlas backgroundTextureAtlas;
    TextureRegion backgroundTextureRegion;

    private int playButtonX, playButtonY, playButtonWidth, playButtonHeight;
    private int leaderboardButtonWidth, leaderboardButtonHeight, leaderboardButtonX, leaderboardButtonY;

    MenuScreen(MyGdxGame game) {
        backgroundTextureAtlas = new TextureAtlas(Gdx.files.internal("background.txt"));
        backgroundTextureRegion = backgroundTextureAtlas.findRegion("0");

        Gdx.input.setInputProcessor(this);

        playButtonWidth = 100;
        playButtonHeight = playButtonWidth;
        playButtonX = Gdx.graphics.getWidth() / 2 - (playButtonWidth/2) - 100;
        playButtonY = Gdx.graphics.getHeight() / 2 - playButtonWidth;

        leaderboardButtonWidth = 100;
        leaderboardButtonHeight = leaderboardButtonWidth;
        leaderboardButtonX = Gdx.graphics.getWidth() / 2 - (leaderboardButtonWidth/2) + 100;
        leaderboardButtonY = Gdx.graphics.getHeight() / 2 - leaderboardButtonWidth;

        this.game = game;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        game.batch.begin();
        game.batch.draw(backgroundTextureRegion, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.font.getData().setScale(2f);
        game.font.draw(game.batch, "AIR BATTLE", Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 + 100, 200, Align.center, true);
        game.font.getData().setScale(1f);
        game.batch.draw(game.assetManager.get("playbtn.png", Texture.class), playButtonX, playButtonY, playButtonWidth, playButtonHeight);
        game.batch.draw(game.assetManager.get("leader.jpg", Texture.class), leaderboardButtonX, leaderboardButtonY, leaderboardButtonWidth, leaderboardButtonHeight);
        game.batch.end();
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

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (screenX >= playButtonX && screenX <= playButtonX + playButtonWidth && Gdx.graphics.getHeight() - screenY >= playButtonY && Gdx.graphics.getHeight() - screenY <= playButtonY + playButtonHeight) {
            game.setScreen(new GameScreen(game));
            dispose();
        }

        if (screenX >= leaderboardButtonX && screenX <= leaderboardButtonX + leaderboardButtonWidth && Gdx.graphics.getHeight() - screenY >= leaderboardButtonY && Gdx.graphics.getHeight() - screenY <= leaderboardButtonY + leaderboardButtonHeight) {

            game.setScreen(new LeaderboardScreen(game));


            dispose();
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
