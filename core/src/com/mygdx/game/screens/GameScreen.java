package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Bullet;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.actors.Straight_Flight;
import com.mygdx.game.controllers.PlayerController;
import com.mygdx.game.actors.UnescapableEnemyPlane;
import com.mygdx.game.actors.PlayerPlane;
import com.mygdx.game.utils.GameState;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class GameScreen implements Screen, InputProcessor {
    private final GameState gameState = GameState.getInstance();
    final MyGdxGame game;
    private PlayerController playerController;
    private Box2DDebugRenderer box2DDebugRenderer;
    private final long startTime = TimeUtils.millis();
    private float nextenemy;
    private World world;
    Random random;
    private Stage stage;
    float timer;
    TextureAtlas textureAtlas;
    TextureAtlas textureAtlasblast;
    private PlayerPlane playerPlane;
    private Viewport viewport;
    final int maxFrame = 238;
    boolean targetDistroyed;
    float accuracyX = 0;
    float accuracyY = 0;
    int currentFrame = 0;
    OrthographicCamera camera;
    TextureRegion textureRegion;
    TextureRegion textureRegionBlast;
    float time;
    int choice ;
    TimeUnit timeUnit;

    private int playButtonX, playButtonY, playButtonWidth, playButtonHeight;
    private int pauseButtonX, pauseButtonY, pauseButtonWidth, pauseButtonHeight;


    private Music wind = Gdx.audio.newMusic(Gdx.files.internal("wind.mp3"));

    private float scoreTimeState;

    public GameScreen(final MyGdxGame game) {
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        this.game = game;
        random = new Random();
        nextenemy = random.nextInt(10000)+ 300;
        stage = new Stage(viewport);
        world = new World(new Vector2(0, -50), true);
        box2DDebugRenderer = new Box2DDebugRenderer();
        playerController = new PlayerController(stage.getViewport());

        textureAtlas = new TextureAtlas(Gdx.files.internal("background.txt"));
        textureAtlasblast = new TextureAtlas(Gdx.files.internal("blast/blast.txt"));

        pauseButtonX = 10;
        pauseButtonY = Gdx.graphics.getHeight()-120;
        pauseButtonWidth = 100;
        pauseButtonHeight = pauseButtonWidth;

        playButtonX = Gdx.graphics.getWidth() / 2 - playButtonWidth/2 - 50;
        playButtonY = Gdx.graphics.getHeight() / 2 - 60;
        playButtonWidth = 100;
        playButtonHeight = playButtonWidth;

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void show() {
        playerPlane = new PlayerPlane(game.assetManager, world, this.playerController);
        wind.setVolume(0.5f);
        wind.play();
        stage.addActor(playerPlane);

    }

    @Override
    public void render(float delta) {
        if(!gameState.isPaused()) {
            Gdx.gl.glClearColor(0, 0, 1, 0);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            currentFrame++;
            if (currentFrame > maxFrame) {
                currentFrame = 0;
            }

            textureRegion = textureAtlas.findRegion(String.valueOf(currentFrame));
            choice =  random.nextInt(3) ;
            choice ++ ;
            System.out.print(choice);

            //  code for drawing of background  :
            game.batch.begin();
            game.batch.draw(textureRegion, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            game.font.draw(game.batch, "Score: " + gameState.getScore(), Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 20);
            game.font.draw(game.batch, "Time Elapsed: " + gameState.getTimeElapsed() + "s", Gdx.graphics.getWidth() - 250, Gdx.graphics.getHeight() - 20);
            game.font.draw(game.batch, "Lives: " + gameState.getRemainingLives(), Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 40);
            game.batch.draw(game.assetManager.get("stop.png", Texture.class), pauseButtonX, pauseButtonY, pauseButtonWidth, pauseButtonHeight);
            game.batch.end();

            if (TimeUtils.timeSinceMillis(startTime) > timer + nextenemy) {
                timer = TimeUtils.timeSinceMillis(startTime);

                if (choice ==  1 || choice == 2 ){
                    UnescapableEnemyPlane unescapableEnemyPlane = new UnescapableEnemyPlane(game.assetManager, playerPlane);
                    stage.addActor(unescapableEnemyPlane);}
                else if ( choice == 3) {
                    stage.getActors();
                    Straight_Flight straight_flight =  new Straight_Flight(game.assetManager , playerPlane) ;
                    stage.addActor(straight_flight);
                }

            }


            world.step(1 / 60f, 8, 3);

            box2DDebugRenderer.render(world, stage.getCamera().combined);

            playerController.draw();

            // fire button : -


            //
            if (!gameState.hasRemainingLives()) gameState.setGameOver(true);

            if (gameState.isGameOver()) {
                game.setScreen(new GameOverScreen(game));
                wind.dispose();
                dispose();
            }

            scoreTimeState += Gdx.graphics.getDeltaTime();
            if (scoreTimeState >= 1f) {
                scoreTimeState = 0;
                gameState.incrementScore(1);
                gameState.setTimeElapsed(gameState.getTimeElapsed() + 1);
            }

            for (Actor actor : stage.getActors()) {
                if (actor.getName() == "UnescapableEnemyPlane" || actor.getName() == "Straight_Flight") {
                    if (gameState.getMinimumEnemyX() > actor.getX() && gameState.getMinimumEnemyY() > actor.getY()) {
                        gameState.setMinimumEnemyX(actor.getX());
                        gameState.setMinimumEnemyY(actor.getY());
                    }
                }

            }
            for (Actor actor :  stage.getActors()){
                if (actor.getName() ==  "Straight_Flight") {
                    if (actor.hasActions()) {
                        for (int i = 1; i <= 71; i++) {
                            String string;
                            string = String.valueOf(i);
                            string = "0" + " " + "(" + string + ")";
                            textureRegionBlast = textureAtlasblast.findRegion(string);
                            game.batch.begin();
                            game.batch.draw(textureRegionBlast, actor.getX(), actor.getY(), 100, 100);
                            game.batch.end();

                        }
                        gameState.decrementLife();
                        gameState.decrementLife();
                        Gdx.input.vibrate(300);
                        actor.remove() ;
                    }
                }
            }

            targetDistroyed = false;
            for (Actor actor : stage.getActors()) {

                if (actor.getName() == "PlayerPlane") {
                    targetDistroyed = actor.getDebug();
                }
                if (actor.getName() == "UnescapableEnemyPlane" || actor.getName() == "Straight_Flight") {
                    System.out.println("Testing : ");
                    System.out.println(targetDistroyed);
                    System.out.println(actor.getX());
                    System.out.println(gameState.getMinimumEnemyX());
                    accuracyX = actor.getX() - gameState.getMinimumEnemyX();
                    accuracyY = actor.getY() - gameState.getMinimumEnemyY();
                    if (targetDistroyed && accuracyX == 0 && accuracyY == 0) {

                        for (int i = 1; i <= 71; i++) {


                            String string;
                            string = String.valueOf(i);
                            string = "0" + " " + "(" + string + ")";
                            textureRegionBlast = textureAtlasblast.findRegion(string);
                            game.batch.begin();
                            game.batch.draw(textureRegionBlast, actor.getX(), actor.getY(), 100, 100);
                            game.batch.end();

                        }
                        actor.remove();
                    }
                    if (actor.getX() < -40) {
                        actor.remove();
                    }
                }

            }

            for (Actor actor : stage.getActors()) {
                if (actor.getName() == "UnescapableEnemyPlane" || actor.getName() == "Straight_Flight") {
                    if (actor.hasParent()) {
                        actor.remove() ;
                    }

                }
            }


            stage.act(Gdx.graphics.getDeltaTime());
            stage.draw();
        } else {
            Gdx.gl.glClearColor(0, 0, 0, 0.6f);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            game.batch.begin();
            game.font.getData().setScale(2f);
            game.font.draw(game.batch, "GAME PAUSED", Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 + 100, 200, Align.center, true);
            game.font.getData().setScale(1f);
            game.batch.draw(game.assetManager.get("playbtn.png", Texture.class), playButtonX, playButtonY, playButtonWidth, playButtonHeight);
            game.batch.end();
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
    }

    // touchdown pe fire

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
        stage.dispose();
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
        if (screenX >= pauseButtonX && screenX <= pauseButtonX + pauseButtonWidth && Gdx.graphics.getHeight() - screenY >= pauseButtonY && Gdx.graphics.getHeight() - screenY <= pauseButtonY + pauseButtonHeight) {
            gameState.setPaused(true);
            wind.pause();
        }

        if(gameState.isPaused()) {
            if (screenX >= playButtonX && screenX <= playButtonX + playButtonWidth && Gdx.graphics.getHeight() - screenY >= playButtonY && Gdx.graphics.getHeight() - screenY <= playButtonY + playButtonHeight) {
                gameState.setPaused(false);
                wind.play();
            }
        }

        playerController.touchDown(screenX, screenY, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        playerController.touchUp(screenX, screenY, pointer, button);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        playerController.touchDragged(screenX, screenY, pointer);
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
