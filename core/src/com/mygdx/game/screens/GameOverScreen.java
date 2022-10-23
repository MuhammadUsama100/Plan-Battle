package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.utils.GameState;

public class GameOverScreen implements Screen, InputProcessor, Input.TextInputListener {

    private final MyGdxGame game;
    private GameState gameState = GameState.getInstance();

    TextureAtlas backgroundTextureAtlas;
    TextureRegion backgroundTextureRegion;

    private int replayButtonX, replayButtonY, replayButtonWidth, replayButtonHeight;

    public GameOverScreen(final MyGdxGame game) {
        backgroundTextureAtlas = new TextureAtlas(Gdx.files.internal("background.txt"));
        backgroundTextureRegion = backgroundTextureAtlas.findRegion("0");

        Gdx.input.setInputProcessor(this);

        replayButtonX = Gdx.graphics.getWidth() / 2 - 25;
        replayButtonY = Gdx.graphics.getHeight() / 2 - 50;
        replayButtonWidth = 50;
        replayButtonHeight = replayButtonWidth;

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
        game.font.draw(game.batch, "GAMEOVER", Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 + 100, 200, Align.center, true);
        game.font.getData().setScale(1f);
        game.font.draw(game.batch, "Score: " + gameState.getScore(), Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 + 50, 200, Align.center, true);
        game.font.draw(game.batch, "Time Elapsed: " + gameState.getTimeElapsed() + "s", Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 + 30, 200, Align.center, true);
        game.batch.draw(game.assetManager.get("replaybtn.png", Texture.class), replayButtonX, replayButtonY, replayButtonWidth, replayButtonHeight);
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
        if (screenX >= replayButtonX && screenX <= replayButtonX + replayButtonWidth && Gdx.graphics.getHeight() - screenY >= replayButtonY && Gdx.graphics.getHeight() - screenY <= replayButtonY + replayButtonHeight) {
            Gdx.input.getTextInput(this, "Enter nickname", "",  "gamerX212");
            gameState.setGameOver(false);
            game.setScreen(new MenuScreen(game));
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

    @Override
    public void input(String nickname) {
        game.db.addScore(nickname, gameState.getScore());
        gameState.resetState();
    }

    @Override
    public void canceled() {

    }
}
