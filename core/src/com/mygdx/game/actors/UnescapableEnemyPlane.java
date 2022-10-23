package com.mygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.Collisiondetection;
import com.mygdx.game.Bullet;
import com.mygdx.game.utils.GameState;

import java.util.ArrayList;
import java.util.Random;


public class UnescapableEnemyPlane extends Actor {
    private final long width = Gdx.graphics.getWidth() / 6;
    private final long height = Gdx.graphics.getHeight() / 5;
    private float nextbullet;
    private final GameState gameState = GameState.getInstance();
    private long startTime = TimeUtils.millis();
    Texture texture;
    boolean  distroy ;
    Texture texture2;
    float accuracy;
    Random random;
    private boolean crash = false;
    PlayerPlane playerPlane;
    Vector2 enemyposition;
    private int number_Of_Bullets = 0;
    private float velocity = 1f;
    int x_velocity = 0;
    boolean fire = false;
    Collisiondetection collisiondetection;

    ArrayList<Bullet> Bullets;


    public UnescapableEnemyPlane(AssetManager assetManager, PlayerPlane playerPlane) {
        texture = assetManager.get("enemy.png", Texture.class);
        texture2 = assetManager.get("bullet.png", Texture.class);

        this.playerPlane = playerPlane;
        random = new Random();
        int y = random.nextInt(Gdx.graphics.getHeight());
        y++;
        enemyposition = new Vector2(Gdx.graphics.getWidth() + 100, y);
        x_velocity = (int) enemyposition.x;
        Bullets = new ArrayList<Bullet>();
        distroy =  false  ;
        collisiondetection =  new Collisiondetection(enemyposition , width ,  height );

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.draw(texture, enemyposition.x, enemyposition.y, width, height);
        //------------------------------------------------------------------------------------------
        for (Bullet bullet : Bullets) {
            batch.draw(texture2, bullet.getPosition().x, bullet.getPosition().y + 20, 50, 60);
        }

        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        ArrayList<Bullet> bulletToRemove = new ArrayList<Bullet>();

        for (Bullet bullet : Bullets) {
            if (bullet.getCollisiondetection().Action(playerPlane.getCollisiondetection())) {
                bulletToRemove.add(bullet);
                gameState.decrementLife();
                number_Of_Bullets = number_Of_Bullets + 1;
                Gdx.input.vibrate(200);
            }
        }

        System.out.println(TimeUtils.timeSinceMillis(startTime));
        accuracy = playerPlane.getPlayer_position().y - enemyposition.y;
        if (accuracy > 0 && accuracy < 1 && TimeUtils.timeSinceMillis(startTime) > 600 + nextbullet) {
            nextbullet = TimeUtils.timeSinceMillis(startTime);
            fire = true;
            Bullets.add(new Bullet(enemyposition));
        } else {
            fire = false;
        }

        for (Bullet bullet : Bullets) {
            bullet.update(delta, -1);
            if (bullet.remove) {
                bulletToRemove.add(new Bullet(enemyposition));
            }

        }

        collisiondetection.collisionMove(enemyposition);
        Bullets.removeAll(bulletToRemove);


        if (enemyposition.x > playerPlane.getPlayer_position().x) {
            enemyposition.x = enemyposition.x - velocity;
        } else if (enemyposition.x < playerPlane.getPlayer_position().x) {
            enemyposition.x = enemyposition.x - velocity;
        }
        if (enemyposition.y > playerPlane.getPlayer_position().y) {
            enemyposition.y = enemyposition.y - velocity;
        } else if (enemyposition.y < playerPlane.getPlayer_position().y) {
            enemyposition.y = enemyposition.y + velocity;
        }
        super.act(delta);

        for (Bullet  bullet : playerPlane.bullets) {
            if (bullet.getCollisiondetection().Action(collisiondetection)) {
                distroy =  true  ;
            }
        }


    }

    @Override
    public float getX() {
        return enemyposition.x;

    }

    @Override
    public float getY() {
        return enemyposition.y;
    }

    @Override
    public String getName() {
        return "UnescapableEnemyPlane";
    }

    @Override
    public int getZIndex() {
        return number_Of_Bullets;
    }

    @Override
    public boolean setZIndex(int x) {
        this.number_Of_Bullets = x;
        return true;
    }
    @Override
    public boolean hasParent() {
        return distroy;
    }


}

