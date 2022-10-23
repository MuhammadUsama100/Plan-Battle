package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.actors.BackButton;
import com.mygdx.game.dataModels.Score;

import java.util.ArrayList;

public class LeaderboardScreen implements Screen, InputProcessor {

    MyGdxGame game;

    TextureAtlas backgroundTextureAtlas;
    TextureRegion backgroundTextureRegion;

    ArrayList<Score> leaderboard;

    Score highestScore;

    Stage stage;
    OrthographicCamera camera;
    Viewport viewport;

    private boolean isBackPressed;

    private int backButtonX, backButtonY, backButtonWidth, backButtonHeight;

    public LeaderboardScreen(MyGdxGame game) {
        backgroundTextureAtlas = new TextureAtlas(Gdx.files.internal("background.txt"));
        backgroundTextureRegion = backgroundTextureAtlas.findRegion("0");
        this.game = game;

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        stage = new Stage(viewport);

        isBackPressed = false;

        backButtonX = 10;
        backButtonY = Gdx.graphics.getHeight() - 120;
        backButtonWidth = 100;
        backButtonHeight = backButtonWidth;
    }

    @Override
    public void show() {

        Table table = new Table();
        Table container = new Table();

        ScrollPane scrollPane = new ScrollPane(table);
        container.add(scrollPane).width((float)Gdx.graphics.getWidth()).height((float)Gdx.graphics.getHeight());
        container.row();

        leaderboard = game.db.getLeaderboard();
        highestScore = leaderboard.get(0);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(inputMultiplexer);
        inputMultiplexer.addProcessor(this);
        inputMultiplexer.addProcessor(stage);

        Label.LabelStyle labelStyle = new Label.LabelStyle(game.font, Color.WHITE);

        for(Score score: leaderboard) {

            Label nameLabel = new Label(score.getName(), labelStyle);
            Label scoreLabel = new Label(String.valueOf(score.getScore()), labelStyle);

            table.add(nameLabel).pad(10);
            table.add(scoreLabel).pad(10);
            table.row();
            nameLabel.setAlignment(Align.center);
            scoreLabel.setAlignment(Align.center);

        }

        scrollPane.setX(Gdx.graphics.getWidth()/2 - (scrollPane.getWidth()/2));
        scrollPane.setY(Gdx.graphics.getHeight()/2 - (scrollPane.getHeight()/2));
//        scrollPane.setHeight(Gdx.graphics.getHeight()/2);
        stage.addActor(scrollPane);
        BackButton backBtn = new BackButton(game.assetManager);
        stage.addActor(backBtn);

    }

    @Override
    public void render(float delta) {
        if(isBackPressed) {
            game.setScreen(new MenuScreen(game));
            dispose();
        }
        game.batch.begin();
        game.batch.draw(backgroundTextureRegion, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.font.getData().setScale(2f);
        game.font.draw(game.batch, "Leaderboard", Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() - 20, 200, Align.center, true);
        game.font.getData().setScale(1f);
        game.font.draw(game.batch, "Highest score: " + highestScore.getScore() + " (" + highestScore.getName() + ")", Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() - 90, 200, Align.center, true);
        game.batch.draw(game.assetManager.get("backbutton.png", Texture.class), backButtonX, backButtonY, backButtonWidth, backButtonHeight);
        game.batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
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
        if (screenX >= backButtonX && screenX <= backButtonX + backButtonWidth && Gdx.graphics.getHeight() - screenY >= backButtonY && Gdx.graphics.getHeight() - screenY <= backButtonY + backButtonHeight) {
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
}
