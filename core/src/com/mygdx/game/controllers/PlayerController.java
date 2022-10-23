package com.mygdx.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;


public class PlayerController extends ScreenAdapter {
    Vector2 movement;
    Vector2 firecontroll;
    boolean touchdown = false;
    public static final String TAG = MyGdxGame.class.getName();
    private static final float CIRCLE_RADIUS = 20 * 2;

    Vector2 corridnates, corridnates_outer;

    // How many seconds until the circular motion repeats
    private static final float PERIOD = 1.0f;
    boolean rocket;
    private ShapeRenderer renderer;
    Viewport port;
    Vector2  Firing ;
    boolean  Shot ;
    // We set up a variable to hold the nanoTime at which the application was created.
    private long initialTime;

    public PlayerController(Viewport viewport) {
        Shot = false  ;
        port = viewport;
        renderer = new ShapeRenderer();
        corridnates = new Vector2();
        // Set the initialTime
        initialTime = TimeUtils.nanoTime();
        corridnates.x = 150;
        corridnates.y = 150;
        corridnates_outer = new Vector2();
        corridnates_outer.y = 150;
        corridnates_outer.x = 150;
        movement = new Vector2();
        movement.x = 0;
        movement.y = 0;
        firecontroll = new Vector2();
        firecontroll.x = Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 4;
        firecontroll.y = Gdx.graphics.getHeight() / 4 -  50 ;
        Firing =  new Vector2() ;
        Firing.x  = firecontroll.x;
        Firing.y =  firecontroll.y+CIRCLE_RADIUS + 60  ;
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }

    @Override
    public void resize(int width, int height) {

    }


    public void draw() {


        renderer.begin(ShapeType.Line);
        renderer.circle(corridnates_outer.x, corridnates_outer.y, CIRCLE_RADIUS * 3);
        renderer.end();
        renderer.begin(ShapeType.Filled);
        renderer.circle(corridnates.x, corridnates.y, CIRCLE_RADIUS);

        renderer.end();
        renderer.begin(ShapeType.Line);
        renderer.circle(firecontroll.x, firecontroll.y, CIRCLE_RADIUS);

        renderer.end();
        renderer.begin(ShapeType.Line);
        renderer.circle(firecontroll.x , firecontroll.y+CIRCLE_RADIUS + 60  ,  CIRCLE_RADIUS);
        renderer.end();

    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //	return false
        Vector2 World = port.unproject(new Vector2(screenX, screenY));
        //System.out.print(World.x);
        if (World.dst(corridnates) < CIRCLE_RADIUS) {
            touchdown = true;
            System.out.print(World.x);
        }
        if (World.dst(firecontroll) < CIRCLE_RADIUS) {
            rocket = true;
        }
        if (World.dst(Firing) < CIRCLE_RADIUS) {
            Shot =  true  ;
        }

        return false;
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        corridnates.y = 150;
        corridnates.x = 150;
        movement.x = 0;
        movement.y = 0;
        touchdown = false;
        rocket = false;

        Shot =  false  ;

        return false;
    }

    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector2 World = port.unproject(new Vector2(screenX, screenY));

        if (touchdown) {
            if (corridnates_outer.dst(World) < CIRCLE_RADIUS * 3 - CIRCLE_RADIUS) {
                corridnates = World;

                movement.x = corridnates.x - 150;
                movement.y = corridnates.y - 150;
            }
        }

        return false;
    }

    public Vector2 getMovement() {
        return movement;
    }

    public boolean isTouchdown() {
        return touchdown;
    }

    public boolean isRocket() {
        return rocket;
    }

    public void setRocket(boolean rocket) {
        this.rocket = rocket;
    }


    public boolean isShot() {
        return Shot;
    }

    public void setShot(boolean shot) {
        Shot = shot;
    }
}